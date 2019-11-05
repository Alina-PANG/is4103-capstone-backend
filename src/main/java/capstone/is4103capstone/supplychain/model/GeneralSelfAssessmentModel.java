package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessmentQuestion;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.enums.OutsourcingSelfAssessmentStatusEnum;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeneralSelfAssessmentModel implements Serializable {
    private String code;
    private OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus;

    @Temporal(TemporalType.DATE)
    private Date AnnualAssessmentDate;

    public GeneralSelfAssessmentModel() {
    }

    public GeneralSelfAssessmentModel(String code, OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus, Date annualAssessmentDate) {
        this.code = code;
        this.outsourcingSelfAssessmentStatus = outsourcingSelfAssessmentStatus;
        AnnualAssessmentDate = annualAssessmentDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OutsourcingSelfAssessmentStatusEnum getOutsourcingSelfAssessmentStatus() {
        return outsourcingSelfAssessmentStatus;
    }

    public void setOutsourcingSelfAssessmentStatus(OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus) {
        this.outsourcingSelfAssessmentStatus = outsourcingSelfAssessmentStatus;
    }

    public Date getAnnualAssessmentDate() {
        return AnnualAssessmentDate;
    }

    public void setAnnualAssessmentDate(Date annualAssessmentDate) {
        AnnualAssessmentDate = annualAssessmentDate;
    }
}

