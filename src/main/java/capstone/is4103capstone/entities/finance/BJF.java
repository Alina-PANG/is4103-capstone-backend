package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.util.enums.BJFStatusEnum;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
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

    private String currencyCode;

    private BigDecimal ongoingCost;

    private BigDecimal totalAmt;

    private BigDecimal projectCost = null; // if type == project

    private String projectCode = null; // string/id both should word: if type == project

    private String purchaseOrderNumber;

    // link to the unique code from the approval
    private String businessApprovalTicketCode;
    // link to the unique code from the approval
    private String budgetApprovalTicketCode;

    private BJFStatusEnum bjfStatus = BJFStatusEnum.PENDING_APPROVAL;


    public BJF() {
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
