package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.enums.ContractStatusEnum;
import capstone.is4103capstone.util.enums.ContractTypeEnum;
import capstone.is4103capstone.util.enums.PurchaseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChildContract extends DBEntityTemplate {

    @NotNull
    private String serviceCode;

    private BigDecimal contractValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_contract")
    @JsonIgnore
    private Contract masterContract;

    public ChildContract() {
    }

    public ChildContract(String objectName, String code, @NotNull String serviceCode, BigDecimal contractValue, Contract masterContract) {
        super(objectName, code);
        this.serviceCode = serviceCode;
        this.contractValue = contractValue;
        this.masterContract = masterContract;
    }

    public ChildContract(@NotNull String serviceCode, BigDecimal contractValue) {
        this.serviceCode = serviceCode;
        this.contractValue = contractValue;
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