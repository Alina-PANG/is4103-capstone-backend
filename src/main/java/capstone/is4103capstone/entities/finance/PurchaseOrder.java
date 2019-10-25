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
    private List<String> relatedBJF;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private List<StatementOfAcctLineItem> statementOfAccount = new ArrayList<>();

    @Column(name = "status")
    private ApprovalStatusEnum status;

    private String currencyCode;

    private Double totalAmount;

    private String approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_vendor")
    @JsonIgnore
    private Vendor vendor;

    public PurchaseOrder() {

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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
