package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.util.enums.ChildContractStatusEnum;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChildContractModel implements Serializable {
    private String name;
    private String code;
    private String id;
    private Long seqNo;
    private String serviceCode;
    private BigDecimal contractValue;
    private GeneralEntityModel masterContract;
    private ChildContractStatusEnum childContractStatusEnum;

    public ChildContractModel(String name, String code, String id, Long seqNo, String serviceCode, BigDecimal contractValue, GeneralEntityModel masterContract, ChildContractStatusEnum childContractStatusEnum) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.seqNo = seqNo;
        this.serviceCode = serviceCode;
        this.contractValue = contractValue;
        this.masterContract = masterContract;
        this.childContractStatusEnum = childContractStatusEnum;
    }

    public ChildContractModel() {

    }

    public ChildContractStatusEnum getChildContractStatusEnum() {
        return childContractStatusEnum;
    }

    public void setChildContractStatusEnum(ChildContractStatusEnum childContractStatusEnum) {
        this.childContractStatusEnum = childContractStatusEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
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
}
