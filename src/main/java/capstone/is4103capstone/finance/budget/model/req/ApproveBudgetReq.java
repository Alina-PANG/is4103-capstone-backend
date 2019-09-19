package capstone.is4103capstone.finance.budget.model.req;

import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;

public class ApproveBudgetReq implements Serializable {
    Boolean approved;
    String username;
    String id;

    public ApproveBudgetReq() {
    }

    public ApproveBudgetReq(Boolean approved, String username, String id) {
        this.approved = approved;
        this.username = username;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
