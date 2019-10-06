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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
    @Temporal(TemporalType.DATE)
    private Date scheduleDate;

    private BigDecimal paidAmt;
    private BigDecimal actualPmt;
    private BigDecimal accruals;

    public StatementOfAcctLineItem() {
    }

    public StatementOfAcctLineItem(Date scheduleDate, BigDecimal paidAmt, BigDecimal actualPmt, BigDecimal accruals) {
        this.scheduleDate = scheduleDate;
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
        this.accruals = accruals;
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

    public BigDecimal getAccruals() {
        return accruals;
    }

    public void setAccruals(BigDecimal accruals) {
        this.accruals = accruals;
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
