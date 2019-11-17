package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.OutsourcingAssessmentDistributionModel;

import java.io.Serializable;

public class GetAssessmentDistributionRes extends GeneralRes implements Serializable {
    private OutsourcingAssessmentDistributionModel outsourcingAssessmentDistributionModel;

    public GetAssessmentDistributionRes() {
    }

    public GetAssessmentDistributionRes(String message, Boolean hasError, OutsourcingAssessmentDistributionModel outsourcingAssessmentDistributionModel) {
        super(message, hasError);
        this.outsourcingAssessmentDistributionModel = outsourcingAssessmentDistributionModel;
    }

    public OutsourcingAssessmentDistributionModel getOutsourcingAssessmentDistributionModel() {
        return outsourcingAssessmentDistributionModel;
    }

    public void setOutsourcingAssessmentDistributionModel(OutsourcingAssessmentDistributionModel outsourcingAssessmentDistributionModel) {
        this.outsourcingAssessmentDistributionModel = outsourcingAssessmentDistributionModel;
    }
}
