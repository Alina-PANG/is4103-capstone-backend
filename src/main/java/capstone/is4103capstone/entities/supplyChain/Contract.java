package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.enums.ContractStatusEnum;
import capstone.is4103capstone.util.enums.ContractTypeEnum;
import capstone.is4103capstone.util.enums.PurchaseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.collections4.bidimap.AbstractBidiMapDecorator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contract extends DBEntityTemplate {
    private String contractDescription;
    private PurchaseTypeEnum purchaseType;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date renewalStartDate;

    private String contractTerm; //no of months, evergreen, perpetual
    private ContractTypeEnum contractType;
    //to-do: hierarchy
    private ContractStatusEnum contractStatus;
    private Integer noticeDaysToExit;
    private BigDecimal totalContractValue;

    @Temporal(TemporalType.DATE)
    private Date cpgReviewAlertDate;

    private String spendType; //not very sure

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

    @OneToMany(mappedBy = "masterContract",fetch = FetchType.LAZY)
    private List<ChildContract> childContractList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_incharge")
    @JsonIgnore
    private Employee employeeInChargeContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    private String currencyCode;

    public Contract(String contractDescription, PurchaseTypeEnum purchaseType, Date startDate, Date endDate, Date renewalStartDate, String contractTerm, ContractTypeEnum contractType, ContractStatusEnum contractStatus, Integer noticeDaysToExit, BigDecimal totalContractValue, Date cpgReviewAlertDate, String spendType, Vendor vendor, List<ChildContract> childContractList, Employee employeeInChargeContract, Team team, String currencyCode) {
        this.purchaseType = purchaseType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.renewalStartDate = renewalStartDate;
        this.contractTerm = contractTerm;
        this.contractType = contractType;
        this.contractStatus = contractStatus;
        this.noticeDaysToExit = noticeDaysToExit;
        this.totalContractValue = totalContractValue;
        this.cpgReviewAlertDate = cpgReviewAlertDate;
        this.spendType = spendType;
        this.vendor = vendor;
        this.childContractList = childContractList;
        this.employeeInChargeContract = employeeInChargeContract;
        this.team = team;
        this.currencyCode = currencyCode;
        this.contractDescription = contractDescription;
    }

    public Contract() {
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<ChildContract> getChildContractList() {
        return childContractList;
    }

    public void setChildContractList(List<ChildContract> childContractList) {
        this.childContractList = childContractList;
    }

    public Employee getEmployeeInChargeContract() {
        return employeeInChargeContract;
    }

    public void setEmployeeInChargeContract(Employee employeeInChargeContract) {
        this.employeeInChargeContract = employeeInChargeContract;
    }

    public BigDecimal getTotalContractValue() {
        return totalContractValue;
    }

    public void setTotalContractValue(BigDecimal totalContractValue) {
        this.totalContractValue = totalContractValue;
    }

    public PurchaseTypeEnum getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseTypeEnum purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRenewalStartDate() {
        return renewalStartDate;
    }

    public void setRenewalStartDate(Date renewalStartDate) {
        this.renewalStartDate = renewalStartDate;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public ContractTypeEnum getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypeEnum contractType) {
        this.contractType = contractType;
    }

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getNoticeDaysToExit() {
        return noticeDaysToExit;
    }

    public void setNoticeDaysToExit(Integer noticeDaysToExit) {
        this.noticeDaysToExit = noticeDaysToExit;
    }

    public Date getCpgReviewAlertDate() {
        return cpgReviewAlertDate;
    }

    public void setCpgReviewAlertDate(Date cpgReviewAlertDate) {
        this.cpgReviewAlertDate = cpgReviewAlertDate;
    }

    public String getSpendType() {
        return spendType;
    }

    public void setSpendType(String spendType) {
        this.spendType = spendType;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
