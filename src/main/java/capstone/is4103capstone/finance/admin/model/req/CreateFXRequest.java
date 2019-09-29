package capstone.is4103capstone.finance.admin.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateFXRequest implements Serializable {
    private String baseCurr;
    private String priceCurr;
    private String effectiveDate;
    private BigDecimal rate;
    private String username;

    public CreateFXRequest() {
    }

    public CreateFXRequest(String baseCurr, String priceCurr, String effectiveDate, BigDecimal rate, String username) {
        this.baseCurr = baseCurr;
        this.priceCurr = priceCurr;
        this.effectiveDate = effectiveDate;
        this.rate = rate;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
