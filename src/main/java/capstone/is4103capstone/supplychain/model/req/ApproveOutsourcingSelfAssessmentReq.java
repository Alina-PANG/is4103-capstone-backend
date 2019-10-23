package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class ApproveOutsourcingSelfAssessmentReq implements Serializable {
    private Boolean approved;
    private String username;
    private String outsourcingSelfAssessmentId;
    private String comment;

    public ApproveOutsourcingSelfAssessmentReq() {
    }

    public ApproveOutsourcingSelfAssessmentReq(Boolean approved, String username, String outsourcingSelfAssessmentId, String comment) {
        this.approved = approved;
        this.username = username;
        this.outsourcingSelfAssessmentId = outsourcingSelfAssessmentId;
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

    public String getOutsourcingSelfAssessmentId() {
        return outsourcingSelfAssessmentId;
    }

    public void setOutsourcingSelfAssessmentId(String outsourcingSelfAssessmentId) {
        this.outsourcingSelfAssessmentId = outsourcingSelfAssessmentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
