package capstone.is4103capstone.general.service;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormEmailModel;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormLineEmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void sendOscAssForm(String from, String to, String subject, String urlA, String urlATitle, OutsourcingAssessment outsourcingAssessment){
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
    }
}
