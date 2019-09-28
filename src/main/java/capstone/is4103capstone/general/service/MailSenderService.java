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

    @Autowired
    public MailSenderService(MailSenderProperties mailSenderProperties) {
        System.out.println("username: "+mailSenderProperties.getUsername());
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(mailSenderProperties.getUsername());
        javaMailSender.setPassword(mailSenderProperties.getPassword());
        javaMailSender.setHost(mailSenderProperties.getHost());
        javaMailSender.setPort(mailSenderProperties.getPort());

    }


    public GeneralRes sendEmail(Mail mail, String template) {
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

    //        helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));
            String html = mailContentBuilder.build(mail.getModel(), template);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            emailSender.send(message);
            return new GeneralRes("Email successfully sent!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
//        properties.setProperty("mail.smtp.quitwait", "false");
        properties.setProperty("mail.smtp.connectiontimeout", "5000");
        properties.setProperty("mail.smtp.timeout", "3000");
        properties.setProperty("mail.smtp.writetimeout", "5000");
//        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//        properties.setProperty("mail.debug", "true");
        return properties;
    }

//    private void sendEmail() throws Exception{
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setTo("hangzhipang@gmail.com");
//        helper.setText("How are you?");
//        helper.setSubject("Hi");
//
//        javaMailSender.send(message);
//    }
//
//    public void prepareAndSend(String recipient, String message) {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("sample@dolszewski.com");
//            messageHelper.setTo(recipient);
//            messageHelper.setSubject("Sample mail subject");
//            messageHelper.setText(message);
//
//            String content = mailContentBuilder.build(message);
//            messageHelper.setText(content, true);
//
//        };
//        try {
////            mailSender.send(messagePreparator);
//        } catch (MailException e) {
//            // runtime exception; compiler will not force you to handle it
//        }
//    }
//
//
//
    public void sendmail()  {
        try{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("is4103.capstone@gmail.com", "capstone4103");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("is4103.capstone@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("is4103.capstone@gmail.com"));
        msg.setSubject("Tutorials point email");
        msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

//        attachPart.attachFile("/var/tmp/image19.png");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }catch(Exception ex){
        ex.printStackTrace();
    }}

}