package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service;

import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;

public class AssessmentRunnableTask implements Runnable{
    @Autowired
    MailSenderService mailSenderService;

    private Mail mail;

    public AssessmentRunnableTask(Mail mail){
        this.mail = mail;
    }

    @Override
    public void run() {
        mailSenderService.sendEmail(this.mail, "scheduledNotification");
    }
}
