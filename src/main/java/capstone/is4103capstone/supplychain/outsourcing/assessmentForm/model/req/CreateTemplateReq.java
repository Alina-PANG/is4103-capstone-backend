package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;

import java.io.Serializable;
import java.util.List;

public class CreateTemplateReq implements Serializable {
    private List<OutsourcingAssessmentSection> sectionList;


    public CreateTemplateReq() {
    }

    public List<OutsourcingAssessmentSection> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<OutsourcingAssessmentSection> sectionList) {
        this.sectionList = sectionList;
    }


}
