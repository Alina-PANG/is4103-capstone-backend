package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.util.enums.ContractStatusEnum;
import capstone.is4103capstone.util.enums.ContractTypeEnum;
import capstone.is4103capstone.util.enums.PurchaseTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContractModel implements Serializable {
    private String contractDescription;
    private String name;
    private String code;
    private String id;
    private Long seqNo;
    private PurchaseTypeEnum purchaseType;
    private String spendType;
    private String contractTerm;
    private ContractTypeEnum contractType;
    private ContractStatusEnum contractStatus;
    private Integer noticeDaysToExit;
    private GeneralEntityModel vendor;
    private GeneralEntityModel employeeInChargeContract;
    private GeneralEntityModel team;
    private BigDecimal totalContractValue;
    private String currencyCode;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date renewalStartDate;

    @Temporal(TemporalType.DATE)
    private Date cpgReviewAlertDate;

    public ContractModel(String contractDescription, String name, String code, String id, Long seqNo, PurchaseTypeEnum purchaseType, String spendType, String contractTerm, ContractTypeEnum contractType, ContractStatusEnum contractStatus, Integer noticeDaysToExit, GeneralEntityModel vendor, GeneralEntityModel employeeInChargeContract, GeneralEntityModel team, BigDecimal totalContractValue, String currencyCode, Date startDate, Date endDate, Date renewalStartDate, Date cpgReviewAlertDate) {
        this.contractDescription = contractDescription;
        this.name = name;
        this.code = code;
        this.id = id;
        this.seqNo = seqNo;
        this.purchaseType = purchaseType;
        this.spendType = spendType;
        this.contractTerm = contractTerm;
        this.contractType = contractType;
        this.contractStatus = contractStatus;
        this.noticeDaysToExit = noticeDaysToExit;
        this.vendor = vendor;
        this.employeeInChargeContract = employeeInChargeContract;
        this.team = team;
        this.totalContractValue = totalContractValue;
        this.currencyCode = currencyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.renewalStartDate = renewalStartDate;
        this.cpgReviewAlertDate = cpgReviewAlertDate;
    }

    public ContractModel() {
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public BigDecimal getTotalContractValue() {
        return totalContractValue;
    }

    public void setTotalContractValue(BigDecimal totalContractValue) {
        this.totalContractValue = totalContractValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public GeneralEntityModel getTeam() {
        return team;
    }

    public void setTeam(GeneralEntityModel team) {
        this.team = team;
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

    public PurchaseTypeEnum getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseTypeEnum purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getSpendType() {
        return spendType;
    }

    public void setSpendType(String spendType) {
        this.spendType = spendType;
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

    public GeneralEntityModel getVendor() {
        return vendor;
    }

    public void setVendor(GeneralEntityModel vendor) {
        this.vendor = vendor;
    }

    public GeneralEntityModel getEmployeeInChargeContract() {
        return employeeInChargeContract;
    }

    public void setEmployeeInChargeContract(GeneralEntityModel employeeInChargeContract) {
        this.employeeInChargeContract = employeeInChargeContract;
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

    public Date getCpgReviewAlertDate() {
        return cpgReviewAlertDate;
    }

    public void setCpgReviewAlertDate(Date cpgReviewAlertDate) {
        this.cpgReviewAlertDate = cpgReviewAlertDate;
    }
}
