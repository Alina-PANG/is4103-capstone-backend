package capstone.is4103capstone.finance.finPurchaseOrder.model.req;

import java.math.BigDecimal;
import java.util.Date;

public class CreateSoAByInvoiceReq {
    private String receiveDate;
    private BigDecimal paidAmt;
    private BigDecimal actualPmt;
    private String poId;

    public CreateSoAByInvoiceReq() {
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
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

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }
}
