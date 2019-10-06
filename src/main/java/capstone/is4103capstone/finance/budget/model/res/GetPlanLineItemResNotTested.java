package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.Plan;

import java.util.ArrayList;
import java.util.List;

public class GetPlanLineItemResNotTested extends GetBudgetRes{
    List<List<String>> content;
    String[] cols;

    public GetPlanLineItemResNotTested() {
    }

    public GetPlanLineItemResNotTested(List<List<String>> content, String[] cols) {
        this.content = content;
        this.cols = cols;
    }

    public GetPlanLineItemResNotTested(List<List<String>> content) {
        this.content = content;
    }

    public GetPlanLineItemResNotTested(BudgetModel budgetPlan, List<List<String>> content) {
        super(budgetPlan);
        this.content = content;
    }

    public GetPlanLineItemResNotTested(String message, Boolean hasError, BudgetModel budgetPlan, List<List<String>> content) {
        super(message, hasError, budgetPlan);
        this.content = content;
    }

    public List<List<String>> getContent() {
        return content;
    }

    public void setContent(List<List<String>> content) {
        this.content = content;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }
}
