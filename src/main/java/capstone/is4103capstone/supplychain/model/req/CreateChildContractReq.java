package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateChildContractReq implements Serializable {
    private String modifierUsername;
    private String childContractName;
    private String serviceCode;
    private BigDecimal contractValue;
    private String masterContractID;

    public CreateChildContractReq() {
    }

    public CreateChildContractReq(String modifierUsername, String childContractName, String serviceCode, BigDecimal contractValue, String masterContractID) {
        this.modifierUsername = modifierUsername;
        this.childContractName = childContractName;
        this.serviceCode = serviceCode;
        this.contractValue = contractValue;
        this.masterContractID = masterContractID;
    }

    public String getModifierUsername() {
        return modifierUsername;
    }

    public void setModifierUsername(String modifierUsername) {
        this.modifierUsername = modifierUsername;
    }

    public String getChildContractName() {
        return childContractName;
    }

    public void setChildContractName(String childContractName) {
        this.childContractName = childContractName;
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

    public String getMasterContractID() {
        return masterContractID;
    }

    public void setMasterContractID(String masterContractID) {
        this.masterContractID = masterContractID;
    }
}
