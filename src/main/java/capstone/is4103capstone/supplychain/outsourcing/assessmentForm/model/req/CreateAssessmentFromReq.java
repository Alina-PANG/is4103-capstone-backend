package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;

import java.io.Serializable;
import java.util.List;

public class CreateAssessmentFromReq implements Serializable {
    private List<OutsourcingAssessmentSection> sectionList;
    private String businessCaseDescription;
    private String username;
    private String approverAUsername;
    private String approverBUsername;

    public CreateAssessmentFromReq() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public List<OutsourcingAssessmentSection> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<OutsourcingAssessmentSection> sectionList) {
        this.sectionList = sectionList;
    }

    public String getBusinessCaseDescription() {
        return businessCaseDescription;
    }

    public void setBusinessCaseDescription(String businessCaseDescription) {
        this.businessCaseDescription = businessCaseDescription;
    }

    public String getApproverAUsername() {
        return approverAUsername;
    }

    public void setApproverAUsername(String approverAUsername) {
        this.approverAUsername = approverAUsername;
    }

    public String getApproverBUsername() {
        return approverBUsername;
    }

    public void setApproverBUsername(String approverBUsername) {
        this.approverBUsername = approverBUsername;
    }
}
