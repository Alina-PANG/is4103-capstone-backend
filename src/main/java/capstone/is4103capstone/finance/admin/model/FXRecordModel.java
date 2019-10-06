package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class FXRecordModel implements Serializable {
    private String baseCurr;
    private String priceCurr;
    private String effectiveDate;
    private boolean hasExpired;
    private String expireDate;
    private BigDecimal rate;
    private String id;
    private String code;

    public FXRecordModel(String baseCurr, String priceCurr, String effectiveDate, boolean hasExpired, String expireDate, BigDecimal rate, String id, String code) {
        this.baseCurr = baseCurr;
        this.priceCurr = priceCurr;
        this.effectiveDate = effectiveDate;
        this.hasExpired = hasExpired;
        this.expireDate = expireDate;
        this.rate = rate;
        this.id = id;
        this.code = code;
    }

    public FXRecordModel() {
    }

    public boolean isHasExpired() {
        return hasExpired;
    }

    public void setHasExpired(boolean hasExpired) {
        this.hasExpired = hasExpired;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getBaseCurr() {
        return baseCurr;
    }

    public void setBaseCurr(String baseCurr) {
        this.baseCurr = baseCurr;
    }

    public String getPriceCurr() {
        return priceCurr;
    }

    public void setPriceCurr(String priceCurr) {
        this.priceCurr = priceCurr;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
