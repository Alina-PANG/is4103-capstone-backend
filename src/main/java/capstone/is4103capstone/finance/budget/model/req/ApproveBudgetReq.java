package capstone.is4103capstone.finance.budget.model.req;

import java.io.Serializable;

public class ApproveBudgetReq implements Serializable {
    Boolean approved;
    String username;
    String planId;

    public ApproveBudgetReq() {
    }

    public ApproveBudgetReq(Boolean approved, String username, String id) {
        this.approved = approved;
        this.username = username;
        this.planId = id;
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
