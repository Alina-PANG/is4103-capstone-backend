package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.entities.finance.Service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vendor extends DBEntityTemplate {
    private String serviceDescription;
    private String relationshipManagerName;
    private String relationshipManagerEmail;
    private String billingContactName;
    private String billingContactEmail;
    private String escalationContactName;
    private String escalationContactEmail;

    @ManyToMany(mappedBy = "vendors", fetch = FetchType.EAGER)
    private List<Team> businessUnits = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "outsourcedVendor")
    private List<Outsourcing> outsourcingList = new ArrayList<>();

    public Vendor(String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail) {
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

    public List<Service> getservices() {
        return services;
    }

    public void setservices(List<Service> services) {
        this.services = services;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Outsourcing> getOutsourcingList() {
        return outsourcingList;
    }

    public void setOutsourcingList(List<Outsourcing> outsourcingList) {
        this.outsourcingList = outsourcingList;
    }

    public List<Team> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<Team> businessUnits) {
        this.businessUnits = businessUnits;
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
