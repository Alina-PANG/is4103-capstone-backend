package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    private String description;

    private String currencyCode;

    private BigDecimal paymentAmount;

    private String fileType;

    private String fileName;

    @OneToOne(fetch = FetchType.LAZY)
    StatementOfAcctLineItem statementOfAcctLineItem;


    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

    public Invoice() {
    }

    public Invoice(String description, String currencyCode, BigDecimal paymentAmount) {
        this.description = description;

        this.currencyCode = currencyCode;
        this.paymentAmount = paymentAmount;
    }

    public Invoice(String objectName, String code, String description, BigDecimal paymentAmount) {
        super(objectName, code);
        this.description = description;
        this.paymentAmount = paymentAmount;
    }

    public StatementOfAcctLineItem getStatementOfAcctLineItem() {
        return statementOfAcctLineItem;
    }

    public void setStatementOfAcctLineItem(StatementOfAcctLineItem statementOfAcctLineItem) {
        this.statementOfAcctLineItem = statementOfAcctLineItem;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Invoice(PurchaseOrder purchaseOrder, String description, BigDecimal paymentAmount, Vendor vendor) {
        this.purchaseOrder = purchaseOrder;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.vendor = vendor;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }




    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
