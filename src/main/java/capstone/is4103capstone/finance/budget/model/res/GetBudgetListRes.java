package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.BudgetPlan;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetBudgetListRes extends GeneralRes {
    private List<BudgetPlan> budgetPlanList;

    public GetBudgetListRes() {
    }

    public GetBudgetListRes(String message, Boolean error, List<BudgetPlan> budgetPlanList) {
        super(message, error);
        this.budgetPlanList = budgetPlanList;
    }

    public List<BudgetPlan> getBudgetPlanList() {
        return budgetPlanList;
    }

    public void setBudgetPlanList(List<BudgetPlan> budgetPlanList) {
        this.budgetPlanList = budgetPlanList;
    }
}
