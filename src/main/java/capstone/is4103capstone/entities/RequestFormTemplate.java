package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public class RequestFormTemplate extends DBEntityTemplate {

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Employee requester;

    @ManyToOne
    @JoinColumn(name = "cost_center_id")
    private CostCenter costCenter;

    private String requestDescription;//can include purpose, training time, etc.
    private String additionalInfo;//buffer field.

    private BigDecimal estimatedBudget;

    private String currency;

    private ApprovalStatusEnum approvalStatus = ApprovalStatusEnum.PENDING;
    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Employee approver;

    public RequestFormTemplate() {
    }

    public RequestFormTemplate(String objectName, Employee requester, CostCenter costCenter, String requestDescription, String additionalInfo, BigDecimal estimatedBudget, String currency) {
        super(objectName);
        this.requester = requester;
        this.costCenter = costCenter;
        this.requestDescription = requestDescription;
        this.additionalInfo = additionalInfo;
        this.estimatedBudget = estimatedBudget;
        this.currency = currency;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public ApprovalStatusEnum getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatusEnum approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(BigDecimal estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
