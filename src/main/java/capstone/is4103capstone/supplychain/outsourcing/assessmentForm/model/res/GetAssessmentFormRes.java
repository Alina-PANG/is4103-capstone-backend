package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetAssessmentFormRes extends GeneralRes {
    OutsourcingAssessment outsourcingAssessment;

    public GetAssessmentFormRes(String message, Boolean hasError, OutsourcingAssessment outsourcingAssessment) {
        super(message, hasError);
        this.outsourcingAssessment = outsourcingAssessment;
    }

    public OutsourcingAssessment getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(OutsourcingAssessment outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
    }
}
