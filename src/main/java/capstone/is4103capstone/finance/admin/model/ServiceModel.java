package capstone.is4103capstone.finance.admin.model;

import capstone.is4103capstone.entities.finance.Service;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServiceModel implements Serializable {
    String serviceName;
    String serviceCode;
    String itemName;
//    String vendorCode;
//    String vendorName;
    String measureUnit;
    boolean hasActiveContract;
    String id;

    BigDecimal referencePrice;
    String currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
//
//    String currentContractCode;
//    String contractStartDate;
//    String contractEndDate;
//    String comment;

    public ServiceModel() {

    }
    public ServiceModel(Service s){
        setServiceName(s.getObjectName());
        setServiceCode(s.getCode());
        setReferencePrice(s.getReferencePrice());
        setCurrency(s.getCurrencyCode());
        setMeasureUnit(s.getMeasureUnit());
        setId(s.getId());
    }

    public ServiceModel(String serviceName, String serviceCode) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
    }

    public ServiceModel(String serviceName, String serviceCode, String measureUnit) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
//        this.vendorCode = vendorCode;
//        this.vendorName = vendorName;
        this.measureUnit = measureUnit;
    }

    public ServiceModel(String serviceName, String serviceCode, String measureUnit, BigDecimal referencePrice, String currency) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.measureUnit = measureUnit;
        this.hasActiveContract = hasActiveContract;
        this.referencePrice = referencePrice;
        this.currency = currency;
    }
    //    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }

    public boolean isHasActiveContract() {
        return hasActiveContract;
    }

    public void setHasActiveContract(boolean hasActiveContract) {
        this.hasActiveContract = hasActiveContract;
    }

//    public String getVendorCode() {
//        return vendorCode;
//    }
//
//    public void setVendorCode(String vendorCode) {
//        this.vendorCode = vendorCode;
//    }

//    public String getVendorName() {
//        return vendorName;
//    }
//
//    public void setVendorName(String vendorName) {
//        this.vendorName = vendorName;
//    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

//    public String getCurrentContractCode() {
//        return currentContractCode;
//    }
//
//    public void setCurrentContractCode(String currentContractCode) {
//        this.currentContractCode = currentContractCode;
//    }
//
//    public String getContractStartDate() {
//        return contractStartDate;
//    }
//
//    public void setContractStartDate(String contractStartDate) {
//        this.contractStartDate = contractStartDate;
//    }
//
//    public String getContractEndDate() {
//        return contractEndDate;
//    }
//
//    public void setContractEndDate(String contractEndDate) {
//        this.contractEndDate = contractEndDate;
//    }
}
