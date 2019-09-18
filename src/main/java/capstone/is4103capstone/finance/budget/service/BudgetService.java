package capstone.is4103capstone.finance.budget.service;



import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetListRes;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BudgetService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    CostCenterRepository costCenterRepository;

    private List<PlanLineItem> saveLineItem(List<PlanLineItem> items, Plan plan) throws Exception{
        List<PlanLineItem> newItems = new ArrayList<>();
        for(PlanLineItem i: items){
            i.setPlanBelongsTo(plan);
            newItems.add(planLineItemRepository.saveAndFlush(i));
        }
        return newItems;
    }

    private void deletePlanLineItem(Plan plan) throws Exception{
        List<PlanLineItem> items = plan.getLineItems();
        plan.setLineItems(null);
        for(PlanLineItem i: items){
            planLineItemRepository.delete(i);
        }
    }

    public GeneralRes createBudget(CreateBudgetReq createBudgetReq){
        logger.info("Start to create budget...");
        logger.info("Username: "+createBudgetReq.getUsername());
        logger.info("IsBudget: "+createBudgetReq.isBudget());
        logger.info("Item Size: "+createBudgetReq.getItems().size());
        try{
            Plan newPlan = new Plan();
            if(createBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.SUBMITTED);
            }
            else{
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.DRAFT);
            }
            if(createBudgetReq.isBudget()){
                newPlan.setPlanType(BudgetPlanEnum.BUDGET);}
            else{
                newPlan.setPlanType(BudgetPlanEnum.REFORECAST);
            }
            CostCenter cc = costCenterRepository.findCostCenterByCode(createBudgetReq.getCostCenterCode());
            newPlan.setCostCenter(cc);
            newPlan.setPlanDescription(createBudgetReq.getDescription());
            newPlan.setVersion(1);
            newPlan.setForYear(createBudgetReq.getYear());
            newPlan.setCreatedBy(createBudgetReq.getUsername());
            newPlan.setCreatedDateTime(new Date());
            Plan p = planRepository.saveAndFlush(newPlan);
            if(createBudgetReq.getItems() != null && createBudgetReq.getItems().size() != 0){
                List<PlanLineItem> newItems = saveLineItem(createBudgetReq.getItems(), p);
                p.setLineItems(newItems);
            }
            logger.info("Successully submitted the new plan! -- "+createBudgetReq.getUsername()+" "+new Date());
            return new GeneralRes("Successully submitted the new budget plan!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GeneralRes updateBudget(CreateBudgetReq updateBudgetReq, String id){
        try{
            Plan newPlan = planRepository.getOne(id);
            if(newPlan.getBudgetPlanStatus() != BudgetPlanStatusEnum.DRAFT){
                return new GeneralRes("The status of the budget plan is "+newPlan.getBudgetPlanStatus()+", and is not allowed to be further edited!", true);
            }
            if(updateBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.SUBMITTED);
            }
            else{
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.DRAFT);
            }
            if(updateBudgetReq.getCostCenterCode() != null){
                CostCenter cc = costCenterRepository.findCostCenterByCode(updateBudgetReq.getCostCenterCode());
                newPlan.setCostCenter(cc);
            }
            if(updateBudgetReq.getYear() != null){
                newPlan.setForYear(updateBudgetReq.getYear());
            }
            if(updateBudgetReq.getDescription() != null){
                newPlan.setPlanDescription(updateBudgetReq.getDescription());
            }
            if(updateBudgetReq.getItems() != null){
                deletePlanLineItem(newPlan);
                List<PlanLineItem> newItems = saveLineItem(updateBudgetReq.getItems(), newPlan);
                newPlan.setLineItems(newItems);
            }
            newPlan.setVersion(newPlan.getVersion() + 1);
            newPlan.setCreatedBy(updateBudgetReq.getUsername());
            newPlan.setCreatedDateTime(new Date());
            planRepository.saveAndFlush(newPlan);
            logger.info("Successully updated the new plan! -- "+updateBudgetReq.getUsername()+" "+new Date());
            return new GeneralRes("Successully submitted the new plan!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GetBudgetRes getBudget(String id){
        try{
            logger.info("Getting plan with id "+id+"...");
            Plan p = planRepository.getOne(id);

            if(p == null){
                return new GetBudgetRes("There is no plan in the database with id "+id, true, null);
            }
            return new GetBudgetRes("Successsfully retrieved the plan with id: "+id,false, p);
        } catch(Exception ex){
            ex.printStackTrace();
            return new GetBudgetRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetBudgetListRes getBudgetList(String username, String type, String status){
        try{
            List<Plan> plans = planRepository.findAll();
            List<Plan> pendingPlans = new ArrayList<>();
            BudgetPlanStatusEnum budgetPlanStatusEnum = BudgetPlanStatusEnum.DRAFT;
            BudgetPlanEnum budgetPlanEnum = BudgetPlanEnum.REFORECAST;
            logger.info("Req: username: "+username+" plan status: "+budgetPlanEnum+" plan type: "+budgetPlanStatusEnum);
            if(type.toUpperCase().equals("BUDGET")){
                budgetPlanEnum = BudgetPlanEnum.BUDGET;
            }
            if(status.toUpperCase().equals("SUBMITTED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.SUBMITTED;
            }
            else if(status.toUpperCase().equals("APPROVED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.APPROVED;
            }
            else if(status.toUpperCase().equals("REJECTED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.REJECTED;
            }
            for(Plan p: plans){
                if(p.getCreatedBy() == null || p.getBudgetPlanStatus() == null || p.getPlanType() == null) continue;
                logger.info("username: "+p.getCreatedBy()+" plan status; "+p.getBudgetPlanStatus()+" plan type"+p.getPlanType());
                if(p.getCreatedBy().equals(username) && p.getBudgetPlanStatus().equals(budgetPlanStatusEnum) && p.getPlanType().equals(budgetPlanEnum)){
                    pendingPlans.add(p);
                }
            }
            if(pendingPlans.size() == 0){
                return new GetBudgetListRes("There is no "+type+" plan in "+status+" status for you!", true, null);
            }
            return new GetBudgetListRes("Successsfully retrieved your pending plans!",false, plans);
        } catch(Exception ex){
            ex.printStackTrace();
            return new GetBudgetListRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }


    public GeneralRes approveBudget(ApproveBudgetReq approveBudgetReq) {
        try {
            Plan plan = planRepository.getOne(approveBudgetReq.getId());
            BudgetPlanStatusEnum budgetPlanStatusEnum = BudgetPlanStatusEnum.APPROVED;
            if (!approveBudgetReq.getApproved()) {
                plan.setBudgetPlanStatus(BudgetPlanStatusEnum.REJECTED);
            }
            planRepository.saveAndFlush(plan);
            logger.info("Successully "+budgetPlanStatusEnum+" the new plan! -- "+approveBudgetReq.getUsername()+" "+new Date());
            return new GeneralRes("Successfully "+budgetPlanStatusEnum+" the plan!", false);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }
}

