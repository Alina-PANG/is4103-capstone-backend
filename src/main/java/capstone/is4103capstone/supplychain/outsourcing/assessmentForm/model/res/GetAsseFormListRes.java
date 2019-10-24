package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormSimpleModel;

import java.util.List;

public class GetAsseFormListRes extends GeneralRes {
    List<AssessmentFormSimpleModel> assessmentRecords;
    Boolean isOutsourcingStaff;

    public Boolean getOutsourcingStaff() {
        return isOutsourcingStaff;
    }

    public void setOutsourcingStaff(Boolean outsourcingStaff) {
        isOutsourcingStaff = outsourcingStaff;
    }

    public GetAsseFormListRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public GetAsseFormListRes(String message, Boolean hasError, List<AssessmentFormSimpleModel> assessmentRecords) {
        super(message, hasError);
        this.assessmentRecords = assessmentRecords;
    }

    public GetAsseFormListRes(String message, Boolean hasError, List<AssessmentFormSimpleModel> assessmentRecords, Boolean isOutsourcingStaff) {
        super(message, hasError);
        this.assessmentRecords = assessmentRecords;
        this.isOutsourcingStaff = isOutsourcingStaff;
    }

    public List<AssessmentFormSimpleModel> getAssessmentRecords() {
        return assessmentRecords;
    }

    public void setAssessmentRecords(List<AssessmentFormSimpleModel> assessmentRecords) {
        this.assessmentRecords = assessmentRecords;
    }
}
