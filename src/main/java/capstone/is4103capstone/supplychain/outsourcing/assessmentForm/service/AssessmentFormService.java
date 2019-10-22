package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailService;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentLineRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentSectionRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.configuration.ThreadPoolTaskSchedulerConfig;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateTemplateReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateResponseReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAssessmentFormRes;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssessmentFormService {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentFormService.class);
    @Autowired
    OutsourcingAssessmentLineRepository outsourcingAssessmentLineRepository;
    @Autowired
    OutsourcingAssessmentSectionRepository outsourcingAssessmentSectionRepository;
    @Autowired
    OutsourcingAssessmentRepository outsourcingAssessmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    OutsourcingRepository outsourcingRepository;
    @Autowired
    MailService mailService;

    public ResponseEntity<GeneralRes> getForm(String id){
        try{
            OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.getOne(id);
            for(OutsourcingAssessmentSection s: outsourcingAssessment.getSectionList()){
                for(OutsourcingAssessmentLine o: s.getOutsourcingAssessmentLines()){
                    logger.info("Fetching line item question"+o.getQuestion());
                }
            }
            if(outsourcingAssessment == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetAssessmentFormRes("Successfully retrieved the assessment form!", true, outsourcingAssessment));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getTemplateForm(){
        try{
            OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.findFormByStatus();
            for(OutsourcingAssessmentSection s: outsourcingAssessment.getSectionList()){
                for(OutsourcingAssessmentLine o: s.getOutsourcingAssessmentLines()){
                    logger.info("Fetching line item question"+o.getQuestion());
                }
            }
            if(outsourcingAssessment == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetAssessmentFormRes("Successfully retrieved the assessment form template!", true, outsourcingAssessment));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> createResponseForm(CreateResponseReq createResponseReq, String id, String username){
        try {
            logger.info("Action performed by: "+username+" on "+new Date());
            OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();
            Employee creater = employeeRepository.findEmployeeByUserName(username);
            if(creater == null) {
                logger.info("Cannot find the user!");
                return ResponseEntity.notFound().build();
            }
            if(id != null){
                OutsourcingAssessment temp = outsourcingAssessmentRepository.getOne(id);
                if(temp != null){
                    temp.setDeleted(true);
                    outsourcingAssessmentRepository.saveAndFlush(temp);
                    List<OutsourcingAssessmentSection> sections = temp.getSectionList();
                    for(OutsourcingAssessmentSection s: sections){
                        s.setDeleted(true);
                        outsourcingAssessmentSectionRepository.saveAndFlush(s);
                        List<OutsourcingAssessmentLine> lines = s.getOutsourcingAssessmentLines();
                        for(OutsourcingAssessmentLine l: lines){
                            l.setDeleted(true);
                            outsourcingAssessmentLineRepository.saveAndFlush(l);
                        }
                    }
                }
            }

            OutsourcingAssessment template = outsourcingAssessmentRepository.findFormByStatus();
            List<OutsourcingAssessmentSection> sections = template.getSectionList();
            outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_BM_APPROVAL);
            outsourcingAssessment.setBusinessCaseDescription(createResponseReq.getBusinessCaseDescription());
            OutsourcingAssessment newAssess = outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);

            for (OutsourcingAssessmentSection s : sections) {
                OutsourcingAssessmentSection newS = new OutsourcingAssessmentSection();
                newS = outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                List<OutsourcingAssessmentLine> outsourcingAssessmentLine = s.getOutsourcingAssessmentLines();
                List<OutsourcingAssessmentLine> linesSaved = new ArrayList<>();
                for (int i = 0; i < outsourcingAssessmentLine.size(); i ++) {
                    OutsourcingAssessmentLine o = new OutsourcingAssessmentLine();
                    o.setQuestion(outsourcingAssessmentLine.get(i).getQuestion());
                    o.setAnswer(createResponseReq.getResponses().get(i));
                    o.setComment(createResponseReq.getComments().get(i));
                    o.setCreatedBy(username);
                    o.setCreatedDateTime(new Date());
                    o.setOutsourcingAssessmentSection(newS);
                    linesSaved.add(outsourcingAssessmentLineRepository.saveAndFlush(o));
                }
                newS.setOutsourcingAssessmentLines(linesSaved);
                newS.setOutsourcingAssessment(newAssess);
                newS.setCreatedBy(username);
                newS.setCreatedDateTime(new Date());
                outsourcingAssessmentSectionRepository.saveAndFlush(newS);
            }
            newAssess.setSectionList(sections);
            newAssess.setCreatedBy(username);
            newAssess.setCreatedDateTime(new Date());

            outsourcingAssessmentRepository.saveAndFlush(newAssess);
            Employee approverA = creater.getManager();
            createApprovalTicket(creater.getUserName(), approverA,newAssess,"Please review and approve the outsourcing assessment form.");
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes("Successfully created the outsourcing assessment form!", false));
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> createTemplateForm(CreateTemplateReq createTemplateReq, String username){
        try {

            logger.info("Action performed by: "+username+" on "+new Date());

            OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();

            Employee creater = employeeRepository.findEmployeeByUserName(username);

            if(creater == null) {
                logger.info("Cannot find the user!");
                return ResponseEntity.notFound().build();
            }

            OutsourcingAssessment temp = outsourcingAssessmentRepository.findFormByStatus();
            if(temp != null){
                temp.setDeleted(true);
                outsourcingAssessmentRepository.saveAndFlush(temp);
                List<OutsourcingAssessmentSection> sections = temp.getSectionList();
                for(OutsourcingAssessmentSection s: sections){
                    s.setDeleted(true);
                    outsourcingAssessmentSectionRepository.saveAndFlush(s);
                    List<OutsourcingAssessmentLine> lines = s.getOutsourcingAssessmentLines();
                    for(OutsourcingAssessmentLine l: lines){
                        l.setDeleted(true);
                        outsourcingAssessmentLineRepository.saveAndFlush(l);
                    }
                }
            }

            List<OutsourcingAssessmentSection> sections = createTemplateReq.getSectionList();

            outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.TEMPLATE);
            OutsourcingAssessment newAssess = outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);

            for (OutsourcingAssessmentSection s : sections) {
                OutsourcingAssessmentSection newS = outsourcingAssessmentSectionRepository.saveAndFlush(s);
                List<OutsourcingAssessmentLine> outsourcingAssessmentLine = s.getOutsourcingAssessmentLines();
                List<OutsourcingAssessmentLine> linesSaved = new ArrayList<>();
                for (OutsourcingAssessmentLine o : outsourcingAssessmentLine) {
                    o.setCreatedBy(username);
                    o.setCreatedDateTime(new Date());
                    o.setOutsourcingAssessmentSection(newS);
                    linesSaved.add(outsourcingAssessmentLineRepository.saveAndFlush(o));
                }
                newS.setOutsourcingAssessmentLines(linesSaved);
                newS.setOutsourcingAssessment(newAssess);
                newS.setCreatedBy(username);
                newS.setCreatedDateTime(new Date());
                outsourcingAssessmentSectionRepository.saveAndFlush(newS);
            }
            newAssess.setSectionList(sections);
            newAssess.setCreatedBy(username);
            newAssess.setCreatedDateTime(new Date());

            outsourcingAssessmentRepository.saveAndFlush(newAssess);
            Employee approverA = creater.getManager();
            createApprovalTicket(creater.getUserName(), approverA,newAssess,"Please review and approve the outsourcing assessment form.");
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes("Successfully created the outsourcing assessment form!", false));
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> approve(String id, boolean approved, boolean isBMApproval, String username){
        try{
            logger.info("****************** Approving assessment form with id "+id+" *********************");
            Optional<OutsourcingAssessment> formOptional= outsourcingAssessmentRepository.findById(id);
            if (!formOptional.isPresent()) {
                logger.info("Assessment Form Not Found!");
                return ResponseEntity
                        .notFound().build();
            }
            OutsourcingAssessment form = formOptional.get();
            if(form.getDeleted()) return ResponseEntity
                    .notFound().build();
            if(approved) {
                if(isBMApproval && form.getOutsourcingAssessmentStatus() == OutsourcingAssessmentStatusEnum.PENDING_BM_APPROVAL) {
                    form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_FUNCTION_APPROVAL);
                    Employee manager = employeeRepository.findEmployeeByUserName(username);
                    createApprovalTicket(form.getCreatedBy(), manager,form,"Please review and approve the outsourcing assessment form.");
                }
                else if(!isBMApproval && form.getOutsourcingAssessmentStatus() == OutsourcingAssessmentStatusEnum.PENDING_FUNCTION_APPROVAL) {
                    form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.APPROVED);
                    String sender = "is4103.capstone@gmail.com";
                    String receiver = employeeRepository.findEmployeeByUserName(form.getCreatedBy()).getEmail();
                    mailService.sendGeneralEmail(sender, receiver, "Your Outsourcing Assessment Form Has Been Approved!", "Outsourcing Assessment Form "+form.getCode()+" has been successfully approved!");
                }
                else {
                    logger.error("approve type is not consistent with the pending status!");
                    return ResponseEntity.badRequest().build();
                }
            }
            else form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.REJECTED);
            outsourcingAssessmentRepository.saveAndFlush(form);
            logger.info("Approving/Rejecting the assessment form created by "+ form.getCreatedBy());
            return ResponseEntity.ok().body(new GeneralRes("Successfully approved/rejected the purchase orders!", false));
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    private void createApprovalTicket(String requesterUsername, Employee receiver, OutsourcingAssessment outsourcingAssessment, String content){
        Employee requesterEntity = employeeRepository.findEmployeeByUserName(requesterUsername);
        ApprovalTicketService.createTicketAndSendEmail(requesterEntity,receiver,outsourcingAssessment,content, ApprovalTypeEnum.OUTSOURCING_ASSESSMENT_FORM);
    }

    public ResponseEntity<GeneralRes> setTimer(Date date, String id){
        try{

            Outsourcing outsourcing = outsourcingRepository.getOne(id);
            if(outsourcing == null) return ResponseEntity
                    .notFound().build();
            else {
                Mail mail = buildMail(outsourcing);
                ThreadPoolTaskSchedulerConfig threadPoolTaskSchedulerConfig = new ThreadPoolTaskSchedulerConfig();
                AssessmentRunnableTask assessmentRunnableTask = new AssessmentRunnableTask(mail);
                threadPoolTaskSchedulerConfig.threadPoolTaskScheduler().schedule(assessmentRunnableTask, date);
                return ResponseEntity.ok().body(new GeneralRes("Successfully set the alert for the outsourcing!", false));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    private Mail buildMail(Outsourcing outsourcing){
        String from = "is4103.capstone@gmail.com";
        String toUsername = outsourcing.getCreatedBy();
        String to = employeeRepository.findEmployeeByUserName(toUsername).getEmail();
        String subject = "Reminder: Please Complete the Annual Outsourcing Assessment Form";
        Map<String, Object> model = new HashMap<>();

        String link = "http://localhost:3000/api/assessmentForm/getDetails/"+outsourcing.getId();
        model.put("link", link);
        return new Mail(from, to, subject, model);
    }
}
