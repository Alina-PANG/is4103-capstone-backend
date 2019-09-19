package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Entity;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Currency extends DBEntityTemplate {

    private String currencyCode;

    public Currency() {
    }

    public Currency(String currencyName, String currencyCode) {
        super(currencyName, currencyCode);
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
