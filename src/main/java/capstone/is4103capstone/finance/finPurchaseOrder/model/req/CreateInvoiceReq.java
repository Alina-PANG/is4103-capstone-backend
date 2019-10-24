package capstone.is4103capstone.finance.finPurchaseOrder.model.req;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class CreateInvoiceReq {
    private BigDecimal totalAmt;
    private String vendorId;
    private String description;
    private String currencyCode;

    public CreateInvoiceReq() {
    }

    public CreateInvoiceReq(BigDecimal totalAmt, String vendorId, String description, String currencyCode) {
        this.totalAmt = totalAmt;
        this.vendorId = vendorId;
        this.description = description;
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
