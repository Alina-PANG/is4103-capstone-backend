package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "fx_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FXRecord extends DBEntityTemplate {

    String baseCurrencyAbbr;

    String priceCurrencyAbbr;

    BigDecimal exchangeRate;

    @Temporal(TemporalType.DATE)
    Date effectiveDate;

    @Temporal(TemporalType.DATE)
    Date expireDate;

    Boolean hasExpired = false;

    public FXRecord() {
    }

    public FXRecord(String baseCurrencyAbbr, String priceCurrencyAbbr, BigDecimal exchangeRate, Date effectiveDate) {
        this.baseCurrencyAbbr = baseCurrencyAbbr;
        this.priceCurrencyAbbr = priceCurrencyAbbr;
        this.exchangeRate = exchangeRate;
        this.effectiveDate = effectiveDate;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        this.setObjectName(this.baseCurrencyAbbr + "-" + this.priceCurrencyAbbr+"-"+format.format(this.effectiveDate));
    }

    public Boolean getHasExpired() {
        return hasExpired;
    }

    public void setHasExpired(Boolean hasExpired) {
        this.hasExpired = hasExpired;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getBaseCurrencyAbbr() {
        return baseCurrencyAbbr;
    }

    public void setBaseCurrencyAbbr(String baseCurrencyAbbr) {
        this.baseCurrencyAbbr = baseCurrencyAbbr;
    }

    public String getPriceCurrencyAbbr() {
        return priceCurrencyAbbr;
    }

    public void setPriceCurrencyAbbr(String priceCurrencyAbbr) {
        this.priceCurrencyAbbr = priceCurrencyAbbr;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
