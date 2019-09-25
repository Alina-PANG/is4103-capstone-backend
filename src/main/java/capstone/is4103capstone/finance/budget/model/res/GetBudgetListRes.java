package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;

import java.io.Serializable;
import java.util.List;

public class GetBudgetListRes extends GeneralRes  {
    private List<BudgetModel> plans;

    public GetBudgetListRes() {
    }

    public GetBudgetListRes(String message, Boolean hasError, List<BudgetModel> plans) {
        super(message, hasError);
        this.plans = plans;
    }

    public List<BudgetModel> getPlans() {
        return plans;
    }

    public void setPlans(List<BudgetModel> plans) {
        this.plans = plans;
    }
}
