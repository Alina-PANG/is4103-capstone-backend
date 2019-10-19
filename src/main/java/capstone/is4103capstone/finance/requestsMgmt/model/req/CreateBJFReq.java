package capstone.is4103capstone.finance.requestsMgmt.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateBJFReq implements Serializable {
    private String requester;
//    private String requestType;  //BAU:
    private String costCenter;
    private String project;
    private String vendor;
    private String serviceOrProduct;
    private String justification;
    private String currency;
    private BigDecimal totalBudget;// not required, because can calcuated
    private String sponsor;
    private String coverage;
    private String revexCurr;
    private String capexCurr;
    private BigDecimal revex;
    private BigDecimal capex;
    private Boolean competitiveQuotesAvailable;

    public CreateBJFReq() {
    }

    public Boolean getCompetitiveQuotesAvailable() {
        return competitiveQuotesAvailable;
    }

    public void setCompetitiveQuotesAvailable(Boolean competitiveQuotesAvailable) {
        this.competitiveQuotesAvailable = competitiveQuotesAvailable;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

//    public String getRequestType() {
//        return requestType;
//    }
//
//    public void setRequestType(String requestType) {
//        this.requestType = requestType;
//    }

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

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getRevexCurr() {
        return revexCurr;
    }

    public void setRevexCurr(String revexCurr) {
        this.revexCurr = revexCurr;
    }

    public String getCapexCurr() {
        return capexCurr;
    }

    public void setCapexCurr(String capexCurr) {
        this.capexCurr = capexCurr;
    }

    public BigDecimal getRevex() {
        return revex;
    }

    public void setRevex(BigDecimal revex) {
        this.revex = revex;
    }

    public BigDecimal getCapex() {
        return capex;
    }

    public void setCapex(BigDecimal capex) {
        this.capex = capex;
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

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
}
