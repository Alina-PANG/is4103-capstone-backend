package capstone.is4103capstone.general.service;

import capstone.is4103capstone.general.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MailService {
    @Autowired
    MailSenderService mailSenderService;

    public void sendGeneralEmail(String from, String to, String subject, String message){
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("name", from);
        Mail mail = new Mail(from, to, subject, map);
        mailSenderService.sendEmail(mail, "mailTemplate");
    }
}
