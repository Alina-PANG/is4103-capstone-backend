package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseOrder extends DBEntityTemplate {
    @Convert(converter = StringListConverter.class)
    private List<String> relatedBJF;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder")
    private List<StatementOfAcctLineItem> statementOfAccount = new ArrayList<>();

    @Column(name = "status")
    private ApprovalStatusEnum status;

    private String currencyCode;

    private Double totalAmount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public PurchaseOrder() {
    }

    public ApprovalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatusEnum status) {
        this.status = status;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<StatementOfAcctLineItem> getStatementOfAccount() {
        return statementOfAccount;
    }

    public void setStatementOfAccount(List<StatementOfAcctLineItem> statementOfAccount) {
        this.statementOfAccount = statementOfAccount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
