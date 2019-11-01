package capstone.is4103capstone.finance.finPurchaseOrder.model;

import capstone.is4103capstone.entities.finance.SpendingRecord;
import capstone.is4103capstone.general.model.GeneralEntityModel;

import java.math.BigDecimal;
import java.util.List;

public class POModel {
    String id;
    String code;
    String vendorName;
    String vendorId;
    Double totalAmount;
    String currencyCode;
    BigDecimal totalPaidAmount;
    String poStatus;
    List<SpendingModel> servicesSpending;


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

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public POModel(String id, String code, String vendorName, String vendorId, Double totalAmount, String currencyCode, BigDecimal totalPaidAmount) {
        this.id = id;
        this.code = code;
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.totalAmount = totalAmount;
        this.currencyCode = currencyCode;
        this.totalPaidAmount = totalPaidAmount;
    }

    public List<SpendingModel> getServicesSpending() {
        return servicesSpending;
    }

    public void setServicesSpending(List<SpendingModel> servicesSpending) {
        this.servicesSpending = servicesSpending;
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
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
