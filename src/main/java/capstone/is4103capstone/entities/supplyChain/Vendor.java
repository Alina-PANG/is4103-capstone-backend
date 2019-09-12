package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.entities.finance.Item;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Vendor extends DBEntityTemplate {
    private String businessUnit; //vendor company
    private String serviceDescription;
    private String relationshipManagerName;
    private String relationshipManagerEmail;
    private String billingContactName;
    private String billingContactEmail;
    private String escalationContactName;
    private String escalationContactEmail;

    @OneToMany(mappedBy = "vendor")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;


    @OneToMany(mappedBy = "outsourcedVendor")
    private List<Outsourcing> outsourcingList;

    public Vendor(String businessUnit, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail) {
        this.businessUnit = businessUnit;
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
    }

    public Vendor() {
    }

    public List<Outsourcing> getOutsourcingList() {
        return outsourcingList;
    }

    public void setOutsourcingList(List<Outsourcing> outsourcingList) {
        this.outsourcingList = outsourcingList;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getRelationshipManagerName() {
        return relationshipManagerName;
    }

    public void setRelationshipManagerName(String relationshipManagerName) {
        this.relationshipManagerName = relationshipManagerName;
    }

    public String getRelationshipManagerEmail() {
        return relationshipManagerEmail;
    }

    public void setRelationshipManagerEmail(String relationshipManagerEmail) {
        this.relationshipManagerEmail = relationshipManagerEmail;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getEscalationContactName() {
        return escalationContactName;
    }

    public void setEscalationContactName(String escalationContactName) {
        this.escalationContactName = escalationContactName;
    }

    public String getEscalationContactEmail() {
        return escalationContactEmail;
    }

    public void setEscalationContactEmail(String escalationContactEmail) {
        this.escalationContactEmail = escalationContactEmail;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
