package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ColsToShow;
import capstone.is4103capstone.finance.budget.model.res.BudgetLineItemModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

@Service
public class BudgetLineItemService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetLineItemService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    MerchandiseRepository merchandiseRepository;

    public List<ArrayList<String>> convertPlanLineItemToList(List<PlanLineItem> planLineItems) throws Exception{
        List<ArrayList<String>> content = new ArrayList<>();
        for(PlanLineItem i: planLineItems){
            ArrayList<String> list = new ArrayList<>();
            // {"Category, Sub1, Sub2, Merchandise_Code", "Amount", "Currency", "Comment"};
            String mcode = i.getMerchandiseCode();
            Merchandise merchandise = merchandiseRepository.findMerchandiseByCode(mcode);
            BudgetSub2 budgetSub2 = merchandise.getBudgetSub2();
            BudgetSub1 budgetSub1 = budgetSub2.getBudgetSub1();
            list.add(budgetSub1.getBudgetCategory().getObjectName());
            list.add(budgetSub1.getObjectName());
            list.add(budgetSub2.getObjectName());
            list.add(i.getMerchandiseCode());
            list.add(i.getBudgetAmount().toString());
            list.add(i.getCurrencyAbbr());
            list.add(i.getComment());
            content.add(list);
        }
        return content;
    }

    public List<BudgetLineItemModel> convertListToPlanLineItem(List<List<String>> content) throws Exception{
        List<BudgetLineItemModel> list = new ArrayList<>();
        for(int i = 1; i < content.size(); i ++){
            BudgetLineItemModel item = new BudgetLineItemModel();
            List<String> c = content.get(i);
            Merchandise m = merchandiseRepository.findMerchandiseByCode(c.get(3));
            if (m == null || m.getDeleted())
                throw new Exception("Merchandise Code given at line["+i+"] is not valid.");

            //TODO: use JPA projection -> for faster processing.
            /*
            select cat.code,sub1.code, sub2.code,m.code from merchandise m join budget_sub2 sub2 join budget_sub1 sub1
                join budget_category cat
                on m.budget_sub2_id = sub2.id and sub1.id = sub2.budget_sub1_id  and cat.id=sub1.budget_category_id;
             */
            BudgetSub2 budgetSub2 = m.getBudgetSub2();
            BudgetSub1 budgetSub1 = budgetSub2.getBudgetSub1();
            BudgetCategory budgetCategory = budgetSub1.getBudgetCategory();

            item.setCategoryCode(budgetCategory.getCode());
            item.setCategoryName(budgetCategory.getObjectName());
            item.setSub1Code(budgetSub1.getCode());
            item.setSub1Name(budgetSub1.getObjectName());
            item.setSub2Code(budgetSub2.getCode());
            item.setSub2Name(budgetSub2.getObjectName());
            item.setMerchandiseName(m.getObjectName());
            item.setMerchandiseCode(c.get(3));
            item.setAmount(new BigDecimal(c.get(4)));
            item.setCurrency(c.get(5));
            if(c.size() >= 7)
                item.setComment(c.get(6));
            list.add(item);
        }
        return list;
    }

}
