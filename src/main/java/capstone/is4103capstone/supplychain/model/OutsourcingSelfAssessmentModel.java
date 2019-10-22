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

public class OutsourcingSelfAssessmentModel implements Serializable {
    private String code;
    private String id;
    private Long seqNo;
    private EmployeeModel outsourcingManager;
    private EmployeeModel designation;
    private EmployeeModel functionHead;    //approver
    private OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus;

    @Temporal(TemporalType.DATE)
    private Date AnnualAssessmentDate;

    private List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList = new ArrayList<>();
    private Boolean canUpdateAndRequest;

    public OutsourcingSelfAssessmentModel() {
    }

    public OutsourcingSelfAssessmentModel(String code, String id, Long seqNo, EmployeeModel outsourcingManager, EmployeeModel designation, EmployeeModel functionHead, OutsourcingSelfAssessmentStatusEnum outsourcingSelfAssessmentStatus, Date annualAssessmentDate, List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList, Boolean canUpdateAndRequest) {
        this.code = code;
        this.id = id;
        this.seqNo = seqNo;
        this.outsourcingManager = outsourcingManager;
        this.designation = designation;
        this.functionHead = functionHead;
        this.outsourcingSelfAssessmentStatus = outsourcingSelfAssessmentStatus;
        AnnualAssessmentDate = annualAssessmentDate;
        this.outsourcingSelfAssessmentQuestionList = outsourcingSelfAssessmentQuestionList;
        this.canUpdateAndRequest = canUpdateAndRequest;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public EmployeeModel getOutsourcingManager() {
        return outsourcingManager;
    }

    public void setOutsourcingManager(EmployeeModel outsourcingManager) {
        this.outsourcingManager = outsourcingManager;
    }

    public EmployeeModel getDesignation() {
        return designation;
    }

    public void setDesignation(EmployeeModel designation) {
        this.designation = designation;
    }

    public EmployeeModel getFunctionHead() {
        return functionHead;
    }

    public void setFunctionHead(EmployeeModel functionHead) {
        this.functionHead = functionHead;
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

    public List<OutsourcingSelfAssessmentQuestion> getOutsourcingSelfAssessmentQuestionList() {
        return outsourcingSelfAssessmentQuestionList;
    }

    public void setOutsourcingSelfAssessmentQuestionList(List<OutsourcingSelfAssessmentQuestion> outsourcingSelfAssessmentQuestionList) {
        this.outsourcingSelfAssessmentQuestionList = outsourcingSelfAssessmentQuestionList;
    }

    public Boolean getCanUpdateAndRequest() {
        return canUpdateAndRequest;
    }

    public void setCanUpdateAndRequest(Boolean canUpdateAndRequest) {
        this.canUpdateAndRequest = canUpdateAndRequest;
    }
}
