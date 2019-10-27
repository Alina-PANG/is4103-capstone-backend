package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import java.io.Serializable;
import java.util.List;

public class CreateResponseReq implements Serializable {
    private List<Boolean> responses;
    private List<String> comments;
    private String businessCaseDescription;
    private String bjfId;

    public String getBusinessCaseDescription() {
        return businessCaseDescription;
    }

    public void setBusinessCaseDescription(String businessCaseDescription) {
        this.businessCaseDescription = businessCaseDescription;
    }

    public String getBjfId() {
        return bjfId;
    }

    public void setBjfId(String bjfId) {
        this.bjfId = bjfId;
    }

    public List<Boolean> getResponses() {
        return responses;
    }

    public void setResponses(List<Boolean> responses) {
        this.responses = responses;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
