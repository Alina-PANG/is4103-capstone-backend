package capstone.is4103capstone.finance.budget;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.UpdateBudgetReq;
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

    public GeneralRes createBudget(CreateBudgetReq createBudgetReq){
        logger.info("Start to create budget...");
        try{
            Plan newPlan = new Plan();
            if(createBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatusEnum(BudgetPlanStatusEnum.SUBMITTED);
            }
            else{
                newPlan.setBudgetPlanStatusEnum(BudgetPlanStatusEnum.DRAFT);
            }
            if(createBudgetReq.isBudget()){
                newPlan.setPlanType(BudgetPlanEnum.BUDGET);}else{
                newPlan.setPlanType(BudgetPlanEnum.REFORECAST);
            }
            newPlan.setLineItems(createBudgetReq.getItems());
            newPlan.setVersion(1);
            newPlan.setForYear(createBudgetReq.getYear());
            newPlan.setCreatedBy(createBudgetReq.getUsername());
            newPlan.setCreatedDateTime(new Date());
            planRepository.saveAndFlush(newPlan);
            logger.info("Successully submitted the new plan! -- "+createBudgetReq.getUsername()+" "+new Date());
            return new GeneralRes("Successully submitted the new budget plan!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GeneralRes updateBudget(UpdateBudgetReq updateBudgetReq){
        try{
            Plan newPlan = planRepository.getOne(updateBudgetReq.getId());
            if(newPlan.getBudgetPlanStatusEnum() != BudgetPlanStatusEnum.DRAFT){
                return new GeneralRes("The status of the budget plan is "+newPlan.getBudgetPlanStatusEnum()+", and is not allowed to be further edited!", true);
            }
            if(updateBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatusEnum(BudgetPlanStatusEnum.SUBMITTED);
            }
            else{
                newPlan.setBudgetPlanStatusEnum(BudgetPlanStatusEnum.DRAFT);
            }
            newPlan.setLineItems(updateBudgetReq.getItems());
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
            if(type.toUpperCase().equals("BUDGET")){
                budgetPlanEnum = BudgetPlanEnum.BUDGET;
            }
            if(status.toUpperCase().equals("SUBMITTED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.SUBMITTED;
            }
            else if(status.toUpperCase().equals("PROCESSING")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.PROCESSING;
            }
            else if(status.toUpperCase().equals("APPROVED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.APPROVED;
            }
            else if(status.toUpperCase().equals("REJECTED")){
                budgetPlanStatusEnum = BudgetPlanStatusEnum.REJECTED;
            }
            for(Plan p: plans){
                if(p.getCreatedBy().equals(username) && p.getBudgetPlanStatusEnum() == budgetPlanStatusEnum && p.getPlanType() == budgetPlanEnum){
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
                plan.setBudgetPlanStatusEnum(BudgetPlanStatusEnum.REJECTED);
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

