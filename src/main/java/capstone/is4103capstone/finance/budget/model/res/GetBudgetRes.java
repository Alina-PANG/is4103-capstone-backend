package capstone.is4103capstone.finance.budget.model.res;


import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class GetBudgetRes extends GeneralRes {
    private BudgetModel budgetPlan;

    public GetBudgetRes() {
    }

    public GetBudgetRes(BudgetModel budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public GetBudgetRes(String message, Boolean hasError, BudgetModel budgetPlan) {
        super(message, hasError);
        this.budgetPlan = budgetPlan;
    }

    public BudgetModel getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BudgetModel budgetPlan) {
        this.budgetPlan = budgetPlan;
    }
}
