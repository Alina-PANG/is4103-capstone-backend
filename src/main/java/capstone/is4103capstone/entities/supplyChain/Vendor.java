package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Convert(converter = StringListConverter.class)
    private List<String> businessUnits = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "vendor")
    private List<PurchaseOrder> purchaseOrders= new ArrayList<>();

    @OneToMany(mappedBy = "outsourcedVendor")
    private List<Outsourcing> outsourcingList = new ArrayList<>();

    public Vendor(String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail, List<String> businessUnits, List<Contract> contracts, List<PurchaseOrder> purchaseOrders, List<Outsourcing> outsourcingList) {
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
        this.businessUnits = businessUnits;
        this.contracts = contracts;
        this.purchaseOrders = purchaseOrders;
        this.outsourcingList = outsourcingList;
    }

    public Vendor(String objectName, String code, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail, List<String> businessUnits, List<Contract> contracts, List<PurchaseOrder> purchaseOrders, List<Outsourcing> outsourcingList) {
        super(objectName, code);
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
        this.businessUnits = businessUnits;
        this.contracts = contracts;
        this.purchaseOrders = purchaseOrders;
        this.outsourcingList = outsourcingList;
    }

    public Vendor(String objectName, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail, List<String> businessUnits, List<Contract> contracts, List<PurchaseOrder> purchaseOrders, List<Outsourcing> outsourcingList) {
        super(objectName);
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
        this.businessUnits = businessUnits;
        this.contracts = contracts;
        this.purchaseOrders = purchaseOrders;
        this.outsourcingList = outsourcingList;
    }

    public Vendor(String objectName, String code, String hierachyPath, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail, List<String> businessUnits, List<Contract> contracts, List<PurchaseOrder> purchaseOrders, List<Outsourcing> outsourcingList) {
        super(objectName, code, hierachyPath);
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
        this.businessUnits = businessUnits;
        this.contracts = contracts;
        this.purchaseOrders = purchaseOrders;
        this.outsourcingList = outsourcingList;
    }

    public Vendor(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail, List<String> businessUnits, List<Contract> contracts, List<PurchaseOrder> purchaseOrders, List<Outsourcing> outsourcingList) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
        this.businessUnits = businessUnits;
        this.contracts = contracts;
        this.purchaseOrders = purchaseOrders;
        this.outsourcingList = outsourcingList;
    }

    public Vendor() {
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }


    public List<Outsourcing> getOutsourcingList() {
        return outsourcingList;
    }

    public void setOutsourcingList(List<Outsourcing> outsourcingList) {
        this.outsourcingList = outsourcingList;
    }

    public List<String> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<String> businessUnits) {
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
