package capstone.is4103capstone.general.service;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.properties.FileStorageProperties;
import capstone.is4103capstone.general.properties.MailSenderProperties;
import capstone.is4103capstone.util.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

@Service
public class MailSenderService {
    @Autowired
    MailContentBuilder mailContentBuilder;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    private JavaMailSender emailSender;

    public GeneralRes sendEmail(Mail mail, String template) {
        try{
            MimeMessage message = emailSender.createMimeMessage();
            String html = mailContentBuilder.build(mail.getModel(), template);
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            messageHelper.setFrom(mail.getFrom());
            messageHelper.setTo(mail.getTo());
            messageHelper.setText(html, true);
            messageHelper.setSubject(mail.getSubject());

            //        helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));
            emailSender.send(message);
            return new GeneralRes("Email successfully sent!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

}