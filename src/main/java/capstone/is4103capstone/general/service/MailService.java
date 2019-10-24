package capstone.is4103capstone.general.service;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.general.model.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormEmailModel;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormLineEmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void sendOscAssForm(String from, String to, String subject, String urlA, String urlATitle, OutsourcingAssessment outsourcingAssessment){
        try{
            HashMap<String, Object> map = new HashMap<>();
            List<AssessmentFormEmailModel> list = new ArrayList<>();
            for(OutsourcingAssessmentSection s: outsourcingAssessment.getSectionList()){
                AssessmentFormEmailModel assessmentFormEmailModel = new AssessmentFormEmailModel();
                List<AssessmentFormLineEmailModel> lines = new ArrayList<>();
                for(OutsourcingAssessmentLine l: s.getOutsourcingAssessmentLines()){
                    AssessmentFormLineEmailModel line = new AssessmentFormLineEmailModel();
                    line.setQuestion(l.getQuestion());
                    line.setAnswer(l.getAnswer()? "Y":"N");
                    line.setComment(l.getComment());
                    lines.add(line);
                }
                assessmentFormEmailModel.setLines(lines);
                assessmentFormEmailModel.setSeqNo(s.getNumber());
                list.add(assessmentFormEmailModel);
            }
            map.put("sections", list);
            map.put("urlA_title", urlATitle);
            map.put("urlA", urlA);
            Mail mail = new Mail(from, to, subject, map);
            mailSenderService.sendEmail(mail, "osrcAssFormNotification");
        }catch (Exception ex){
            logger.error("[Internal Error]Email Sending got problem");
        }

    }
}
