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
    private String description;

    private String currencyCode;

    private BigDecimal paymentAmount;

    private String fileType;

    private String fileName;

    @OneToOne(fetch = FetchType.LAZY)
    StatementOfAcctLineItem statementOfAcctLineItem;


    @Lob
    private byte[] data;


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

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
