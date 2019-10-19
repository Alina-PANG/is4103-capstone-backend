package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderRes;
import capstone.is4103capstone.finance.finPurchaseOrder.service.PurchaseOrderService;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentLineRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentSectionRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.configuration.ThreadPoolTaskSchedulerConfig;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateAssessmentFromReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateSchedulerReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAssessmentFormRes;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

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
            OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.findFormByStatus(0);
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

    public ResponseEntity<GeneralRes> createForm(boolean isTemplate, CreateAssessmentFromReq createAssessmentFromReq, String id){
        try {

            logger.info("Action performed by: "+createAssessmentFromReq.getUsername()+" on "+new Date());

            OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();

            Employee approverA = employeeRepository.findEmployeeByUserName(createAssessmentFromReq.getApproverAUsername());
            Employee approverB = employeeRepository.findEmployeeByUserName(createAssessmentFromReq.getApproverBUsername());
            if(approverA == null || approverB == null) {
                logger.info("Cannot find approvers!");
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

            List<OutsourcingAssessmentSection> sections = createAssessmentFromReq.getSectionList();

            if(isTemplate)  outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.TEMPLATE);
            else outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_BM_APPROVAL);
            outsourcingAssessment.setBusinessCaseDescription(createAssessmentFromReq.getBusinessCaseDescription());
            OutsourcingAssessment newAssess = outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);

            for (OutsourcingAssessmentSection s : sections) {
                OutsourcingAssessmentSection newS = outsourcingAssessmentSectionRepository.saveAndFlush(s);
                List<OutsourcingAssessmentLine> outsourcingAssessmentLine = s.getOutsourcingAssessmentLines();
                List<OutsourcingAssessmentLine> linesSaved = new ArrayList<>();
                for (OutsourcingAssessmentLine o : outsourcingAssessmentLine) {
                    o.setCreatedBy(createAssessmentFromReq.getUsername());
                    o.setCreatedDateTime(new Date());
                    o.setOutsourcingAssessmentSection(newS);
                    linesSaved.add(outsourcingAssessmentLineRepository.saveAndFlush(o));
                }
                newS.setOutsourcingAssessmentLines(linesSaved);
                newS.setOutsourcingAssessment(newAssess);
                newS.setCreatedBy(createAssessmentFromReq.getUsername());
                newS.setCreatedDateTime(new Date());
                outsourcingAssessmentSectionRepository.saveAndFlush(newS);
            }
            newAssess.setSectionList(sections);
            newAssess.setCreatedBy(createAssessmentFromReq.getUsername());
            newAssess.setCreatedDateTime(new Date());

            outsourcingAssessmentRepository.saveAndFlush(newAssess);
            createApprovalTicket(createAssessmentFromReq.getUsername(), approverA,newAssess,"Please review and approve the outsourcing assessment form.");
            createApprovalTicket(createAssessmentFromReq.getUsername(), approverB,newAssess,"Please review and approve the outsourcing assessment form.");

            return ResponseEntity
                    .ok()
                    .body(new GeneralRes("Successfully created the outsourcing assessment form!", true));
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> approve(String id, boolean approved){
        try{
            logger.info("****************** Getting assessment form with id "+id+" *********************");
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
                if(form.getOutsourcingAssessmentStatus() == OutsourcingAssessmentStatusEnum.PENDING_FUNCTION_APPROVAL) form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.APPROVED);
                else form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_FUNCTION_APPROVAL);
            }
            else form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.REJECTED);
            outsourcingAssessmentRepository.saveAndFlush(form);
            logger.info("Approving/Rejecting the assessment form created by "+ form.getCreatedBy());
            return ResponseEntity.ok().body(new GeneralRes("Successfully approved/rejected the purchase orders!", true));
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
                return ResponseEntity.ok().body(new GeneralRes("Successfully set the alert for the outsourcing!", true));
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
