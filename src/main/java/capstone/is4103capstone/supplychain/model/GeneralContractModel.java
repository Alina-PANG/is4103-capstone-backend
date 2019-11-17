package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;
import java.io.Serializable;
import java.util.Date;

public class GeneralContractModel implements Serializable {
    private String contractDescription;
    private String name;
    private String code;
    private ContractStatusEnum contractStatus;
    private Date endDate;
    private Date renewDate;

    public GeneralContractModel() {
    }

    public GeneralContractModel(String contractDescription, String name, String code, ContractStatusEnum contractStatus, Date endDate, Date renewDate) {
        this.contractDescription = contractDescription;
        this.name = name;
        this.code = code;
        this.contractStatus = contractStatus;
        this.endDate = endDate;
        this.renewDate = renewDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
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

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }
}
