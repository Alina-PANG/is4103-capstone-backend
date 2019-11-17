package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.enums.TrainingTypeEnum;
import capstone.is4103capstone.entities.finance.TrainingForm;
import capstone.is4103capstone.finance.Repository.TrainingFormRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.TrainingModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTrainingRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailService;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.TravelTrainingTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CostCenterService costCenterService;
    @Autowired
    TrainingFormRepository trainingFormRepository;
    @Autowired
    MailService mailService;

    public TTFormResponse createTrainingPlan(CreateTrainingRequest req) throws Exception{
        TrainingForm form = new TrainingForm();
        Employee requester = employeeService.getCurrentLoginEmployee();
        CostCenter costCenter = costCenterService.validateCostCenter(req.getCostCenter());

        form.setTargetAudience(req.getTargetAudience());
        form.setTrainerCompany(req.getTrainingCompany() == null? "":req.getTrainingCompany());
        form.setTrainerEmail(req.getTrainerEmail() == null? "":req.getTrainerEmail());
        form.setTrainerType(req.getIsInternal()? TrainingTypeEnum.INTERNAL:TrainingTypeEnum.EXTERNAL);
        form.setTrainingLocation(req.getTrainingLocation());
        form.setTrainingTitle(req.getTrainingTitle());
        form.setTrainerName(req.getTrainerName());


        form.setRequestDescription(req.getDescription());
        form.setAdditionalInfo(req.getAdditionalInfo()==null?"":req.getAdditionalInfo());
        form.setTrainingType(req.getIsAdhoc()? TravelTrainingTypeEnum.ADHOC:TravelTrainingTypeEnum.BUDGETED);
        System.out.println(req.getIsAdhoc());

        form.setRequester(requester);
        form.setApprover(requester.getManager());
        form.setCostCenter(costCenter);
        form.setObjectName("Training Plan: "+requester.getFullName()+ Tools.dateFormatter.format(new Date()));
        form.setCurrency(req.getCurrencyCode());
        form.setEstimatedBudget(req.getEstimatedBudget());
        form.setCreatedBy(requester.getUserName());
        try{
            form.setEndDate(Tools.dateFormatter.parse(req.getEndDate()));
            form.setStartDate(Tools.dateFormatter.parse(req.getStartDate()));
        }catch (ParseException pe){
            throw new ParseException("Date format incorrect.",pe.getErrorOffset());
        }

        form = trainingFormRepository.save(form);
        String startDateStr = "" + Long.valueOf(form.getStartDate().getTime()/1000);
        String code = EntityCodeHPGeneration.getCode(trainingFormRepository,form,startDateStr);
        form.setCode(code);
        TrainingModel model = new TrainingModel(form,true);
        form.setHierachyPath(EntityCodeHPGeneration.setHP(costCenter,form));

        //TODO: difference between adhoc and budgted: the way dealing with budgeting should be different;  detailed deal after approved;
        ApprovalTicketService.createTicketAndSendEmail(requester, requester.getManager(),form,form.getRequestDescription(), ApprovalTypeEnum.TRAINING);

        return new TTFormResponse("Successfully created training plan",false,model);
    }

    public TTListResponse retrieveTrainingPlansByUser(String usernameOrId) throws Exception{
        Employee requester = employeeService.validateUser(usernameOrId);
        List<TrainingForm> myTrainingPlans = trainingFormRepository.getByRequester_Id(requester.getId());

        return new TTListResponse("Successfully retrieved",false,assembleModelList(myTrainingPlans));
    }

    public TTListResponse retrieveTrainingPlanByApprover(String usernameOrId) throws Exception{
        Employee requester = employeeService.validateUser(usernameOrId);
        List<TrainingForm> myApprovals = trainingFormRepository.getByApprover_Id(requester.getId());
        return new TTListResponse("Successfully retrieved",false,assembleModelList(myApprovals));
    }

    private List<TrainingModel> assembleModelList(List<TrainingForm> forms) throws Exception{
        List<TrainingModel> models = new ArrayList<>();
        for (TrainingForm f: forms){
            if (f.getDeleted())
                continue;
            models.add(new TrainingModel(f,true));
        }
        if (models.isEmpty())
            throw new Exception("No travel plans under the user");
        return models;
    }


    public TrainingModel getTrainingPlanDetails(String planId){
        Optional<TrainingForm> tOps = trainingFormRepository.findById(planId);
        if (!tOps.isPresent())
            throw new EntityNotFoundException("Travel Plan with id ["+planId+"] not found.");
        try {
            TrainingModel trainingModel = new TrainingModel(tOps.get());
            return trainingModel;
        }catch (NullPointerException e){
            throw new NullPointerException("[internal error] Training Plan in the database is not valid.");
        }
    }

    public void trainingPlanApproval(ApprovalForRequest ticket){
//can be reject/approve
        TrainingForm t = validateTraining(ticket.getRequestedItemId());
        t.setApprovalStatus(ticket.getApprovalStatus());
        boolean isApproved = ticket.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED);
        if (isApproved){
            t.setApprovalStatus(ApprovalStatusEnum.APPROVED);
            oncePlanApproved(t);
        }else {
            t.setApprovalStatus(ApprovalStatusEnum.REJECTED);
        }
        trainingFormRepository.save(t);
        mailService.sendGeneralEmailDefaultEmail(t.getRequester().getEmail(),"Travel Request["+t.getCode()+"] is "+ticket.getApprovalStatus(),
                "Your travel request is "+ticket.getApprovalStatus()
        );
    }

    private void oncePlanApproved(TrainingForm t){

    }

    public TrainingForm validateTraining(String idOrCode) throws EntityNotFoundException {

        TrainingForm e = trainingFormRepository.getOne(idOrCode);
        if (e != null)
            if (!e.getDeleted())
                return e;
            else
                throw new EntityNotFoundException("Training Request already deleted.");

        Optional<TrainingForm> t = trainingFormRepository.getByCode(idOrCode);
        if (t.isPresent())
            if (!t.get().getDeleted())
                return t.get();
            else
                throw new EntityNotFoundException("Training Request already deleted.");

        throw new EntityNotFoundException("Training Request code or id not valid");
    }

}
