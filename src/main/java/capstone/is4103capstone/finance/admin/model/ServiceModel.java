package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServiceModel implements Serializable {
    String serviceName;
    String serviceCode;
    String vendorCode;
    String vendorName;
    String measureUnit;
    boolean hasActiveContract;

    BigDecimal currentPrice;
    String currentContractCode;
    String contractStartDate;
    String contractEndDate;
    String comment;

    public ServiceModel() {
    }

    public ServiceModel(String serviceName, String serviceCode) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
    }

    public ServiceModel(String serviceName, String serviceCode, String vendorCode, String vendorName, String measureUnit) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.measureUnit = measureUnit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isHasActiveContract() {
        return hasActiveContract;
    }

    public void setHasActiveContract(boolean hasActiveContract) {
        this.hasActiveContract = hasActiveContract;
    }

    public String getserviceName() {
        return serviceName;
    }

    public void setserviceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getserviceCode() {
        return serviceCode;
    }

    public void setserviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrentContractCode() {
        return currentContractCode;
    }

    public void setCurrentContractCode(String currentContractCode) {
        this.currentContractCode = currentContractCode;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }
}
