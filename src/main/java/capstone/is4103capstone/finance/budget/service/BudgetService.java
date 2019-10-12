package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.BudgetLineItemModel;
import capstone.is4103capstone.finance.budget.model.res.BudgetModel;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetListRes;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.stereotype.Service
public class BudgetService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    CostCenterRepository costCenterRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    FinanceEntityCodeHPGenerator g = new FinanceEntityCodeHPGenerator();
    private String generateCode(JpaRepository repo, DBEntityTemplate entity){
        try {
            return g.generateCode(repo,entity);
        }catch (RepositoryEntityMismatchException e){
            return null;
        } catch (Exception e) {
            return null;
        }
    }

//    @Transactional
    private List<PlanLineItem> saveLineItem(List<PlanLineItem> items, Plan plan) throws Exception{
        List<PlanLineItem> newItems = new ArrayList<>();
        for(PlanLineItem i: items){
            i.setPlanBelongsTo(plan);
            i.setHierachyPath(g.getPlanItemHP(i));
            newItems.add(planLineItemRepository.saveAndFlush(i));
            generateCode(planLineItemRepository,i);
        }
        return newItems;
    }

    private void deletePlanItem(List<PlanLineItem> items) throws Exception{
        for(PlanLineItem i: items){
            i.setDeleted(true);
            planLineItemRepository.saveAndFlush(i);
        }
    }

    public GeneralRes createBudget(CreateBudgetReq createBudgetReq, String id){
        logger.info("Start to create budget...");
        logger.info("Username: "+createBudgetReq.getUsername());
        logger.info("IsBudget: "+createBudgetReq.isBudget());
        logger.info("Item Size: "+createBudgetReq.getItems().size());
        try{
            int version = 1;
            if (!isValidPlan(createBudgetReq.getItems()))
                throw new Exception("Submitted budget plan not valid, duplicate service fields!");
            CostCenter cc = costCenterRepository.findCostCenterByCode(createBudgetReq.getCostCenterCode());


            if(id != null){
                Plan existingPlan = planRepository.getOne(id);
                existingPlan.setDeleted(true);
                planRepository.saveAndFlush(existingPlan);
                deletePlanItem(existingPlan.getLineItems());
                version = existingPlan.getVersion() + 1;
            }
            Plan newPlan = new Plan();
            if(createBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.PENDING_BM_APPROVAL);
            }
            else{
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.DRAFT);
            }
            if(createBudgetReq.isBudget()){
                List<Plan> plansOfCC = cc.getPlans();
                for (Plan p:plansOfCC){
                    if (p.getForYear().equals(createBudgetReq.getYear()) && p.getPlanType().equals(BudgetPlanEnum.BUDGET) && (!p.getDeleted() && !p.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.REJECTED))){
                        throw new Exception("Budget plan for year "+createBudgetReq.getYear()+" of cost center ["+cc.getCode()+"] already exists!");
                    }
                }
                newPlan.setPlanType(BudgetPlanEnum.BUDGET);
                newPlan.setObjectName(createBudgetReq.getYear()+"-BUDGET");
            }
            else{//reforecast
                List<Plan> plansOfCC = cc.getPlans();
                for (Plan p:plansOfCC){
                    if (p.getForYear().equals(createBudgetReq.getYear()) && p.getPlanType().equals(BudgetPlanEnum.REFORECAST) && (!p.getDeleted() && !p.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.REJECTED))){
                        throw new Exception("Reforecast plan for year "+createBudgetReq.getYear()+", month "+p.getForMonth()+" of cost center ["+cc.getCode()+"] already exists!");
                    }
                }
                newPlan.setPlanType(BudgetPlanEnum.REFORECAST);
//                if (createBudgetReq.getMonth() == null){
//                    throw new Exception("You must select the month of reforecast!");
//                }
                createBudgetReq.setMonth((new Date()).getMonth()+1);
                newPlan.setObjectName(createBudgetReq.getYear()+"-"+new SimpleDateFormat("MMM").format(new Date())+"-REFORECAST");
            }


            newPlan.setCostCenter(cc);
            newPlan.setPlanDescription(createBudgetReq.getDescription());
            newPlan.setVersion(version);
            newPlan.setForMonth(createBudgetReq.getMonth());
            newPlan.setForYear(createBudgetReq.getYear());
            newPlan.setCreatedBy(createBudgetReq.getUsername());

            newPlan = planRepository.saveAndFlush(newPlan);
            if(createBudgetReq.getItems() != null && createBudgetReq.getItems().size() != 0){
                    List<PlanLineItem> newItems = saveLineItem(createBudgetReq.getItems(), newPlan);
                    newPlan.setLineItems(newItems);
            }


            newPlan.setHierachyPath(g.getPlanHP(newPlan,cc));
            planRepository.saveAndFlush(newPlan);
            generateCode(planRepository,newPlan);

            logger.info("Successully submitted the new plan! -- "+createBudgetReq.getUsername()+" "+new Date());

            //TODO: create approval ticket sending email notification to the approver:
            if (createBudgetReq.getToSubmit()){
                try{
                    createApprovalTicket(createBudgetReq.getUsername(),cc.getBmApprover(),newPlan,"BM Approver please view the budget plan.");
//                    createApprovalTicket(createBudgetReq.getUsername(),cc.getFunctionApprover(),newPlan,"Function approver please view the budget plan.");
                }catch (Exception emailExc){
                }
            }


            return new GeneralRes("Successully "+(createBudgetReq.getToSubmit()?"submit":"save")+" the plan!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes(ex.getMessage(), true);
        }
    }

    @Transactional
    public GetBudgetRes getBudget(String id, String username){
        try{
            logger.info("Getting plan with id "+id+"...");
            Plan p = planRepository.getOne(id);
            if(p == null){
                return new GetBudgetRes("There is no plan in the database with id "+id, true, null);
            }

            //Check whether the user has approval right to it.
            boolean[] approvalRight = getApprovalRight(p,username);
            boolean bmApprover = approvalRight[0];
            boolean functionApprover = approvalRight[1];


            List<BudgetLineItemModel> items = new ArrayList<>();
            BudgetModel plan = new BudgetModel(p.getForYear(), p.getForMonth(), p.getObjectName(), id, p.getBudgetPlanStatus(),p.getPlanType());
            plan.setCreateBy(p.getCreatedBy());
            plan.setDescription(p.getPlanDescription());
            plan.setCostCenterCode(p.getCostCenter().getCode());
            plan.setTeamCode(p.getCostCenter().getTeam().getCode());
            plan.setCountryCode(p.getCostCenter().getTeam().getBusinessUnit().getFunction().getCountry().getCode());
            plan.setFunctionCode(p.getCostCenter().getTeam().getBusinessUnit().getFunction().getCode());

            plan.setLastModifiedTime(datetimeFormatter.format(p.getLastModifiedDateTime() == null ? p.getCreatedDateTime() : p.getLastModifiedDateTime()));

            for(int i = 0; i < p.getLineItems().size(); i ++){
                PlanLineItem item = p.getLineItems().get(i);
                Service m = serviceRepository.findServiceByCode(item.getserviceCode());
                System.out.println(item.getserviceCode());
                BudgetSub2 budgetSub2 = m.getBudgetSub2();
                System.out.println("sub2: "+budgetSub2.getCode());
                BudgetSub1 budgetSub1 = budgetSub2.getBudgetSub1();
                System.out.println("sub1: "+budgetSub1.getCode());
                BudgetCategory budgetCategory = budgetSub1.getBudgetCategory();
                System.out.println("category: "+budgetCategory.getCode());
                items.add(new BudgetLineItemModel(item.getId(), budgetCategory.getObjectName(), budgetCategory.getCode(), budgetSub1.getObjectName(), budgetSub1.getCode(), budgetSub2.getObjectName(), budgetSub2.getCode(), m.getObjectName(), m.getCode(), item.getBudgetAmount(), item.getCurrencyAbbr(), item.getComment()));
            }

            plan.setItems(items);

            List<ApprovalTicketModel> reviews = ApprovalTicketService.getAllNonPendingTicketsByRequestItem(p);

            return new GetBudgetRes("Successsfully retrieved the plan with id: "+id,false, plan,bmApprover,functionApprover,reviews.isEmpty()? null : reviews.get(reviews.size()-1));
        } catch(Exception ex){
            ex.printStackTrace();
            return new GetBudgetRes(ex.getMessage(), true, null);
        }
    }


    //only get plan with same name for the latest version!!!
    public GetBudgetListRes getBudgetList(String username, String teamId){//, String costcenterId, Integer retrieveType, Integer year){
        //retrieveType: by default: null or 0: all, 1-budget, 2-reforecast
        try{
            List<CostCenter> ccList = costCenterRepository.findCostCentersByTeamId(teamId);
            List<BudgetModel> result = new ArrayList<>();
            for (CostCenter c : ccList){
                List<Plan> plans = planRepository.findByCostCenterId(c.getId());
                //组装好cc返回
                for (Plan p : plans) {
                    if (p.getDeleted() || p.getCreatedBy() == null || p.getBudgetPlanStatus() == null || p.getPlanType() == null)
                        continue;

                    BudgetModel thisPlan = new BudgetModel(p.getForYear(), p.getForMonth(), p.getObjectName(), p.getId(), p.getBudgetPlanStatus(), p.getPlanType());
                    thisPlan.setCostCenterCode(c.getCode());
//                  thisPlan.setTeamCode(c.getTeam().getCode());
//                  thisPlan.setFunctionCode(c.getTeam().getFunction().getCode());
//                  thisPlan.setCountryCode(c.getTeam().getCountry().getCode());
                    thisPlan.setCreateBy(p.getCreatedBy());
                    thisPlan.setLastModifiedTime(datetimeFormatter.format(p.getLastModifiedDateTime() == null ? p.getCreatedDateTime() : p.getLastModifiedDateTime()));
                    result.add(thisPlan);
                }
            }


            return new GetBudgetListRes("Successsfully retrieved plans under the cost center!",false, result);

        }catch (Exception ex){
            return new GetBudgetListRes(ex.getMessage(), true, null);

        }

    }

    public GeneralRes approveBudget(ApproveBudgetReq approveBudgetReq) {
        try {
            Optional<Plan> planOp = planRepository.findById(approveBudgetReq.getPlanId());
            if (!planOp.isPresent()) {
                throw new Exception("Plan Id not found");
            }
            Plan plan = planOp.get();
            if (!plan.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.PENDING_BM_APPROVAL) && !plan.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.PENDING_FUNCTION_APPROVAL)) {
                logger.error("Internal error, a non-pending budget plan goes into approve function");
                throw new Exception("Internal error, a non-pending budget plan goes into approve function");
            }



            if (approveBudgetReq.getApprovalType() == 0) { //0:bm
                return BMApproval(approveBudgetReq, plan);
            } else {
                return functionApproval(approveBudgetReq, plan);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
        }
    }

    //validate the plan items submitted by the user, whether there're any repeatative;
    private boolean isValidPlan(List<PlanLineItem> items){
        Set<String> submittedserviceCodes = new HashSet<String>();
        for (PlanLineItem item: items){
            if (item.getserviceCode() == null){
                return false;
            }
            if (!submittedserviceCodes.add(item.getserviceCode()))
                return false;
        }
        return true;
    }


    private GeneralRes BMApproval(ApproveBudgetReq approveBudgetReq, Plan plan) throws Exception{
        if (!approveBudgetReq.getApproved()){
            plan.setBudgetPlanStatus(BudgetPlanStatusEnum.REJECTED);
            ApprovalTicketService.rejectTicketByEntity(plan,approveBudgetReq.getComment(),approveBudgetReq.getUsername());
        }
        else{
            plan.setBudgetPlanStatus(BudgetPlanStatusEnum.PENDING_FUNCTION_APPROVAL);
            ApprovalTicketService.approveTicketByEntity(plan,approveBudgetReq.getComment(),approveBudgetReq.getUsername());
            createApprovalTicket(approveBudgetReq.getUsername(),plan.getCostCenter().getFunctionApprover(),plan,"BM Approver approved, Function approver please view the budget plan.");
        }

        planRepository.saveAndFlush(plan);
        logger.info("BM APPROVEL Successully "+plan.getBudgetPlanStatus()+" the new plan! -- "+approveBudgetReq.getUsername()+" "+new Date());
        return new GeneralRes("BM APPROVAL Successfully "+plan.getBudgetPlanStatus()+" the plan!", false);

    }
    private GeneralRes functionApproval(ApproveBudgetReq approveBudgetReq, Plan plan) throws Exception{
        if (!approveBudgetReq.getApproved()){
            plan.setBudgetPlanStatus(BudgetPlanStatusEnum.REJECTED);
            ApprovalTicketService.rejectTicketByEntity(plan,approveBudgetReq.getComment(),approveBudgetReq.getUsername());
        }
        else{
            plan.setBudgetPlanStatus(BudgetPlanStatusEnum.APPROVED);
            ApprovalTicketService.approveTicketByEntity(plan,approveBudgetReq.getComment(),approveBudgetReq.getUsername());

        }

        planRepository.saveAndFlush(plan);
        logger.info("BM APPROVEL Successully "+plan.getBudgetPlanStatus()+" the new plan! -- "+approveBudgetReq.getUsername()+" "+new Date());
        return new GeneralRes("BM APPROVAL Successfully "+plan.getBudgetPlanStatus()+" the plan!", false);

    }

    private boolean checkPlanTypeAndYear(Plan p, Integer request, Integer year){
        boolean passRequest = false;
        boolean passYear = false;
        if (request == null || request.equals(0)){
            passRequest = true;
        }
        if (request.equals(1)){
            passRequest = p.getPlanType() == BudgetPlanEnum.BUDGET;
        }else if (request.equals(2)){
            passRequest = p.getPlanType() == BudgetPlanEnum.REFORECAST;
        }

        if (year == 0) {
            passYear = true;
        }else {
            passYear = (year.equals(p.getForYear()));
        }
        return passRequest && passYear;



    }

    private List<CostCenter> getCostCentersByUserAccess(String username){
        return costCenterRepository.findAllNotDeleted();//for now //TODO
    }

    @Transactional
    public boolean[] getApprovalRight(Plan p, String username) throws Exception{
        Optional<CostCenter> thisCCO = costCenterRepository.findById(p.getCostCenter().getId());
        if (!thisCCO.isPresent())
            throw new Exception("Cost center internal error: code not correct");
        CostCenter thisCC = thisCCO.get();
        boolean[] a = new boolean[2];
        if (username.equals(thisCC.getBmApprover().getUserName()))
            a[0] = true;
        else a[0] = false;
        if (username.equals(thisCC.getFunctionApprover().getUserName()))
            a[1] = true;
        else a[1] = false;

        return a;
    }


    private void createApprovalTicket(String requesterUsername, Employee receiver, Plan newPlan, String content){
        Employee requesterEntity = employeeRepository.findEmployeeByUserName(requesterUsername);
        ApprovalTicketService.createTicketAndSendEmail(requesterEntity,receiver,newPlan,content, ApprovalTypeEnum.BUDGETPLAN);
        //TODO: What's the message content?
    }


}

