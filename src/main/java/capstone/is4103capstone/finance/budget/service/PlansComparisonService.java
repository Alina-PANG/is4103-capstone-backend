package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.CompareLineItemModel;
import capstone.is4103capstone.finance.budget.model.res.PlanCompareRes;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PlansComparisonService {

    @Autowired
    PlanRepository planRepository;

    public PlanCompareRes getPlanComparisonResult(String reforecastId) throws Exception{
        try{
            Optional<Plan> afterOps = planRepository.findUndeletedPlanById(reforecastId);
            if (!afterOps.isPresent())
                throw new Exception("Reforecast plan with id not found");

            Plan after = afterOps.get();
            System.out.println(BudgetPlanStatusEnum.APPROVED.ordinal() + " " +  BudgetPlanEnum.BUDGET.ordinal());
            Optional<Plan> budgetOps = planRepository.findBudgetByCCAndYear(
                    after.getCostCenter().getId(),after.getForYear(), BudgetPlanStatusEnum.APPROVED.ordinal(),
                    BudgetPlanEnum.BUDGET.ordinal());

            if (!budgetOps.isPresent())
                throw new Exception("Corresponding budget plan not found");

            Plan budget = budgetOps.get();


            return comparePlans(budget,after);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

//    public JSONObject getPlanComparisonResult(String beforeId, String afterId) throws Exception{
//        try{
//            Optional<Plan> beforeOps = planRepository.findUndeletedPlanById(beforeId);
//            if (!beforeOps.isPresent())
//                throw new Exception("Original plan with id not found");
//            Plan beforePlan = beforeOps.get();
//
//            Optional<Plan> afterOps = planRepository.findUndeletedPlanById(afterId);
//            if (!afterOps.isPresent())
//                throw new Exception("New plan with id not found");
//            Plan afterPlan = afterOps.get();
//
//
//
//
//
//            return comparePlans(beforePlan,afterPlan);
//        }catch (Exception ex){
//            throw new Exception(ex.getMessage());
//        }
//    }

    private PlanCompareRes comparePlans(Plan before, Plan after) throws Exception{

        List<CompareLineItemModel[]> changed = new ArrayList<>();
        List<CompareLineItemModel> insertion = new ArrayList<>();

        HashMap<String, CompareLineItemModel> beforeMap = new HashMap<>();

        List<CompareLineItemModel> beforeItems = planRepository.findPlanLineItemsWithPlanId(before.getId());
        List<CompareLineItemModel> afterItems = planRepository.findPlanLineItemsWithPlanId(after.getId());

        for (CompareLineItemModel item: beforeItems){
            beforeMap.put(item.getServiceCode(),item);
        }

        for (CompareLineItemModel item: afterItems){
            if (beforeMap.containsKey(item.getServiceCode()) ){
                CompareLineItemModel[] tuple = new CompareLineItemModel[2];
                tuple[0] = beforeMap.get(item.getServiceCode());
                tuple[1] = item;
                if (!lineItemSameServiceIsEqual(tuple[0],tuple[1]))
                    changed.add(tuple);
                    //change
                beforeMap.remove(item.getServiceCode());
            }else{
                insertion.add(item);
            }
        }

        List<CompareLineItemModel> deletion = new ArrayList<>(beforeMap.values());
        for (CompareLineItemModel[] m:changed){
            System.out.println(m[0].getServiceName()+ " "+m[1].getServiceName());
        }


        return new PlanCompareRes("Successfully Retrieved", false, insertion,deletion,changed,
                before.calculateTotalValue(), after.calculateTotalValue());

    }


    private boolean lineItemSameServiceIsEqual(CompareLineItemModel a, CompareLineItemModel b){
        if (!a.getAmount().equals(b.getAmount()))
            return false;
        if ((a.getComment() == null || b.getComment()==null ) && !(a.getComment() == null && b.getComment()==null ))
            return false;
        if (!a.getComment().equals(b.getComment()))
            return false;
        if (!a.getCurrency().equals(b.getCurrency()))
            return false;



        return true;
    }

}
