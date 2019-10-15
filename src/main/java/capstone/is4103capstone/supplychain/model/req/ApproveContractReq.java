package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class ApproveContractReq implements Serializable {
    private Boolean approved;
    private String username;
    private String contarctId;
    private String comment;
    private int approvalType;

    public ApproveContractReq() {
    }

    public ApproveContractReq(Boolean approved, String username, String contarctId, String comment, int approvalType) {
        this.approved = approved;
        this.username = username;
        this.contarctId = contarctId;
        this.comment = comment;
        this.approvalType = approvalType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContarctId() {
        return contarctId;
    }

    public void setContarctId(String contarctId) {
        this.contarctId = contarctId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(int approvalType) {
        this.approvalType = approvalType;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}