package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseOrder extends DBEntityTemplate {
    @Convert(converter = StringListConverter.class)
    @JsonIgnore
    private List<String> relatedBJF;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StatementOfAcctLineItem> statementOfAccount = new ArrayList<>();

    @OneToMany(mappedBy = "relatedPO",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SpendingRecord> detailedSpending = new ArrayList<>();

    @Column(name = "status")
    private ApprovalStatusEnum status;

    private String countryId;

    private String currencyCode;

    private BigDecimal totalAmount;
    private BigDecimal totalAmtInGBP;

    private String approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_vendor")
    @JsonIgnore
    private Vendor vendor;

    public PurchaseOrder() {

    }

    public BigDecimal getTotalAmtInGBP() {
        return totalAmtInGBP;
    }

    public void setTotalAmtInGBP(BigDecimal totalAmtInGBP) {
        this.totalAmtInGBP = totalAmtInGBP;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public List<SpendingRecord> getDetailedSpending() {
        return detailedSpending;
    }

    public void setDetailedSpending(List<SpendingRecord> detailedSpending) {
        this.detailedSpending = detailedSpending;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public List<StatementOfAcctLineItem> getStatementOfAccount() {
        return statementOfAccount;
    }

    public void setStatementOfAccount(List<StatementOfAcctLineItem> statementOfAccount) {
        this.statementOfAccount = statementOfAccount;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public ApprovalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatusEnum status) {
        this.status = status;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
