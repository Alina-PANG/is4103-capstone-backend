package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class ApproveContractReq implements Serializable {
    private Boolean approved;
    private String username;
    private String contractId;
    private String comment;

    public ApproveContractReq() {
    }

    public ApproveContractReq(Boolean approved, String username, String contractId, String comment) {
        this.approved = approved;
        this.username = username;
        this.contractId = contractId;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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
}