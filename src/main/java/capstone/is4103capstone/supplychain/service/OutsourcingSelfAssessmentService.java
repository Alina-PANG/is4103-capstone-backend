package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.supplyChain.*;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentQuestionRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.OutsourcingSelfAssessmentModel;
import capstone.is4103capstone.supplychain.model.req.ApproveOutsourcingSelfAssessmentReq;
import capstone.is4103capstone.supplychain.model.req.CreateOutsourcingSelfAssessmentReq;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingSelfAssessmentRes;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingSelfAssessmentsRes;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.OutsourcingSelfAssessmentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutsourcingSelfAssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(OutsourcingSelfAssessmentService.class);

    @Autowired
    OutsourcingService outsourcingService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    OutsourcingSelfAssessmentRepository outsourcingSelfAssessmentRepository;
    @Autowired
    OutsourcingSelfAssessmentQuestionRepository outsourcingSelfAssessmentQuestionRepository;
    @Autowired
    EmployeeRepository employeeRepository;


    public GeneralRes createOutsourcingSelfAssessment(CreateOutsourcingSelfAssessmentReq createOutsourcingSelfAssessmentReq) {
        try {
            OutsourcingSelfAssessment outsourcingSelfAssessment = new OutsourcingSelfAssessment();
            outsourcingSelfAssessment.setAnnualAssessmentDate(createOutsourcingSelfAssessmentReq.getAnnualAssessmentDate());

            if (employeeService.validateEmployeeId(createOutsourcingSelfAssessmentReq.getOutsourcingManagerId())) {
                outsourcingSelfAssessment.setOutsourcingManagerId(createOutsourcingSelfAssessmentReq.getOutsourcingManagerId());
            } else {
                throw new Exception("This is not a valid outsourcing manager ID.");
            }

            if (employeeService.validateEmployeeId(createOutsourcingSelfAssessmentReq.getDesignationId())) {
                outsourcingSelfAssessment.setDesignationId(createOutsourcingSelfAssessmentReq.getDesignationId());
            } else {
                throw new Exception("This is not a valid designation ID.");
            }

            if (employeeService.validateEmployeeId(createOutsourcingSelfAssessmentReq.getFunctionHeadId())) {
                outsourcingSelfAssessment.setFunctionHeadId(createOutsourcingSelfAssessmentReq.getFunctionHeadId());
            } else {
                throw new Exception("This is not a valid function head ID.");
            }

            if (outsourcingService.validateOutsourcingId(createOutsourcingSelfAssessmentReq.getOutsourcingId())) {
                outsourcingSelfAssessment.setOutsourcingId(createOutsourcingSelfAssessmentReq.getOutsourcingId());
            } else {
                throw new Exception("This is not a valid outsourcing ID.");
            }

            outsourcingSelfAssessment.setOutsourcingSelfAssessmentStatus(OutsourcingSelfAssessmentStatusEnum.DRAFT);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcingSelfAssessment.setLastModifiedBy(currentEmployee.getUserName());
            outsourcingSelfAssessment.setCreatedBy(currentEmployee.getUserName());

            //create question record
            for (OutsourcingSelfAssessmentQuestion question : createOutsourcingSelfAssessmentReq.getOutsourcingSelfAssessmentQuestionList()) {
                OutsourcingSelfAssessmentQuestion newQuestion = new OutsourcingSelfAssessmentQuestion();
                newQuestion.setQuestion(question.getQuestion());
                newQuestion.setAnswer(question.getAnswer());
                newQuestion.setComment(question.getComment());
                newQuestion.setCreatedBy(currentEmployee.getUserName());
                newQuestion.setLastModifiedBy(currentEmployee.getUserName());

                outsourcingSelfAssessmentQuestionRepository.saveAndFlush(newQuestion);
                if (newQuestion.getSeqNo() == null) {
                    newQuestion.setSeqNo(new Long(outsourcingSelfAssessmentQuestionRepository.findAll().size()));
                }

                AuthenticationTools.configurePermissionMap(newQuestion);
                newQuestion.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingSelfAssessmentQuestionRepository, newQuestion));
                outsourcingSelfAssessmentQuestionRepository.save(newQuestion);
                outsourcingSelfAssessment.getOutsourcingSelfAssessmentQuestionIdList().add(newQuestion.getId());
            }

            outsourcingSelfAssessment = outsourcingSelfAssessmentRepository.save(outsourcingSelfAssessment);
            if (outsourcingSelfAssessment.getSeqNo() == null) {
                outsourcingSelfAssessment.setSeqNo(new Long(outsourcingSelfAssessmentRepository.findAll().size()));
            }

            AuthenticationTools.configurePermissionMap(outsourcingSelfAssessment);
            outsourcingSelfAssessment.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingSelfAssessmentRepository, outsourcingSelfAssessment));
            outsourcingSelfAssessmentRepository.save(outsourcingSelfAssessment);

            logger.info("Successfully created new outsourcing annual self assessment! -- " + outsourcingSelfAssessment.getId());
            return new GeneralRes("Successfully created new outsourcing annual self assessment!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    public GetOutsourcingSelfAssessmentRes getOutsourcingSelfAssessment(String id){
        try{
            logger.info("Getting outsourcing self assessment by id: " + id);
            OutsourcingSelfAssessment outsourcingSelfAssessment = outsourcingSelfAssessmentRepository.getOne(id);

            if(outsourcingSelfAssessment == null){
                return new GetOutsourcingSelfAssessmentRes("There is no outsourcing self assessment in the database with id " + id, true, null);
            }
            else if(outsourcingSelfAssessment.getDeleted()){
                return new GetOutsourcingSelfAssessmentRes("This outsourcing self assessment is deleted", true, null);
            }
            else{
                OutsourcingSelfAssessmentModel outsourcingSelfAssessmentModel = transformToOutsourcingSelfAssessmentModelDetails(outsourcingSelfAssessment);
                return new GetOutsourcingSelfAssessmentRes("Successfully retrieved the outsourcing self assessment with id " + id, false, outsourcingSelfAssessmentModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetOutsourcingSelfAssessmentRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetOutsourcingSelfAssessmentsRes getAllOutsourcingSelfAssessmentsByOutsourcingId(String outsourcingId){
        try {
            logger.info("Getting all outsourcing self assessments by outsourcing ID");
            List<OutsourcingSelfAssessmentModel> returnList = new ArrayList<>();
            List<OutsourcingSelfAssessment> outsourcingSelfAssessmentList = outsourcingSelfAssessmentRepository.findOutsourcingSelfAssessmentsByOutsourcingId(outsourcingId);

            for(OutsourcingSelfAssessment outsourcingSelfAssessment: outsourcingSelfAssessmentList){
                if(outsourcingSelfAssessment.getDeleted()){
                    continue;
                }

                OutsourcingSelfAssessmentModel outsourcingSelfAssessmentModel = transformToOutsourcingSelfAssessmentModelOverview(outsourcingSelfAssessment);
                returnList.add(outsourcingSelfAssessmentModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No outsourcing self assessment available.");
            }

            return new GetOutsourcingSelfAssessmentsRes("Successfully retrieved all outsourcing  self assessments", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetOutsourcingSelfAssessmentsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateOutsourcingSelfAssessment(CreateOutsourcingSelfAssessmentReq updateOutsourcingSelfAssessmentReq, String id) {
        try {
            OutsourcingSelfAssessment outsourcingSelfAssessment = outsourcingSelfAssessmentRepository.getOne(id);

            if(outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.APPROVED)
            || outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.PENDING_APPROVAL)){
                throw new Exception("The outsourcing self assessment is PENDING_APPROVAL or APPROVED. You are not allowed to update it!");
            }

            if(updateOutsourcingSelfAssessmentReq.getAnnualAssessmentDate() != null){
                outsourcingSelfAssessment.setAnnualAssessmentDate(updateOutsourcingSelfAssessmentReq.getAnnualAssessmentDate());
            }

            if(updateOutsourcingSelfAssessmentReq.getOutsourcingManagerId() != null){
               if(employeeService.validateEmployeeId(updateOutsourcingSelfAssessmentReq.getOutsourcingManagerId())) {
                   outsourcingSelfAssessment.setOutsourcingManagerId(updateOutsourcingSelfAssessmentReq.getOutsourcingManagerId());
               }else{
                   throw new Exception("This is not a valid outsourcing manager ID.");
               }
            }

            if(updateOutsourcingSelfAssessmentReq.getDesignationId() != null){
                if(employeeService.validateEmployeeId(updateOutsourcingSelfAssessmentReq.getDesignationId())){
                    outsourcingSelfAssessment.setDesignationId(updateOutsourcingSelfAssessmentReq.getDesignationId());
                }else{
                    throw new Exception("This is not a valid designation ID.");
                }
            }

            if(updateOutsourcingSelfAssessmentReq.getFunctionHeadId() != null){
                if(employeeService.validateEmployeeId(updateOutsourcingSelfAssessmentReq.getFunctionHeadId())) {
                    outsourcingSelfAssessment.setFunctionHeadId(updateOutsourcingSelfAssessmentReq.getFunctionHeadId());
                }else{
                    throw new Exception("This is not a valid function head ID.");
                }
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcingSelfAssessment.setLastModifiedBy(currentEmployee.getUserName());

            if(updateOutsourcingSelfAssessmentReq.getOutsourcingSelfAssessmentQuestionList() != null
                    && updateOutsourcingSelfAssessmentReq.getOutsourcingSelfAssessmentQuestionList().size() != 0){

                outsourcingSelfAssessment.setOutsourcingSelfAssessmentQuestionIdList(new ArrayList<>());

                for (OutsourcingSelfAssessmentQuestion question : updateOutsourcingSelfAssessmentReq.getOutsourcingSelfAssessmentQuestionList()) {
                    OutsourcingSelfAssessmentQuestion newQuestion = new OutsourcingSelfAssessmentQuestion();
                    newQuestion.setQuestion(question.getQuestion());
                    newQuestion.setAnswer(question.getAnswer());
                    newQuestion.setComment(question.getComment());
                    newQuestion.setCreatedBy(currentEmployee.getUserName());
                    newQuestion.setLastModifiedBy(currentEmployee.getUserName());

                    outsourcingSelfAssessmentQuestionRepository.saveAndFlush(newQuestion);
                    if (newQuestion.getSeqNo() == null) {
                        newQuestion.setSeqNo(new Long(outsourcingSelfAssessmentQuestionRepository.findAll().size()));
                    }

                    AuthenticationTools.configurePermissionMap(newQuestion);
                    newQuestion.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingSelfAssessmentQuestionRepository, newQuestion));
                    outsourcingSelfAssessmentQuestionRepository.save(newQuestion);
                    outsourcingSelfAssessment.getOutsourcingSelfAssessmentQuestionIdList().add(newQuestion.getId());
                }
            }

            outsourcingSelfAssessmentRepository.saveAndFlush(outsourcingSelfAssessment);
            logger.info("Successfully created new outsourcing annual self assessment! -- " + outsourcingSelfAssessment.getId());
            return new GeneralRes("Successfully updated the outsourcing self assessment!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    public OutsourcingSelfAssessmentModel transformToOutsourcingSelfAssessmentModelDetails(OutsourcingSelfAssessment outsourcingSelfAssessment) {
        EmployeeModel outsourcingManager = null;
        EmployeeModel designation = null;
        EmployeeModel functionHead = null;
        Boolean canUpdateAndRequest = false;
        List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList = new ArrayList<>();

        if (outsourcingSelfAssessment.getOutsourcingManagerId() != null) {
            Employee o = employeeRepository.getOne(outsourcingSelfAssessment.getOutsourcingManagerId());
            outsourcingManager = new EmployeeModel(o);
        }
        if (outsourcingSelfAssessment.getDesignationId() != null) {
            Employee d = employeeRepository.getOne(outsourcingSelfAssessment.getDesignationId());
            designation = new EmployeeModel(d);
        }
        if (outsourcingSelfAssessment.getFunctionHeadId() != null) {
            Employee f = employeeRepository.getOne(outsourcingSelfAssessment.getFunctionHeadId());
            functionHead = new EmployeeModel(f);
        }

        if (outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.DRAFT)
                || outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.REJECTED)) {
            canUpdateAndRequest = true;
        }

        for(String questionId : outsourcingSelfAssessment.getOutsourcingSelfAssessmentQuestionIdList()){
            OutsourcingSelfAssessmentQuestion question = outsourcingSelfAssessmentQuestionRepository.getOne(questionId);
            outsourcingSelfAssessmentQuestionList.add(question);
        }

        OutsourcingSelfAssessmentModel result = new OutsourcingSelfAssessmentModel(
                outsourcingSelfAssessment.getCode(), outsourcingSelfAssessment.getId(), outsourcingSelfAssessment.getSeqNo(),
                outsourcingManager, designation, functionHead, outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus(),
                outsourcingSelfAssessment.getAnnualAssessmentDate(), outsourcingSelfAssessmentQuestionList, canUpdateAndRequest);

        return result;
    }

    //faster to load overview information
    public OutsourcingSelfAssessmentModel transformToOutsourcingSelfAssessmentModelOverview(OutsourcingSelfAssessment outsourcingSelfAssessment) {
        EmployeeModel outsourcingManager = null;
        EmployeeModel designation = null;
        EmployeeModel functionHead = null;
        Boolean canUpdateAndRequest = false;
        List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList = new ArrayList<>();

        if (outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.DRAFT)
                || outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.REJECTED)) {
            canUpdateAndRequest = true;
        }

        OutsourcingSelfAssessmentModel result = new OutsourcingSelfAssessmentModel(
                outsourcingSelfAssessment.getCode(), outsourcingSelfAssessment.getId(), outsourcingSelfAssessment.getSeqNo(),
                outsourcingManager, designation, functionHead, outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus(),
                outsourcingSelfAssessment.getAnnualAssessmentDate(), outsourcingSelfAssessmentQuestionList, canUpdateAndRequest);

        return result;
    }

    public GeneralRes createApprovalTicket(String outsourcingSelfAssessmentId, String content){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee requester = (Employee) auth.getPrincipal();

            OutsourcingSelfAssessment outsourcingSelfAssessment = outsourcingSelfAssessmentRepository.getOne(outsourcingSelfAssessmentId);
            String approverId = outsourcingSelfAssessment.getFunctionHeadId();
            Employee approver = employeeRepository.getOne(approverId);

            OutsourcingSelfAssessmentStatusEnum status = outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus();

            if((status.equals(OutsourcingSelfAssessmentStatusEnum.APPROVED) || status.equals(OutsourcingSelfAssessmentStatusEnum.PENDING_APPROVAL))){
                logger.error("This outsourcing self assessment has been APPROVED/PENDING_APPROVAL. Cannot request for approval again!");
                throw new Exception("This outsourcing self assessment has been APPROVED/PENDING_APPROVAL. Cannot request for approval again!");
            }

            outsourcingSelfAssessment.setOutsourcingSelfAssessmentStatus(OutsourcingSelfAssessmentStatusEnum.PENDING_APPROVAL);
            outsourcingSelfAssessmentRepository.saveAndFlush(outsourcingSelfAssessment);
            ApprovalTicketService.createTicketAndSendEmail(requester, approver, outsourcingSelfAssessment, content, ApprovalTypeEnum.OUTSOURCING_SELF_ASSESSMENT);

            return new GeneralRes("Request for approval has been sent Successfully!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
        }
    }

    @Transactional
    public boolean getApprovalRight(OutsourcingSelfAssessment outsourcingSelfAssessment, String username) throws Exception{
        String approverId = outsourcingSelfAssessment.getFunctionHeadId();
        Employee approver = employeeRepository.getOne(approverId);

        if (approver.getUserName().equals(username)){
            return true;
        }else {
            return false;
        }
    }

    public GeneralRes approveOutsourcingSelfAssessment(ApproveOutsourcingSelfAssessmentReq approveOutsourcingSelfAssessmentReq) {
        try {
            Optional<OutsourcingSelfAssessment> outsourcingSelfAssessmentOp = outsourcingSelfAssessmentRepository.findById(approveOutsourcingSelfAssessmentReq.getOutsourcingSelfAssessmentId());
            if (!outsourcingSelfAssessmentOp.isPresent()) {
                throw new Exception("Outsourcing self assessment Id not found");
            }
            OutsourcingSelfAssessment outsourcingSelfAssessment = outsourcingSelfAssessmentOp.get();

            if (!outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus().equals(OutsourcingSelfAssessmentStatusEnum.PENDING_APPROVAL)) {
                logger.error("Internal error, a non-pending outsourcing self assessment goes into approve function");
                throw new Exception("Internal error, a non-pending outsourcing self assessment goes into approve function");
            }

            if(!getApprovalRight(outsourcingSelfAssessment, approveOutsourcingSelfAssessmentReq.getUsername())){
                logger.error("This user is not the approver and does not have the right to approve this outsourcing self assessment");
                throw new Exception("This user is not the approver and does not have the right to approve this outsourcing self assessment");
            }

            if (!approveOutsourcingSelfAssessmentReq.getApproved()){
                outsourcingSelfAssessment.setOutsourcingSelfAssessmentStatus(OutsourcingSelfAssessmentStatusEnum.REJECTED);
                ApprovalTicketService.rejectTicketByEntity(outsourcingSelfAssessment, approveOutsourcingSelfAssessmentReq.getComment(),approveOutsourcingSelfAssessmentReq.getUsername());
            }
            else{
                outsourcingSelfAssessment.setOutsourcingSelfAssessmentStatus(OutsourcingSelfAssessmentStatusEnum.APPROVED);
                ApprovalTicketService.approveTicketByEntity(outsourcingSelfAssessment, approveOutsourcingSelfAssessmentReq.getComment(),approveOutsourcingSelfAssessmentReq.getUsername());
            }

            outsourcingSelfAssessmentRepository.saveAndFlush(outsourcingSelfAssessment);
            logger.info("Approver Successfully: "+outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus()+" the outsourcing self assessment! -- "+approveOutsourcingSelfAssessmentReq.getUsername()+" "+new Date());
            return new GeneralRes("Approver Successfully "+outsourcingSelfAssessment.getOutsourcingSelfAssessmentStatus()+" the outsourcing self assessment!", false);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
        }
    }
}