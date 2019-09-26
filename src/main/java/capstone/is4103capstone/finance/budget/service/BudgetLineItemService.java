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

}
