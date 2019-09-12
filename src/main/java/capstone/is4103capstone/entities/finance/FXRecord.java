package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class FXRecord extends DBEntityTemplate {

    String baseCurrencyAbbr;

    String priceCurrencyAbbr;

    Double exchangeRate;

    public FXRecord() {
    }

    public FXRecord(String baseCurrencyAbbr, String priceCurrencyAbbr, Double exchangeRate) {
        this.baseCurrencyAbbr = baseCurrencyAbbr;
        this.priceCurrencyAbbr = priceCurrencyAbbr;
        this.exchangeRate = exchangeRate;
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

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
