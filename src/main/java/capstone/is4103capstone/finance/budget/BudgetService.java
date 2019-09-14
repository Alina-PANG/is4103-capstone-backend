package capstone.is4103capstone.finance.budget;

import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.UpdateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetListRes;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
    public GeneralRes createBudget(CreateBudgetReq createBudgetReq){

        return new GeneralRes("Success", false);
    }

    public GeneralRes updateBudget(UpdateBudgetReq updateBudgetReq){
        return new GeneralRes("Success", false);
    }

    public GetBudgetRes getBudget(String id){
        return new GetBudgetRes();
    }

    public GetBudgetListRes getPendingBudgetList(String username){
        return new GetBudgetListRes();
    }

    public GetBudgetListRes getSubmittedBudgetList(String username){
        return new GetBudgetListRes();
    }

    public GeneralRes approveBudget(ApproveBudgetReq approveBudgetReq){
        return new ApproveBudgetReq();
    }
}

