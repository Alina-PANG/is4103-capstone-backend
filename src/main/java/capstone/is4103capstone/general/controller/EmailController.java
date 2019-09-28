package capstone.is4103capstone.general.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email/{username}")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {
    @Autowired
    MailSenderService mailSenderService;

    @RequestMapping(value = "/send")
    public ResponseEntity<GeneralRes> sendEmail(@RequestBody Mail mail, @PathVariable("username") String username) {
        String template = "mailTemplate";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hangzhi");
        model.put("message", "test message");
        mail.setModel(model);
        if(Authentication.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(mailSenderService.sendEmail(mail, template));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));

    }
}