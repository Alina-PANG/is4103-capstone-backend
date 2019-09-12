package capstone.is4103capstone.finance.budget;

import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    public GeneralRes createBudget(CreateBudgetReq createBudgetReq){
        return new GeneralRes("Success", false);
    }
}
