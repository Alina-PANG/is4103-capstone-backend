package capstone.is4103capstone.finance.requestsMgmt.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateBJFReq implements Serializable {
    String requester;
    String requestType;  //BAU:
    String costCenter;
    String project;
    String vendor;
    String serviceOrProduct;
    String justification;
    BigDecimal projectCost;
    BigDecimal ongoingCost;
    String currency;
    BigDecimal totalBudget;// not required, because can calcuated
    String additionalInfo;

    public CreateBJFReq() {
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getServiceOrProduct() {
        return serviceOrProduct;
    }

    public void setServiceOrProduct(String serviceOrProduct) {
        this.serviceOrProduct = serviceOrProduct;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    public BigDecimal getOngoingCost() {
        return ongoingCost;
    }

    public void setOngoingCost(BigDecimal ongoingCost) {
        this.ongoingCost = ongoingCost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
