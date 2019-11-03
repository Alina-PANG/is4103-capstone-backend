package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.OutsourcingSelfAssessmentDistributionModel;

import java.io.Serializable;

public class GetSelfAssessmentDistributionRes extends GeneralRes implements Serializable {
    private OutsourcingSelfAssessmentDistributionModel outsourcingSelfAssessmentDistributionModel;

    public GetSelfAssessmentDistributionRes() {
    }

    public GetSelfAssessmentDistributionRes(String message, Boolean hasError, OutsourcingSelfAssessmentDistributionModel outsourcingSelfAssessmentDistributionModel) {
        super(message, hasError);
        this.outsourcingSelfAssessmentDistributionModel = outsourcingSelfAssessmentDistributionModel;
    }

    public OutsourcingSelfAssessmentDistributionModel getOutsourcingSelfAssessmentDistributionModel() {
        return outsourcingSelfAssessmentDistributionModel;
    }

    public void setOutsourcingSelfAssessmentDistributionModel(OutsourcingSelfAssessmentDistributionModel outsourcingSelfAssessmentDistributionModel) {
        this.outsourcingSelfAssessmentDistributionModel = outsourcingSelfAssessmentDistributionModel;
    }
}
