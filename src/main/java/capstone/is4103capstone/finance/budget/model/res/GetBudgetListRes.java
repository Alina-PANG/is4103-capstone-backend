package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class GetBudgetListRes extends GeneralRes  implements Serializable {
    private List<Plan> budgetPlanList;

    public GetBudgetListRes() {
    }

    public GetBudgetListRes(String message, Boolean error, List<Plan> budgetPlanList) {
        super(message, error);
        this.budgetPlanList = budgetPlanList;
    }

    public List<Plan> getBudgetPlanList() {
        return budgetPlanList;
    }

    public void setBudgetPlanList(List<Plan> budgetPlanList) {
        this.budgetPlanList = budgetPlanList;
    }
}
