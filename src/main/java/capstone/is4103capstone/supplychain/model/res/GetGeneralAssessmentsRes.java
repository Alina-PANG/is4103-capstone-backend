package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.GeneralAssessmentModel;
import capstone.is4103capstone.supplychain.model.GeneralContractModel;

import java.io.Serializable;
import java.util.List;

public class GetGeneralAssessmentsRes extends GeneralRes implements Serializable {
    public List<GeneralAssessmentModel> assessmentModelList;

    public GetGeneralAssessmentsRes() {

    }

    public GetGeneralAssessmentsRes(String message, Boolean hasError, List<GeneralAssessmentModel> assessmentModelList) {
        super(message, hasError);
        this.assessmentModelList = assessmentModelList;
    }

    public List<GeneralAssessmentModel> getAssessmentModelList() {
        return assessmentModelList;
    }

    public void setAssessmentModelList(List<GeneralAssessmentModel> assessmentModelList) {
        this.assessmentModelList = assessmentModelList;
    }
}
