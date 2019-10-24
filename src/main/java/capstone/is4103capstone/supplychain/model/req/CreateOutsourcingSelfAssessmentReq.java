package capstone.is4103capstone.supplychain.model.req;

import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessmentQuestion;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOutsourcingSelfAssessmentReq  implements Serializable {
    private String outsourcingId;
    private String outsourcingManagerId;
    private String designationId;
    private String functionHeadId; //approver

    @Temporal(TemporalType.DATE)
    private Date annualAssessmentDate;

    private List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList = new ArrayList<>();

    public CreateOutsourcingSelfAssessmentReq() {
    }

    public CreateOutsourcingSelfAssessmentReq(String outsourcingId, String outsourcingManagerId, String designationId, String functionHeadId, Date annualAssessmentDate, List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList) {
        this.outsourcingId = outsourcingId;
        this.outsourcingManagerId = outsourcingManagerId;
        this.designationId = designationId;
        this.functionHeadId = functionHeadId;
        this.annualAssessmentDate = annualAssessmentDate;
        this.outsourcingSelfAssessmentQuestionList = outsourcingSelfAssessmentQuestionList;
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

    public Date getAnnualAssessmentDate() {
        return annualAssessmentDate;
    }

    public void setAnnualAssessmentDate(Date annualAssessmentDate) {
        this.annualAssessmentDate = annualAssessmentDate;
    }

    public List<OutsourcingSelfAssessmentQuestion> getOutsourcingSelfAssessmentQuestionList() {
        return outsourcingSelfAssessmentQuestionList;
    }

    public void setOutsourcingSelfAssessmentQuestionList(List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList) {
        this.outsourcingSelfAssessmentQuestionList = outsourcingSelfAssessmentQuestionList;
    }
}
