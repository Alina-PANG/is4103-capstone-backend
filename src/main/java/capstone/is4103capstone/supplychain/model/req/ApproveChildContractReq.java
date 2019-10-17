package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class ApproveChildContractReq implements Serializable {
    private Boolean approved;
    private String username;
    private String childContractId;
    private String comment;

    public ApproveChildContractReq() {
    }

    public ApproveChildContractReq(Boolean approved, String username, String childContractId, String comment) {
        this.approved = approved;
        this.username = username;
        this.childContractId = childContractId;
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

    public String getChildContractId() {
        return childContractId;
    }

    public void setChildContractId(String childContractId) {
        this.childContractId = childContractId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
