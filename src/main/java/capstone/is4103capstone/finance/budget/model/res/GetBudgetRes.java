package capstone.is4103capstone.finance.budget.model.res;


import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class GetBudgetRes extends GeneralRes {
    private Plan budgetPlan;

    public GetBudgetRes() {
    }


    public GetBudgetRes(String message, Boolean error, Plan budgetPlan) {
        super(message, error);
        this.budgetPlan = budgetPlan;
    }

    public Plan getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(Plan budgetPlan) {
        this.budgetPlan = budgetPlan;
    }
}
