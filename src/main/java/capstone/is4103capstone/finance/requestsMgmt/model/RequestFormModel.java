package capstone.is4103capstone.finance.requestsMgmt.model;

import capstone.is4103capstone.entities.RequestFormTemplate;

import java.math.BigDecimal;

public class RequestFormModel {

    private String requesterUsername;
    private String requesterName;
    private String costCenterCode;
    private String description;
    private String additionalInfo;
    private BigDecimal estimatedBudget;
    private String currencyCode;
    private String approvalStatus;

    public RequestFormModel() {
    }

    public RequestFormModel(RequestFormTemplate e) {
        setAdditionalInfo(e.getAdditionalInfo());
        setCostCenterCode(e.getCostCenter().getCode());
        setDescription(e.getRequestDescription());
        setEstimatedBudget(e.getEstimatedBudget());
        setCurrencyCode(e.getCurrency());
        setRequesterName(e.getRequester().getFullName());
        setRequesterUsername(e.getRequester().getUserName());
        setApprovalStatus(e.getStatus().name());
    }

    public RequestFormModel(String requesterUsername, String requesterName, String costCenterCode, String description, String additionalInfo, BigDecimal estimatedBudget, String currencyCode) {
        this.requesterUsername = requesterUsername;
        this.requesterName = requesterName;
        this.costCenterCode = costCenterCode;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.estimatedBudget = estimatedBudget;
        this.currencyCode = currencyCode;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
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
