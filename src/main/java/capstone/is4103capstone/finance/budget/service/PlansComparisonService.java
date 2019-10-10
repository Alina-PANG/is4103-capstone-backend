package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.CompareLineItemModel;
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

    public JSONObject getPlanComparisonResult(String reforecastId) throws Exception{
        try{
            Optional<Plan> afterOps = planRepository.findUndeletedPlanById(reforecastId);
            if (!afterOps.isPresent())
                throw new Exception("Reforecast plan with id not found");

            Plan after = afterOps.get();

            Optional<Plan> budgetOps = planRepository.findBudgetByCCAndYear(after.getCostCenter().getId(),after.getForYear(), BudgetPlanStatusEnum.APPROVED,
                    BudgetPlanEnum.BUDGET);

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
    class CompareLineItem{
        String service;
        BigDecimal amount;
        String currency;
        String comment;

    public CompareLineItem(String service, BigDecimal amount, String currency, String comment) {
        this.service = service;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }
}
    private JSONObject comparePlans(Plan before, Plan after) throws Exception{
        JSONObject res = new JSONObject();

        List<CompareLineItemModel[]> change = new ArrayList<>();
        List<CompareLineItemModel> insertion = new ArrayList<>();

        HashMap<String, CompareLineItemModel> beforeMap = new HashMap<>();

        List<CompareLineItemModel> beforeItems = planRepository.findPlanLineItemsWithPlanId(before.getId());
        List<CompareLineItemModel> afterItems = planRepository.findPlanLineItemsWithPlanId(after.getId());

        for (CompareLineItemModel item: beforeItems){
            beforeMap.put(item.getMerchandiseCode(),item);
        }

        for (CompareLineItemModel item: afterItems){
            if (beforeMap.containsKey(item.getMerchandiseCode())){
                //change
                beforeMap.remove(item.getMerchandiseCode());
            }else{
                insertion.add(item);
            }
        }

        List<CompareLineItemModel> deletion = new ArrayList<>(beforeMap.values());


        //then combine deletion&insertion together








        return res;
    }

}
