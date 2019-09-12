package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Currency extends DBEntityTemplate {
    private Character symbol;
    private String countryCode;

    public Currency() {
    }

    public Currency(String currencyName, String currencyCode,Character symbol,String countryCode) {
        super(currencyName, currencyCode);
        this.symbol = symbol;
        this.countryCode = countryCode;
    }


    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}