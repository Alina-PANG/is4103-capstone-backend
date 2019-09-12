package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.BudgetPlan;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetBudgetRes extends GeneralRes {
    private BudgetPlan budgetPlan;

    public GetBudgetRes() {
    }

    public GetBudgetRes(String message, Boolean error, BudgetPlan budgetPlan) {
        super(message, error);
        this.budgetPlan = budgetPlan;
    }

    public BudgetPlan getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BudgetPlan budgetPlan) {
        this.budgetPlan = budgetPlan;
    }
}
