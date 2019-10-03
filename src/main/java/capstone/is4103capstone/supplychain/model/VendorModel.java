package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;

import java.io.Serializable;
import java.util.List;

public class VendorModel implements Serializable {
    private String id;
    private String code;
    private String vendorName;
    private Long seqNo;
    private List<GeneralEntityModel> businessUnits;
    private String serviceDescription;
    private String relationshipManagerName;
    private String relationshipManagerEmail;
    private String billingContactName;
    private String billingContactEmail;
    private String escalationContactName;
    private String escalationContactEmail;

    public VendorModel(String id, String code, String vendorName, Long seqNo, List<GeneralEntityModel> businessUnits, String serviceDescription, String relationshipManagerName, String relationshipManagerEmail, String billingContactName, String billingContactEmail, String escalationContactName, String escalationContactEmail) {
        this.id = id;
        this.code = code;
        this.vendorName = vendorName;
        this.seqNo = seqNo;
        this.businessUnits = businessUnits;
        this.serviceDescription = serviceDescription;
        this.relationshipManagerName = relationshipManagerName;
        this.relationshipManagerEmail = relationshipManagerEmail;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.escalationContactName = escalationContactName;
        this.escalationContactEmail = escalationContactEmail;
    }

    public VendorModel() {
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public List<GeneralEntityModel> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<GeneralEntityModel> businessUnits) {
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
}
