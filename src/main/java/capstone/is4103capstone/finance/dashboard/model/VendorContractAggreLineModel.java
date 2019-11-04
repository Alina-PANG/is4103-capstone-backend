package capstone.is4103capstone.finance.dashboard.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendorContractAggreLineModel {
    // level: vendor, or child contract(which is service, in this case)
    String levelCode;
    String levelName;
    BigDecimal totalContractValue;
    BigDecimal commitedAmount;
    BigDecimal paidAmount;
    BigDecimal actualsAmount;
    BigDecimal accuralsAmount;
    String contractStatus;
    String contractCode;
    Boolean hasContract;

    List<VendorContractAggreLineModel> nextLevel = new ArrayList<>();

    public VendorContractAggreLineModel() {
    }
    public VendorContractAggreLineModel(VendorPurchaseAmountDBModel purchase, boolean hasContract){
        setLevelName(purchase.getVendorName());
        setLevelCode(purchase.getVendorCode());

        setHasContract(false);
        setTotalContractValue(BigDecimal.ZERO);
        setContractCode("No contract available");
        setContractStatus(null);
        setCommitedAmount(purchase.getTotalCommitted());
        setPaidAmount(purchase.getTotalPaid());
        setActualsAmount(purchase.getTotalActual());
        setAccuralsAmount(purchase.getTotalActual().add(purchase.getTotalPaid().negate()));
        setNextLevel(null);
    }
    //without next level
    public VendorContractAggreLineModel(VendorAndContractDBModel contract , VendorPurchaseAmountDBModel purchase) {
        setLevelCode(contract.getVendorCode());
        setLevelName(contract.getVendorName());
        setTotalContractValue(contract.getActiveContractValueByVendor());
        setContractCode(contract.getContractCode());
        setContractStatus(contract.getContractStatus().name());

        setCommitedAmount(purchase.getTotalCommitted());
        setPaidAmount(purchase.getTotalPaid());
        setActualsAmount(purchase.getTotalActual());
        setAccuralsAmount(purchase.getTotalActual().add(purchase.getTotalPaid().negate()));
        setNextLevel(null);
        setHasContract(true);
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Boolean getHasContract() {
        return hasContract;
    }

    public void setHasContract(Boolean hasContract) {
        this.hasContract = hasContract;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public BigDecimal getTotalContractValue() {
        return totalContractValue;
    }

    public void setTotalContractValue(BigDecimal totalContractValue) {
        this.totalContractValue = totalContractValue;
    }

    public BigDecimal getCommitedAmount() {
        return commitedAmount;
    }

    public void setCommitedAmount(BigDecimal commitedAmount) {
        this.commitedAmount = commitedAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getActualsAmount() {
        return actualsAmount;
    }

    public void setActualsAmount(BigDecimal actualsAmount) {
        this.actualsAmount = actualsAmount;
    }

    public BigDecimal getAccuralsAmount() {
        return accuralsAmount;
    }

    public void setAccuralsAmount(BigDecimal accuralsAmount) {
        this.accuralsAmount = accuralsAmount;
    }

    public List<VendorContractAggreLineModel> getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(List<VendorContractAggreLineModel> nextLevel) {
        this.nextLevel = nextLevel;
    }
}
