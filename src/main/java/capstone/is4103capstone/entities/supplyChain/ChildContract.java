package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.enums.ChildContractStatusEnum;
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
    private ChildContractStatusEnum childContractStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_contract")
    @JsonIgnore
    private Contract masterContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_approve")
    @JsonIgnore
    private Employee approver;

    public ChildContract() {
    }

    public ChildContract(@NotNull String serviceCode, BigDecimal contractValue, Contract masterContract, Employee approver) {
        this.serviceCode = serviceCode;
        this.contractValue = contractValue;
        this.masterContract = masterContract;
        this.approver = approver;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public ChildContractStatusEnum getChildContractStatus() {
        return childContractStatus;
    }

    public void setChildContractStatus(ChildContractStatusEnum childContractStatus) {
        this.childContractStatus = childContractStatus;
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