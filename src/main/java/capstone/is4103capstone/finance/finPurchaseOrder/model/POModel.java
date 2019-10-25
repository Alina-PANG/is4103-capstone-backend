package capstone.is4103capstone.finance.finPurchaseOrder.model;

import java.math.BigDecimal;
import java.util.List;

public class POModel {
    String id;
    String code;
    String vendorName;
    String vendorId;
    Double totalAmount;
    String currencyCode;


    public POModel() {
    }

    public POModel(String id, String code, String vendorName, String vendorId, Double totalAmount, String currencyCode) {
        this.id = id;
        this.code = code;
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.totalAmount = totalAmount;
        this.currencyCode = currencyCode;
    }

    public POModel(String vendorName, String vendorId, Double totalAmount, String currencyCode) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.totalAmount = totalAmount;
        this.currencyCode = currencyCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }
}
