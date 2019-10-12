package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BJF extends RequestFormTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    @JsonIgnore
    private Service service;

    private BjfTypeEnum BjfType;

    private String justification;

    private String currencyCode;

    private BigDecimal ongoingCost;

    private BigDecimal totalAmt;

    private BigDecimal projectCost = null; // if type == project

    private String projectCode = null; // if type == project

    private String purchaseOrdersNumber;

    // link to the unique code from the approval
    private String businessApprovalTicketCode;
    // link to the unique code from the approval
    private String budgetApprovalTicketCode;


    public BJF() {
    }



    public BJF(BjfTypeEnum bjfType, String justification, String currencyCode, BigDecimal ongoingCost, BigDecimal totalAmt, BigDecimal projectCost, String projectCode) {
        this.BjfType = bjfType;
        this.justification = justification;
        this.currencyCode = currencyCode;
        this.ongoingCost = ongoingCost;
        this.totalAmt = totalAmt;
        this.projectCost = projectCost;
        this.projectCode = projectCode;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public BjfTypeEnum getBjfType() {
        return BjfType;
    }

    public void setBjfType(BjfTypeEnum bjfType) {
        BjfType = bjfType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getOngoingCost() {
        return ongoingCost;
    }

    public void setOngoingCost(BigDecimal ongoingCost) {
        this.ongoingCost = ongoingCost;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }


    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPurchaseOrdersNumber() {
        return purchaseOrdersNumber;
    }

    public void setPurchaseOrdersNumber(String purchaseOrdersNumber) {
        this.purchaseOrdersNumber = purchaseOrdersNumber;
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


    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
