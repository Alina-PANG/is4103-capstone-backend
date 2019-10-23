package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.OutsourcingSelfAssessmentModel;

import java.io.Serializable;
import java.util.List;

public class GetOutsourcingSelfAssessmentsRes extends GeneralRes implements Serializable {
    public List<OutsourcingSelfAssessmentModel> outsourcingSelfAssessmentModels;

    public GetOutsourcingSelfAssessmentsRes() {
    }

    public GetOutsourcingSelfAssessmentsRes(String message, Boolean hasError, List<OutsourcingSelfAssessmentModel> outsourcingSelfAssessmentModels) {
        super(message, hasError);
        this.outsourcingSelfAssessmentModels = outsourcingSelfAssessmentModels;
    }

    public List<OutsourcingSelfAssessmentModel> getOutsourcingSelfAssessmentModels() {
        return outsourcingSelfAssessmentModels;
    }

    public void setOutsourcingSelfAssessmentModels(List<OutsourcingSelfAssessmentModel> outsourcingSelfAssessmentModels) {
        this.outsourcingSelfAssessmentModels = outsourcingSelfAssessmentModels;
    }
}