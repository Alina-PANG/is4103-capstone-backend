package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Entity;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Currency extends DBEntityTemplate {


    private String countryCode;


    public Currency() {
    }

    public Currency(String currencyName, String countryCode) {
        super(currencyName, countryCode);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
