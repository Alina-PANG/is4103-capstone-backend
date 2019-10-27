package capstone.is4103capstone.finance.requestsMgmt.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateTravelRequest implements Serializable {

    String requester;
    String costCenter;
    String description;
    String additionalInfo;
    BigDecimal estimatedBudget;
    String currencyCode;
    Boolean isAdhoc;
    String destCity;
    String destCountry;
    String startDate;
    String endDate;

    public CreateTravelRequest() {
    }

    public CreateTravelRequest(String requester, String costCenter, String description, String additionalInfo, BigDecimal estimatedBudget, String currencyCode, Boolean isAdhoc, String destCity, String destCountry, String startDate, String endDate) {
        this.requester = requester;
        this.costCenter = costCenter;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.estimatedBudget = estimatedBudget;
        this.currencyCode = currencyCode;
        this.isAdhoc = isAdhoc;
        this.destCity = destCity;
        this.destCountry = destCountry;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Boolean getIsAdhoc() {
        return isAdhoc;
    }

    public void setIsAdhoc(Boolean isAdhoc) {
        this.isAdhoc = isAdhoc;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
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

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDestCountry() {
        return destCountry;
    }

    public void setDestCountry(String destCountry) {
        this.destCountry = destCountry;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
