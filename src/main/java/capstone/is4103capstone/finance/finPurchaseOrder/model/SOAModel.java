package capstone.is4103capstone.finance.finPurchaseOrder.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SOAModel implements Serializable {
    private String id;
    private BigDecimal paidAmt;
    private BigDecimal actualPmt;
    @Temporal(TemporalType.DATE)
    private Date scheduleDate;
    private String invoice_id;
    private String invoice_code;

    public SOAModel() {
    }

    public SOAModel(String id, BigDecimal paidAmt, BigDecimal actualPmt, Date scheduleDate) {
        this.id = id;
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
        this.scheduleDate = scheduleDate;
    }

    public SOAModel(String id, BigDecimal paidAmt, BigDecimal actualPmt, Date scheduleDate, String invoice_id, String invoice_code) {
        this.id = id;
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
        this.scheduleDate = scheduleDate;
        this.invoice_id = invoice_id;
        this.invoice_code = invoice_code;
    }

    public SOAModel(BigDecimal paidAmt, BigDecimal actualPmt, Date scheduleDate, String invoice_id, String invoice_code) {
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
        this.scheduleDate = scheduleDate;
        this.invoice_id = invoice_id;
        this.invoice_code = invoice_code;
    }

    public SOAModel(BigDecimal paidAmt, BigDecimal actualPmt, Date scheduleDate) {
        this.paidAmt = paidAmt;
        this.actualPmt = actualPmt;
        this.scheduleDate = scheduleDate;
    }

    public String getInvoice_code() {
        return invoice_code;
    }

    public void setInvoice_code(String invoice_code) {
        this.invoice_code = invoice_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }
}
