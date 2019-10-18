package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import java.io.Serializable;

public class ApproveReq implements Serializable {
    String username;
    Boolean approved;

    public ApproveReq(String username) {
        this.username = username;
    }

    public ApproveReq(String username, Boolean approved) {
        this.username = username;
        this.approved = approved;
    }

    public ApproveReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
