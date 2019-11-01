package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.TravelForm;
import capstone.is4103capstone.finance.Repository.TravelFormRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.TravelModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailService;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.TravelTrainingTypeEnum;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TravelService {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CostCenterService costCenterService;
    @Autowired
    TravelFormRepository travelFormRepository;
    @Autowired
    MailService mailService;

    public TTFormResponse createTravelPlan(CreateTravelRequest req) throws Exception{
        TravelForm form = new TravelForm();
        Employee requester = employeeService.getCurrentLoginEmployee();
        CostCenter costCenter = costCenterService.validateCostCenter(req.getCostCenter());
        form.setDestCity(req.getDestCity());
        form.setDestCountry(req.getDestCountry());
        form.setRequestDescription(req.getDescription());
        form.setAdditionalInfo(req.getAdditionalInfo()==null?"":req.getAdditionalInfo());
        form.setTravelType(req.getIsAdhoc()? TravelTrainingTypeEnum.ADHOC:TravelTrainingTypeEnum.BUDGETD);
        System.out.println(req.getIsAdhoc());

        form.setRequester(requester);
        form.setApprover(requester.getManager());
        form.setCostCenter(costCenter);
        form.setObjectName("Travel Plan: "+requester.getFullName()+Tools.dateFormatter.format(new Date()));
        form.setCurrency(req.getCurrencyCode());
        form.setEstimatedBudget(req.getEstimatedBudget());
        form.setCreatedBy(requester.getUserName());
        try{
            form.setEndDate(Tools.dateFormatter.parse(req.getEndDate()));
            form.setStartDate(Tools.dateFormatter.parse(req.getStartDate()));
        }catch (ParseException pe){
            throw new ParseException("Date format incorrect.",pe.getErrorOffset());
        }

        form = travelFormRepository.save(form);
        String startDateStr = "" + Long.valueOf(form.getStartDate().getTime()/1000);
        String code = EntityCodeHPGeneration.getCode(travelFormRepository,form,startDateStr);
        form.setCode(code);
        form.setHierachyPath(EntityCodeHPGeneration.setHP(costCenter,form));
        TravelModel model = new TravelModel(form,true);

        //TODO: difference between adhoc and budgted: the way dealing with budgeting should be different;  detailed deal after approved;
        ApprovalTicketService.createTicketAndSendEmail(requester, requester.getManager(),form,form.getRequestDescription(), ApprovalTypeEnum.TRAVEL);

        return new TTFormResponse("Successfully created travel plan",false,model);
    }

    public TTListResponse retrieveTravelPlansByUser(String usernameOrId) throws Exception{
        Employee requester = employeeService.validateUser(usernameOrId);
        List<TravelForm> myTravelPlans = travelFormRepository.getTravelPlanByRequester_Id(requester.getId());
        return new TTListResponse("Successfully retrieved",false,assembleModelList(myTravelPlans));
    }

    public TTListResponse retrieveTravelPlanByApprover(String usernameOrId) throws Exception{
        Employee approver = employeeService.validateUser(usernameOrId);
        List<TravelForm> travelsOfMyApproval = travelFormRepository.getTravelFormByApprover_Id(approver.getId());
        return new TTListResponse("Successfully retrieved",false,assembleModelList(travelsOfMyApproval));
    }

    private List<TravelModel> assembleModelList(List<TravelForm> forms) throws Exception{
        List<TravelModel> models = new ArrayList<>();
        for (TravelForm f: forms){
            if (f.getDeleted())
                continue;
            models.add(new TravelModel(f,true));
        }
        if (models.isEmpty())
            throw new Exception("No travel plans under the user");
        return models;
    }

    public TTFormResponse getTravelPlanDetails(String planId) throws Exception{
        Optional<TravelForm> tOps = travelFormRepository.findById(planId);
        if (!tOps.isPresent())
            throw new EntityNotFoundException("Travel Plan with id ["+planId+"] not found.");
        try {
            TravelModel travelModel = new TravelModel(tOps.get());
            return new TTFormResponse("Successfully retrieved travel plan",false,travelModel);
        }catch (NullPointerException e){
            throw new NullPointerException("[internal error] Travel Plan in the database is not valid.");
        }
    }

    public void travelPlanApproval(ApprovalForRequest ticket){
        //can be reject/approve
        TravelForm t = validateTravel(ticket.getRequestedItemId());
        t.setApprovalStatus(ticket.getApprovalStatus());
        boolean isApproved = ticket.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED);
        if (isApproved){
            t.setApprovalStatus(ApprovalStatusEnum.APPROVED);
            oncePlanApproved(t);
        }else {
            t.setApprovalStatus(ApprovalStatusEnum.REJECTED);
        }
        travelFormRepository.save(t);
        mailService.sendGeneralEmailDefaultEmail(t.getRequester().getEmail(),"Travel Request["+t.getCode()+"] is "+ticket.getApprovalStatus(),
        "Your travel request is "+ticket.getApprovalStatus()
        );
    }

    //TODO: backend processing of approved ticket
    private void oncePlanApproved(TravelForm t){

    }

    public TravelForm validateTravel(String idOrCode) throws EntityNotFoundException {

        TravelForm e = travelFormRepository.getOne(idOrCode);
        if (e != null)
            if (!e.getDeleted())
                return e;
            else
                throw new EntityNotFoundException("Travel Request already deleted.");

        Optional<TravelForm> t = travelFormRepository.getTravelFormByCode(idOrCode);
        if (t.isPresent())
            if (!t.get().getDeleted())
                return t.get();
            else
                throw new EntityNotFoundException("Travel Request already deleted.");

        throw new EntityNotFoundException("Travel Request code or id not valid");
    }
}
