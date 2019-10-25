package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailService;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentLineRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentSectionRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.configuration.ThreadPoolTaskSchedulerConfig;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateTemplateReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateResponseReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAsseFormListRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res.GetAssessmentFormRes;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    EmployeeService employeeService;
    @Autowired
    OutsourcingRepository outsourcingRepository;
    @Autowired
    MailService mailService;
    @Autowired
    BjfRepository bjfRepository;
    @Autowired
    BJFService bjfService;

    @Value("${spring.mail.username}")
    private String senderEmailAddr;

    public ResponseEntity<GeneralRes> getForm(String id){
        try{
            OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.getOne(id);
            for(OutsourcingAssessmentSection s: outsourcingAssessment.getSectionList()){
                for(OutsourcingAssessmentLine o: s.getOutsourcingAssessmentLines()){
                    logger.info("Fetching line item question"+o.getQuestion());
                }
            }
            BJFModel bjf = bjfService.getBJFDetails(outsourcingAssessment.getRelated_BJF().getId());
            if(outsourcingAssessment == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetAssessmentFormRes("Successfully retrieved the assessment form!", true, outsourcingAssessment, bjf));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getFormByBjfId(String id){
        try{
            BJF bjf = bjfRepository.getOne(id);
            if(bjf == null || bjf.getDeleted())  return ResponseEntity
                    .notFound().build();
            BJFModel bjfModel = bjfService.getBJFDetails(bjf.getId());
            OutsourcingAssessment outsourcingAssessment = bjf.getRelated_outsourcing_assessment();
            for(OutsourcingAssessmentSection s: outsourcingAssessment.getSectionList()){
                for(OutsourcingAssessmentLine o: s.getOutsourcingAssessmentLines()){
                    logger.info("Fetching line item question"+o.getQuestion());
                }
            }
            if(outsourcingAssessment == null) return ResponseEntity
                    .notFound().build();

            else return ResponseEntity.ok().body(new GetAssessmentFormRes("Successfully retrieved the assessment form!", true, outsourcingAssessment, bjfModel));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    /*
    Retrieve:
    1) if the current log in user belongs to outsourcing team(now: employee code/hierachy contains'OUTSOURCING':
        we return the full list,
    2) If not, we assume he is the directly manager, so we retrieve the assessment forms under his management

     */
    public GetAsseFormListRes retrieveAssessmentForms() throws Exception{
        Employee currentUser = employeeService.getCurrentLoginEmployee();
        List<AssessmentFormSimpleModel> allForms = new ArrayList<>();
        if (currentUser.getCode().toUpperCase().contains("OUTSOURCING")){
            //it's an outsourcing manager
            allForms = outsourcingAssessmentRepository.getAllAssessmentsForms();
        }else{
            allForms = outsourcingAssessmentRepository.getAssessmentsUnderMyApproval(currentUser.getId());
        }
        if (allForms.isEmpty())
            throw new Exception("No assessment forms available.");

        return new GetAsseFormListRes("Succesffully retrieved",false,allForms,false);

    }

    public GetAsseFormListRes retrieveAllApprovedAssessmentForms() throws Exception{
        Employee currentUser = employeeService.getCurrentLoginEmployee();
        if (currentUser.getCode().toUpperCase().contains("OUTSOURCING")){
            //it's an outsourcing manager
            List<AssessmentFormSimpleModel> allForms = outsourcingAssessmentRepository.getAllAssessmentsForms();
            List<AssessmentFormSimpleModel> approvedForms = new ArrayList<>();
            for (AssessmentFormSimpleModel m:allForms){
                if (m.getAssessmentFormStatus().equals(OutsourcingAssessmentStatusEnum.APPROVED)){
                    approvedForms.add(m);
                }
            }
            if (approvedForms.isEmpty()){
                throw new Exception("No approved assessment forms currently.");
            }
            return new GetAsseFormListRes("Succesffully retrieved",false,approvedForms,true);
        }else{
            throw new Exception("You don't have the right to view the assessment forms.");
        }
    }


    public ResponseEntity<GeneralRes> getTemplateForm(){
        try{
            OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.findTemplate();
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

    public ResponseEntity<GeneralRes> createResponseForm(CreateResponseReq createResponseReq, String username){
        try {
            logger.info("Action performed by: "+username+" on "+new Date());
            OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();
            Employee creater = null;
            BJF b = null;
            try{
                creater = employeeService.validateUser(username);
                b = bjfService.validateBJF(createResponseReq.getBjfId());
            }catch (EntityNotFoundException ex){
                logger.info("Cannot find the user!");
                return ResponseEntity.notFound().build();
            }

            if(b.getRelated_outsourcing_assessment() != null){
                OutsourcingAssessment temp = b.getRelated_outsourcing_assessment();
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

            OutsourcingAssessment template = outsourcingAssessmentRepository.findTemplate();
            List<OutsourcingAssessmentSection> sections = template.getSectionList();
            outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_BM_APPROVAL);
            outsourcingAssessment.setBusinessCaseDescription(createResponseReq.getBusinessCaseDescription());
            OutsourcingAssessment newAssess = outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);
            newAssess.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentRepository,newAssess));

            int j = 0;
            List<OutsourcingAssessmentSection> newSections = new ArrayList<>();
            for (OutsourcingAssessmentSection s : sections) {
                OutsourcingAssessmentSection newS = new OutsourcingAssessmentSection();
                newS.setNumber(j);
                newS = outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                List<OutsourcingAssessmentLine> outsourcingAssessmentLine = s.getOutsourcingAssessmentLines();
                List<OutsourcingAssessmentLine> linesSaved = new ArrayList<>();
                for (int i = 0; i < outsourcingAssessmentLine.size(); i ++) {
                    OutsourcingAssessmentLine o = new OutsourcingAssessmentLine();
                    o.setNumber(i);
                    o.setQuestion(outsourcingAssessmentLine.get(i).getQuestion());
                    o.setAnswer(createResponseReq.getResponses().get(i));
                    o.setComment(createResponseReq.getComments().get(i));
                    o.setCreatedBy(username);
                    o.setCreatedDateTime(new Date());
                    o.setOutsourcingAssessmentSection(newS);
                    o = outsourcingAssessmentLineRepository.saveAndFlush(o);
                    o.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentLineRepository,o));
                    linesSaved.add(o);
                    outsourcingAssessmentLineRepository.saveAndFlush(o);
                }
                newS.setOutsourcingAssessmentLines(linesSaved);
                newS.setOutsourcingAssessment(newAssess);
                newS.setCreatedBy(username);
                newS.setCreatedDateTime(new Date());
                newS.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentLineRepository,newS));
                newS = outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                newSections.add(newS);
                outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                j ++;
            }
            newAssess.setSectionList(newSections);
            newAssess.setCreatedBy(username);
            newAssess.setCreatedDateTime(new Date());
            newAssess = outsourcingAssessmentRepository.saveAndFlush(newAssess);

            newAssess.setRelated_BJF(b);
            b.setRelated_outsourcing_assessment(newAssess);

            newAssess = outsourcingAssessmentRepository.saveAndFlush(newAssess);
            bjfRepository.saveAndFlush(b);

            Employee approverA = creater.getManager();
            createApprovalTicket(creater.getUserName(), approverA, newAssess,"Please review and approve the outsourcing assessment form.");
            mailService.sendOscAssForm("noreply@gmail.com", "hangzhipang@u.nus.edu", "test the outsourcing form", "www.google.com", "xxxTitleA",  newAssess);
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

            Employee creater = employeeService.validateUser(username);

            if(creater == null) {
                logger.info("Cannot find the user!");
                return ResponseEntity.notFound().build();
            }

            OutsourcingAssessment temp = outsourcingAssessmentRepository.findTemplate();
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
            newAssess.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentRepository,newAssess));

            Integer i = 0;
            for (OutsourcingAssessmentSection s : sections) {
                OutsourcingAssessmentSection newS = outsourcingAssessmentSectionRepository.saveAndFlush(s);
                newS.setNumber(i);
                newS = outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                List<OutsourcingAssessmentLine> outsourcingAssessmentLine = s.getOutsourcingAssessmentLines();
                List<OutsourcingAssessmentLine> linesSaved = new ArrayList<>();
                Integer j = 0;
                for (OutsourcingAssessmentLine o : outsourcingAssessmentLine) {
                    o.setNumber(j);
                    o.setCreatedBy(username);
                    o.setCreatedDateTime(new Date());
                    o.setOutsourcingAssessmentSection(newS);
                    outsourcingAssessmentLineRepository.saveAndFlush(o);
                    o.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentLineRepository,o));
                    linesSaved.add(outsourcingAssessmentLineRepository.saveAndFlush(o));
                    j ++;
                }
                newS.setOutsourcingAssessmentLines(linesSaved);
                newS.setOutsourcingAssessment(newAssess);
                newS.setCreatedBy(username);
                newS.setCreatedDateTime(new Date());
                newS.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingAssessmentLineRepository,newS));
                outsourcingAssessmentSectionRepository.saveAndFlush(newS);
                i ++;
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
                    form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.PENDING_OUTSOURCING_APPROVAL);
                }
                else if(!isBMApproval && form.getOutsourcingAssessmentStatus() == OutsourcingAssessmentStatusEnum.PENDING_OUTSOURCING_APPROVAL) {
                    form.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.APPROVED);
                    String receiver = employeeService.validateUser(form.getCreatedBy()).getEmail();
                    mailService.sendGeneralEmail(senderEmailAddr, receiver, "Your Outsourcing Assessment Form Has Been Approved!", "Outsourcing Assessment Form "+form.getCode()+" has been successfully approved!");
                }
                else {
                    logger.error("approve type is BM Approval: "+isBMApproval+" is not consistent with the pending status: "+form.getOutsourcingAssessmentStatus()+"!");
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
        Employee requesterEntity = employeeService.validateUser(requesterUsername);
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
        String to = employeeService.validateUser(toUsername).getEmail();
        String subject = "Reminder: Please Complete the Annual Outsourcing Assessment Form";
        Map<String, Object> model = new HashMap<>();

        String link = "http://localhost:3000/api/assessmentForm/getDetails/"+outsourcing.getId();
        model.put("link", link);
        return new Mail(from, to, subject, model);
    }

    public Boolean validateAssessmentFormId(String id){
        Optional<OutsourcingAssessment> optionalOutsourcingAssessment = outsourcingAssessmentRepository.findUndeletedAssessmentFormById(id);
        if (!optionalOutsourcingAssessment.isPresent()) {
            return false;
        }else{
            return true;
        }
    }
}
