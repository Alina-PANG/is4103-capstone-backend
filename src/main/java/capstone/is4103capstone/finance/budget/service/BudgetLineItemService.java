package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ColsToShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BudgetLineItemService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetLineItemService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;

    public List<ArrayList<String>> convertPlanLineItemToList(List<PlanLineItem> planLineItems) throws Exception{
        List<ArrayList<String>> content = new ArrayList<>();
        for(PlanLineItem i: planLineItems){
            ArrayList<String> list = new ArrayList<>();
            // {"Merchandise_Code", "Amount", "Currency", "Comment"};
            list.add(i.getMerchandiseCode());
            list.add(i.getBudgetAmount().toString());
            list.add(i.getCurrencyAbbr());
            list.add(i.getComment());
            content.add(list);
        }
        return content;
    }

    public List<PlanLineItem> convertListToPlanLineItem(List<List<String>> content) throws Exception{
        List<PlanLineItem> list = new ArrayList<>();
        for(int i = 1; i < content.size(); i ++){
            PlanLineItem item = new PlanLineItem();
            List<String> c = content.get(i);
            item.setMerchandiseCode(c.get(0));
            item.setBudgetAmount(new BigDecimal(c.get(1)));
            item.setCurrencyAbbr(c.get(2));
            item.setComment(c.get(3));
            list.add(item);
        }
        return list;
    }

    // ======== TODO ============ //

    public List<ArrayList<String>> convertPlanLineItemToList(List<PlanLineItem> planLineItems, ColsToShow colsToShow){
        List<ArrayList<String>> content = new ArrayList<>();
        for(int i = 0; i < planLineItems.size(); i ++){
            ArrayList<String> list = new ArrayList<>();

            list.add(planLineItems.get(i).getPlanBelongsTo().getCode());
        }
        return null;
    }

    public List<PlanLineItem> aggregatePlanStaticstics(String[] cols, List<ArrayList<String>> restrictions){
        List<PlanLineItem> planLineItems = filterPlanItem(cols, restrictions);
        List<PlanLineItem> aggregatedItem = new ArrayList<>();

        return planLineItems;
    }

    public List<PlanLineItem> filterPlanItem(String[] cols, List<ArrayList<String>> restrictions){
        List<PlanLineItem> planLineItems = planLineItemRepository.findAll();
        List<PlanLineItem> selected = new ArrayList<>();
        for(int t = 0; t < planLineItems.size(); t ++){
            boolean flag = true;
            PlanLineItem item = planLineItems.get(t);
            for(int i = 0; i < restrictions.size() && flag; i ++){
                if(restrictions.get(i).size() == 0) continue;
                else{
                    String colName = cols[i];
                    Set<String> res = new HashSet<>(); // e.g. "SG", "US"
                    for(int j = 0; j < restrictions.get(i).size(); j ++){
                        res.add(restrictions.get(i).get(j));
                    }
                    if(colName.toUpperCase().equals("COUNTRY")){
                        String country = item.getPlanBelongsTo().getCostCenter().getCountry().getCode();
                        if(!res.contains(country)){
                            flag = false;
                        }
                    }
                    else if(colName.toUpperCase().equals("REGION")){
                        String region = item.getPlanBelongsTo().getCostCenter().getCountry().getRegion().getCode();
                        if(!res.contains(region)){
                            flag = false;
                        }
                    } // [TODO] add in  more filters, and decide how to filter by code
                }
            }
            if(flag) {
                selected.add(item);
            }
        }
        return selected;
    }

}
