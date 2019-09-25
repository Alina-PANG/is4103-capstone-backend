package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.BudgetLineItemModel;
import capstone.is4103capstone.finance.budget.model.res.BudgetModel;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetListRes;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    @Autowired
    MerchandiseRepository merchandiseRepository;

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
            if(id != null){
                Plan existingPlan = planRepository.getOne(id);
                existingPlan.setDeleted(true);
                planRepository.saveAndFlush(existingPlan);
                deletePlanItem(existingPlan.getLineItems());
                version = existingPlan.getVersion() + 1;
            }
            Plan newPlan = new Plan();
            if(createBudgetReq.isToSubmit()){
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.SUBMITTED);
            }
            else{
                newPlan.setBudgetPlanStatus(BudgetPlanStatusEnum.DRAFT);
            }
            if(createBudgetReq.isBudget()){
                newPlan.setPlanType(BudgetPlanEnum.BUDGET);
                newPlan.setObjectName(createBudgetReq.getYear()+"-BUDGET");
            }
            else{
                newPlan.setPlanType(BudgetPlanEnum.REFORECAST);
                if (createBudgetReq.getMonth() == null){
                    throw new Exception("You must select the month of reforecast!");
                }
                newPlan.setObjectName(createBudgetReq.getYear()+"-"+createBudgetReq.getMonth()+"-REFORECAST");
            }
            CostCenter cc = costCenterRepository.findCostCenterByCode(createBudgetReq.getCostCenterCode());

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
            return new GeneralRes("Successully submitted the new budget plan!", false);
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

            List<BudgetLineItemModel> items = new ArrayList<>();
            BudgetModel plan = new BudgetModel(p.getForYear(), p.getForMonth(), p.getObjectName(), id, p.getBudgetPlanStatus(),p.getPlanType());
            plan.setCreateBy(p.getCreatedBy());
            plan.setLastModifiedTime(datetimeFormatter.format(p.getLastModifiedDateTime() == null ? p.getCreatedDateTime() : p.getLastModifiedDateTime()));

            for(int i = 0; i < p.getLineItems().size(); i ++){
                PlanLineItem item = p.getLineItems().get(i);
                Merchandise m = merchandiseRepository.findMerchandiseByCode(item.getMerchandiseCode());
                System.out.println(item.getMerchandiseCode());
                BudgetSub2 budgetSub2 = m.getBudgetSub2();
                System.out.println("sub2: "+budgetSub2.getCode());
                BudgetSub1 budgetSub1 = budgetSub2.getBudgetSub1();
                System.out.println("sub1: "+budgetSub1.getCode());
                BudgetCategory budgetCategory = budgetSub1.getBudgetCategory();
                System.out.println("category: "+budgetCategory.getCode());
                items.add(new BudgetLineItemModel(item.getId(), budgetCategory.getObjectName(), budgetCategory.getCode(), budgetSub1.getObjectName(), budgetSub1.getCode(), budgetSub2.getObjectName(), budgetSub2.getCode(), m.getObjectName(), m.getCode(), item.getBudgetAmount(), item.getCurrencyAbbr(), item.getComment()));
            }

            plan.setItems(items);

            return new GetBudgetRes("Successsfully retrieved the plan with id: "+id,false, plan);
        } catch(Exception ex){
            ex.printStackTrace();
            return new GetBudgetRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetBudgetListRes getBudgetList(String costcenterId, String username, Integer retrieveType, Integer year){
        //retrieveType: by default: null or 0: all, 1-budget, 2-reforecast
        try{
            if (retrieveType != null && (retrieveType > 2 || retrieveType < 0))
                throw new Exception("Retrieve Type can only take value 0,1,2");


            List<Plan> plans = planRepository.findByCostCenterId(costcenterId);
            List<BudgetModel> result = new ArrayList<>();


            logger.info("Req: username: "+username+" plan type: "+retrieveType);
            for(Plan p: plans){
                if(p.getDeleted() || p.getCreatedBy() == null || p.getBudgetPlanStatus() == null || p.getPlanType() == null) continue;
                if(checkPlanTypeAndYear(p,retrieveType,year)){
                    BudgetModel thisPlan = new BudgetModel(p.getForYear(), p.getForMonth(), p.getObjectName(), p.getId(), p.getBudgetPlanStatus(),p.getPlanType());
                    thisPlan.setCreateBy(p.getCreatedBy());
                    thisPlan.setLastModifiedTime(datetimeFormatter.format(p.getLastModifiedDateTime() == null ? p.getCreatedDateTime() : p.getLastModifiedDateTime()));
                    result.add(thisPlan);
                }
            }
            if(result.size() == 0){
                return new GetBudgetListRes("There is no plan to view according to the search type!", true, null);
            }
            return new GetBudgetListRes("Successsfully retrieved plans under the cost center!",false, result);
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
}

