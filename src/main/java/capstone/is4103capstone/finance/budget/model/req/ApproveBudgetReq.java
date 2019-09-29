package capstone.is4103capstone.finance.budget.model.req;

import java.io.Serializable;

public class ApproveBudgetReq implements Serializable {
    private Boolean approved;
    private String username;
    private String planId;
    private String comment;
    private int approvalType;

    public ApproveBudgetReq() {
    }

    public ApproveBudgetReq(Boolean approved, String username, String planId, String comment, int approvalType) {
        this.approved = approved;
        this.username = username;
        this.planId = planId;
        this.comment = comment;
        this.approvalType = approvalType;
    }

    public int getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(int approvalType) {
        this.approvalType = approvalType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
