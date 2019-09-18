package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BudgetLineItemService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;

//    public List<PlanLineItem> aggregatePlanStaticstics(){
//
//    }

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
                        String country = item.getCode();
                        if(!res.contains(country)){
                            flag = false;
                        }
                    }
                    else if(colName.toUpperCase().equals("REGION")){
                        String region = item.getCode();
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
