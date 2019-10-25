package capstone.is4103capstone.finance.finPurchaseOrder.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceModel implements Serializable {
    private String id;

    private String description;

    private String currencyCode;

    private BigDecimal paymentAmount;

    private String fileType;

    private String fileName;

    public InvoiceModel() {
    }

    public InvoiceModel(String description, String currencyCode, BigDecimal paymentAmount, String fileType, String fileName) {
        this.description = description;
        this.currencyCode = currencyCode;
        this.paymentAmount = paymentAmount;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
