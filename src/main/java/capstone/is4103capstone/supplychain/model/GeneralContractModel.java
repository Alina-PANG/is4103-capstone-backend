package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;
import java.io.Serializable;

public class GeneralContractModel implements Serializable {
    private String contractDescription;
    private String name;
    private String code;
    private ContractStatusEnum contractStatus;

    public GeneralContractModel() {
    }

    public GeneralContractModel(String contractDescription, String name, String code, ContractStatusEnum contractStatus) {
        this.contractDescription = contractDescription;
        this.name = name;
        this.code = code;
        this.contractStatus = contractStatus;
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
