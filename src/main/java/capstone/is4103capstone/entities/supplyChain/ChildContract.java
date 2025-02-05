package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChildContract extends DBEntityTemplate {

    @NotNull
    private String serviceCode;
    private BigDecimal contractValue;
    private BigDecimal contractValueInGBP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_contract")
    @JsonIgnore
    private Contract masterContract;

    public ChildContract() {
    }

    public BigDecimal getContractValueInGBP() {
        return contractValueInGBP;
    }

    public void setContractValueInGBP(BigDecimal contractValueInGBP) {
        this.contractValueInGBP = contractValueInGBP;
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

    public Contract getMasterContract() {
        return masterContract;
    }

    public void setMasterContract(Contract masterContract) {
        this.masterContract = masterContract;
    }
}