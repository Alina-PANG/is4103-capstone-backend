package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import java.io.Serializable;
import java.util.List;

public class CreateResponseReq implements Serializable {
    List<Boolean> responses;
    List<String> comments;
    private String businessCaseDescription;

    public String getBusinessCaseDescription() {
        return businessCaseDescription;
    }

    public void setBusinessCaseDescription(String businessCaseDescription) {
        this.businessCaseDescription = businessCaseDescription;
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
