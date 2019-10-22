package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessment;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.OutsourcingSelfAssessmentModel;

import java.io.Serializable;
import java.util.List;

public class GetOutsourcingSelfAssessmentRes extends GeneralRes implements Serializable{
    public OutsourcingSelfAssessmentModel outsourcingSelfAssessmentModel;

    public GetOutsourcingSelfAssessmentRes() {
    }

    public GetOutsourcingSelfAssessmentRes(String message, Boolean hasError, OutsourcingSelfAssessmentModel outsourcingSelfAssessmentModel) {
        super(message, hasError);
        this.outsourcingSelfAssessmentModel = outsourcingSelfAssessmentModel;
    }

    public OutsourcingSelfAssessmentModel getOutsourcingSelfAssessmentModel() {
        return outsourcingSelfAssessmentModel;
    }

    public void setOutsourcingSelfAssessmentModel(OutsourcingSelfAssessmentModel outsourcingSelfAssessmentModel) {
        this.outsourcingSelfAssessmentModel = outsourcingSelfAssessmentModel;
    }
}
