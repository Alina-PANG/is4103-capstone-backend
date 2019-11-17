package capstone.is4103capstone.finance.dashboard.model;

import java.math.BigDecimal;

public class SimplestServiceAmountModel {
    String serviceCode;
    String currencyCode;
    BigDecimal amount;

    public SimplestServiceAmountModel(String serviceCode, String currencyCode, BigDecimal amount) {
        this.serviceCode = serviceCode;
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
