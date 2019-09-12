package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class StatementOfAcctLineItem extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
    @Temporal(TemporalType.DATE)
    private Date scheduleDate;

    private Double paidAmt;
    private Double actualPmt;
    private Double accruals;

    public StatementOfAcctLineItem() {
    }

    public StatementOfAcctLineItem(Date scheduleDate, Double paidAmt, Double actualPmt, Double accruals) {
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

    public Double getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(Double paidAmt) {
        this.paidAmt = paidAmt;
    }

    public Double getActualPmt() {
        return actualPmt;
    }

    public void setActualPmt(Double actualPmt) {
        this.actualPmt = actualPmt;
    }

    public Double getAccruals() {
        return accruals;
    }

    public void setAccruals(Double accruals) {
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
