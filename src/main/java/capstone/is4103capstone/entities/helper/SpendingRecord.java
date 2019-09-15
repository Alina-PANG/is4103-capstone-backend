package capstone.is4103capstone.entities.helper;

import capstone.is4103capstone.util.enums.SpendingTypeEnum;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
public class SpendingRecord {
    private String incurredFrom; // can be the purchase order code, travel plan code, etc.
    private Date incurredDate;
    private BigDecimal spendingAmt;
    private String currencyCode;
    private SpendingTypeEnum spendingType;

    public SpendingRecord() {
    }


    public SpendingRecord(String incurredFrom, Date incurredDate, BigDecimal spendingAmt, String currencyCode, SpendingTypeEnum spendingType) {
        this.incurredFrom = incurredFrom;
        this.incurredDate = incurredDate;
        this.spendingAmt = spendingAmt;
        this.currencyCode = currencyCode;
        this.spendingType = spendingType;
    }

    public SpendingTypeEnum getSpendingType() {
        return spendingType;
    }

    public void setSpendingType(SpendingTypeEnum spendingType) {
        this.spendingType = spendingType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getIncurredFrom() {
        return incurredFrom;
    }

    public void setIncurredFrom(String incurredFrom) {
        this.incurredFrom = incurredFrom;
    }

    public Date getIncurredDate() {
        return incurredDate;
    }

    public void setIncurredDate(Date incurredDate) {
        this.incurredDate = incurredDate;
    }

    public BigDecimal getSpendingAmt() {
        return spendingAmt;
    }

    public void setSpendingAmt(BigDecimal spendingAmt) {
        this.spendingAmt = spendingAmt;
    }
}
