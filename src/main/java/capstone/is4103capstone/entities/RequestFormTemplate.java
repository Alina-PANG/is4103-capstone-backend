package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import java.math.BigDecimal;

public class RequestFormTemplate extends DBEntityTemplate {

    private Employee requester;

    private CostCenter costCenter;

    private String requestDescription;//can include purpose, training time, etc.
    private String additionalInfo;//buffer field.

    private BigDecimal estimatedBudget;

    private String currency;

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
