package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.util.enums.BJFStatusEnum;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BJF extends RequestFormTemplate {

    //justification == form description
    private String serviceId;
    private String vendorId;

    private BjfTypeEnum BjfType;

    private BigDecimal capex;
    private String capexCurrency;
    private String revexCurrency;
    private Boolean competitiveQuotesAvailable;
    private BigDecimal revex; // if type == project
    private String sponsor;
    private String projectCode = null; // string/id both should work: if type == project
    private String coverage;
    private String purchaseOrderNumber;

    // link to the unique code from the approval
    private String businessApprovalTicketCode;
    // link to the unique code from the approval
    private String budgetApprovalTicketCode;
    private BigDecimal totalSpending;

    private BJFStatusEnum bjfStatus = BJFStatusEnum.PENDING_APPROVAL;


    @OneToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "oa_id",nullable = true)
    @JsonIgnore
    private OutsourcingAssessment related_outsourcing_assessment;

    public BJF() {
    }

    public OutsourcingAssessment getRelated_outsourcing_assessment() {
        return related_outsourcing_assessment;
    }

    public void setRelated_outsourcing_assessment(OutsourcingAssessment related_outsourcing_assessment) {
        this.related_outsourcing_assessment = related_outsourcing_assessment;
    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(BigDecimal totalSpending) {
        this.totalSpending = totalSpending;
    }

    public String getCapexCurrency() {
        return capexCurrency;
    }

    public void setCapexCurrency(String capexCurrency) {
        this.capexCurrency = capexCurrency;
    }

    public String getRevexCurrency() {
        return revexCurrency;
    }

    public void setRevexCurrency(String revexCurrency) {
        this.revexCurrency = revexCurrency;
    }

    public Boolean getCompetitiveQuotesAvailable() {
        return competitiveQuotesAvailable;
    }

    public void setCompetitiveQuotesAvailable(Boolean competitiveQuotesAvailable) {
        this.competitiveQuotesAvailable = competitiveQuotesAvailable;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public BJFStatusEnum getBjfStatus() {
        return bjfStatus;
    }

    public void setBjfStatus(BJFStatusEnum bjfStatus) {
        this.bjfStatus = bjfStatus;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public BjfTypeEnum getBjfType() {
        return BjfType;
    }

    public void setBjfType(BjfTypeEnum bjfType) {
        BjfType = bjfType;
    }

    public BigDecimal getCapex() {
        return capex;
    }

    public void setCapex(BigDecimal capex) {
        this.capex = capex;
    }


    public BigDecimal getRevex() {
        return revex;
    }

    public void setRevex(BigDecimal revex) {
        this.revex = revex;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getBusinessApprovalTicketCode() {
        return businessApprovalTicketCode;
    }

    public void setBusinessApprovalTicketCode(String businessApprovalTicketCode) {
        this.businessApprovalTicketCode = businessApprovalTicketCode;
    }

    public String getBudgetApprovalTicketCode() {
        return budgetApprovalTicketCode;
    }

    public void setBudgetApprovalTicketCode(String budgetApprovalTicketCode) {
        this.budgetApprovalTicketCode = budgetApprovalTicketCode;
    }

}
