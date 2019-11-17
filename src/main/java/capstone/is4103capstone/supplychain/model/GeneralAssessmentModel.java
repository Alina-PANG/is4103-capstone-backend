package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;

import java.io.Serializable;

public class GeneralAssessmentModel implements Serializable {
    private String BusinessDescription;
    private String code;
    private OutsourcingAssessmentStatusEnum status;

    public GeneralAssessmentModel() {
    }

    public GeneralAssessmentModel(String businessDescription, String code, OutsourcingAssessmentStatusEnum status) {
        BusinessDescription = businessDescription;
        this.code = code;
        this.status = status;
    }

    public String getBusinessDescription() {
        return BusinessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        BusinessDescription = businessDescription;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OutsourcingAssessmentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OutsourcingAssessmentStatusEnum status) {
        this.status = status;
    }
}
