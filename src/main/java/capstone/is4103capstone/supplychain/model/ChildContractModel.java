package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChildContractModel implements Serializable {
    private String childContractName;
    private String code;
    private String id;
    private Long seqNo;
    private String serviceName;
    private BigDecimal contractValue;
    private GeneralEntityModel masterContract;
    private String currencyCode;
    private Boolean canUpdate;

    public ChildContractModel(String childContractName, String code, String id, Long seqNo, String serviceName, BigDecimal contractValue, GeneralEntityModel masterContract, String currencyCode, Boolean canUpdate) {
        this.childContractName = childContractName;
        this.code = code;
        this.id = id;
        this.seqNo = seqNo;
        this.serviceName = serviceName;
        this.contractValue = contractValue;
        this.masterContract = masterContract;
        this.currencyCode = currencyCode;
        this.canUpdate = canUpdate;
    }

    public ChildContractModel() {

    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getChildContractName() {
        return childContractName;
    }

    public void setChildContractName(String childContractName) {
        this.childContractName = childContractName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getContractValue() {
        return contractValue;
    }

    public void setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
    }

    public GeneralEntityModel getMasterContract() {
        return masterContract;
    }

    public void setMasterContract(GeneralEntityModel masterContract) {
        this.masterContract = masterContract;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}
