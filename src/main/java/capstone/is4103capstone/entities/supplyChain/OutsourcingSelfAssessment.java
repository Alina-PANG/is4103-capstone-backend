package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.util.enums.OutsourcingSelfAssessmentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OutsourcingSelfAssessment extends DBEntityTemplate {
    private String outsourcingId;
    private String outsourcingManagerId;
    private String designationId;
    private String functionHeadId;    //approver
    private OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus;

    @Temporal(TemporalType.DATE)
    private Date AnnualAssessmentDate;

    @Convert(converter = StringListConverter.class)
    private List<String> outsourcingSelfAssessmentQuestionIdList = new ArrayList<>();

    public OutsourcingSelfAssessment() {
    }

    public OutsourcingSelfAssessment(String outsourcingId, String outsourcingManagerId, String designationId, String functionHeadId, OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus, Date annualAssessmentDate, List<String> outsourcingSelfAssessmentQuestionIdList) {
        this.outsourcingId = outsourcingId;
        this.outsourcingManagerId = outsourcingManagerId;
        this.designationId = designationId;
        this.functionHeadId = functionHeadId;
        this.outsourcingSelfAssessmentStatus = outsourcingSelfAssessmentStatus;
        AnnualAssessmentDate = annualAssessmentDate;
        this.outsourcingSelfAssessmentQuestionIdList = outsourcingSelfAssessmentQuestionIdList;
    }

    public String getOutsourcingId() {
        return outsourcingId;
    }

    public void setOutsourcingId(String outsourcingId) {
        this.outsourcingId = outsourcingId;
    }

    public String getOutsourcingManagerId() {
        return outsourcingManagerId;
    }

    public void setOutsourcingManagerId(String outsourcingManagerId) {
        this.outsourcingManagerId = outsourcingManagerId;
    }

    public String getDesignationId() {
        return designationId;
    }

    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }

    public String getFunctionHeadId() {
        return functionHeadId;
    }

    public void setFunctionHeadId(String functionHeadId) {
        this.functionHeadId = functionHeadId;
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

    public List<String> getOutsourcingSelfAssessmentQuestionIdList() {
        return outsourcingSelfAssessmentQuestionIdList;
    }

    public void setOutsourcingSelfAssessmentQuestionIdList(List<String> outsourcingSelfAssessmentQuestionIdList) {
        this.outsourcingSelfAssessmentQuestionIdList = outsourcingSelfAssessmentQuestionIdList;
    }
}
