package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.Tools;

import java.math.BigDecimal;

public class RequestFormModel {

    private EmployeeModel requester;
    private String costCenterCode;
    private String description;
    private String additionalInfo;
    private BigDecimal estimatedBudget;
    private String currencyCode;
    private String approvalStatus;
    private String id;
    private String createdDateTime;

    public RequestFormModel() {
    }


    public RequestFormModel(RequestFormTemplate e) {
        setAdditionalInfo(e.getAdditionalInfo());
        setCostCenterCode(e.getCostCenter() == null? null:e.getCostCenter().getCode());
        setDescription(e.getRequestDescription());
        setEstimatedBudget(e.getEstimatedBudget());
        setCurrencyCode(e.getCurrency());
        setApprovalStatus(e.getApprovalStatus().name());
        setRequester(new EmployeeModel(e.getRequester()));
        setId(e.getId());
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
    }

    public RequestFormModel(EmployeeModel requester, String costCenterCode, String description, String additionalInfo, BigDecimal estimatedBudget, String currencyCode, String approvalStatus, String id, String createdDateTime) {
        this.requester = requester;
        this.costCenterCode = costCenterCode;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.estimatedBudget = estimatedBudget;
        this.currencyCode = currencyCode;
        this.approvalStatus = approvalStatus;
        this.id = id;
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeModel getRequester() {
        return requester;
    }

    public void setRequester(EmployeeModel requester) {
        this.requester = requester;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
