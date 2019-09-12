package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BJF extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @JsonIgnore
    private Merchandise merchandise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    private BjfTypeEnum BjfType;

    private String justification;

    private String currencyCode;

    private Double ongoingCost;

    private Double totalAmt;

    private String costCenterCode;

    private Double projectCost = null; // if type == project

    private String projectCode = null; // if type == project

    private String purchaseOrdersNumber;

    // link to the unique code from the approval
    private String businessApprovalTicketCode;
    // link to the unique code from the approval
    private String budgetApprovalTicketCode;


    public BJF() {
    }

    public BJF(BjfTypeEnum bjfType, String justification, String currencyCode, Double ongoingCost, Double totalAmt, String costCenterCode, Double projectCost, String projectCode) {
        this.BjfType = bjfType;
        this.justification = justification;
        this.currencyCode = currencyCode;
        this.ongoingCost = ongoingCost;
        this.totalAmt = totalAmt;
        this.costCenterCode = costCenterCode;
        this.projectCost = projectCost;
        this.projectCode = projectCode;
    }

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
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

    public Double getOngoingCost() {
        return ongoingCost;
    }

    public void setOngoingCost(Double ongoingCost) {
        this.ongoingCost = ongoingCost;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public Double getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Double projectCost) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
