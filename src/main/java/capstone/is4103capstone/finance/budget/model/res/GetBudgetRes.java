package capstone.is4103capstone.finance.budget.model.res;


import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetBudgetRes extends GeneralRes {
    private BudgetModel budgetPlan;
    private boolean userIsBMApprover;
    private boolean userIsFunctionApprover;
    ApprovalTicketModel latestReview;


    public GetBudgetRes() {
    }

    public GetBudgetRes(BudgetModel budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public GetBudgetRes(String message, Boolean hasError, BudgetModel budgetPlan) {
        super(message, hasError);
        this.budgetPlan = budgetPlan;
    }

    public GetBudgetRes(String message, Boolean hasError, BudgetModel budgetPlan, boolean isBMApprover, boolean userIsFunctionApprover) {
        super(message, hasError);
        this.budgetPlan = budgetPlan;
        this.userIsBMApprover = isBMApprover;
        this.userIsFunctionApprover = userIsFunctionApprover;
    }

    public GetBudgetRes(String message, Boolean hasError, BudgetModel budgetPlan, boolean userIsBMApprover, boolean userIsFunctionApprover, ApprovalTicketModel latestReview) {
        super(message, hasError);
        this.budgetPlan = budgetPlan;
        this.userIsBMApprover = userIsBMApprover;
        this.userIsFunctionApprover = userIsFunctionApprover;
        this.latestReview = latestReview;
    }

    public ApprovalTicketModel getLatestReview() {
        return latestReview;
    }

    public void setLatestReview(ApprovalTicketModel latestReview) {
        this.latestReview = latestReview;
    }

    public boolean isUserIsBMApprover() {
        return userIsBMApprover;
    }

    public void setUserIsBMApprover(boolean userIsBMApprover) {
        this.userIsBMApprover = userIsBMApprover;
    }

    public boolean isUserIsFunctionApprover() {
        return userIsFunctionApprover;
    }

    public void setUserIsFunctionApprover(boolean userIsFunctionApprover) {
        this.userIsFunctionApprover = userIsFunctionApprover;
    }

    public BudgetModel getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BudgetModel budgetPlan) {
        this.budgetPlan = budgetPlan;
    }
}
