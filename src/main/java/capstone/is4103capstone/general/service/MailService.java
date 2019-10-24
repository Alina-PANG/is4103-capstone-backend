package capstone.is4103capstone.general.service;

import capstone.is4103capstone.general.model.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    MailSenderService mailSenderService;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendGeneralEmail(String from, String to, String subject, String message){
        try{
            HashMap<String, Object> map = new HashMap<>();
            map.put("message", message);
            map.put("name", from);
            Mail mail = new Mail(from, to, subject, map);
            mailSenderService.sendEmail(mail, "mailTemplate");
        }catch (Exception ex){
            logger.error("[Internal Error]Email Sending got problem");
        }

    }

    public void sendGeneralEmailDefaultEmail(String to, String subject, String message){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("message", message);
            map.put("name", senderEmail);
            Mail mail = new Mail(senderEmail, to, subject, map);
            mailSenderService.sendEmail(mail, "mailTemplate");
        }catch (Exception ex){
            logger.error("[Internal Error]Email Sending got problem");
        }
    }
}
