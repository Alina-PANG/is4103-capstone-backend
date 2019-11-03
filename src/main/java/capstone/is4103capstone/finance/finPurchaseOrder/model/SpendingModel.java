package capstone.is4103capstone.finance.finPurchaseOrder.model;

import capstone.is4103capstone.entities.finance.SpendingRecord;
import capstone.is4103capstone.util.Tools;

import java.math.BigDecimal;

public class SpendingModel {
    private String lastModifiedDateTime;
    private String serviceId;
    private BigDecimal spendingAmt;
    private String currencyCode;
    private String code;

    public SpendingModel(SpendingRecord s) {
        setCode(s.getCode());
        setCurrencyCode(s.getCurrencyCode());
        setServiceId(s.getServiceId());
        setSpendingAmt(s.getSpendingAmt());
        setLastModifiedDateTime(Tools.datetimeFormatter.format(s.getLastModifiedDateTime()));
    }



    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getSpendingAmt() {
        return spendingAmt;
    }

    public void setSpendingAmt(BigDecimal spendingAmt) {
        this.spendingAmt = spendingAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
