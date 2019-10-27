package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StatementOfAcctLineItem extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "purchase_order_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    @Temporal(TemporalType.DATE)
    private Date scheduleDate;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "statementOfAcctLineItem")
    @JsonIgnore
    private Invoice invoice;

    private BigDecimal paidAmt;
    private BigDecimal actualPmt;

    public StatementOfAcctLineItem() {
    }

    public StatementOfAcctLineItem(PurchaseOrder purchaseOrder, Date scheduleDate, Invoice invoice, BigDecimal paidAmt, BigDecimal actualPmt) {
        this.purchaseOrder = purchaseOrder;
        this.scheduleDate = scheduleDate;
        this.invoice = invoice;
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public BigDecimal getActualPmt() {
        return actualPmt;
    }

    public void setActualPmt(BigDecimal actualPmt) {
        this.actualPmt = actualPmt;
    }



    public StatementOfAcctLineItem(PurchaseOrder purchaseOrder, String statement) {
        this.purchaseOrder = purchaseOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

}
