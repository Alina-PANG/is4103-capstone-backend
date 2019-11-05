package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.GeneralContractModel;
import capstone.is4103capstone.supplychain.model.GeneralSelfAssessmentModel;

import java.io.Serializable;
import java.util.List;

public class GetGeneralSelfAssessmentsRes extends GeneralRes implements Serializable {
    public List<GeneralSelfAssessmentModel> selfAssessmentModelList;

    public GetGeneralSelfAssessmentsRes() {
    }

    public GetGeneralSelfAssessmentsRes(String message, Boolean hasError, List<GeneralSelfAssessmentModel> selfAssessmentModelList) {
        super(message, hasError);
        this.selfAssessmentModelList = selfAssessmentModelList;
    }

    public List<GeneralSelfAssessmentModel> getSelfAssessmentModelList() {
        return selfAssessmentModelList;
    }

    public void setSelfAssessmentModelList(List<GeneralSelfAssessmentModel> selfAssessmentModelList) {
        this.selfAssessmentModelList = selfAssessmentModelList;
    }
}
