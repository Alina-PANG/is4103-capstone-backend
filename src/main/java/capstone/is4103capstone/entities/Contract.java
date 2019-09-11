package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.enums.ContractStatusEnum;
import capstone.is4103capstone.entities.enums.ContractTypeEnum;
import capstone.is4103capstone.entities.enums.PurchaseTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Contract extends DBEntityTemplate {
    private PurchaseTypeEnum purchaseType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date renewalStartDate;

    private String contractTerm; //no of months, evergreen, perpetual
    private ContractTypeEnum contractType;
    //to-do: hierarchy
    private ContractStatusEnum contractStatus;
    private Integer noticeDaysToExit;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cpgReviewAlertDate;

    private String spendType; //not very sure

    @OneToOne(mappedBy = "contract")
    private Vendor vendor;

    public Contract(PurchaseTypeEnum purchaseType, Date startDate, Date endDate, String contractTerm, ContractTypeEnum contractType, ContractStatusEnum contractStatus, Integer noticeDaysToExit, String spendType, Vendor vendor) {
        this.purchaseType = purchaseType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractTerm = contractTerm;
        this.contractType = contractType;
        this.contractStatus = contractStatus;
        this.noticeDaysToExit = noticeDaysToExit;
        this.spendType = spendType;
        this.vendor = vendor;
    }

    public Contract() {
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