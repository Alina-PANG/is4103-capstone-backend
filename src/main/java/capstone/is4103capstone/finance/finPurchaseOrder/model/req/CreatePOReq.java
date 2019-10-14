package capstone.is4103capstone.finance.finPurchaseOrder.model.req;

import capstone.is4103capstone.entities.finance.PurchaseOrderLineItem;

import java.io.Serializable;
import java.util.List;

public class CreatePOReq implements Serializable {
    private String vendorId;
    private List<PurchaseOrderLineItem> purchaseOrderLineItemList;
    private String currencyCode;
    private List<String> relatedBJF;
    private Boolean toSubmit; // submit or draft
    String username;



    public CreatePOReq() {
    }

    public Boolean getToSubmit() {
        return toSubmit;
    }

    public void setToSubmit(Boolean toSubmit) {
        this.toSubmit = toSubmit;
    }

    public CreatePOReq(String vendorId, List<PurchaseOrderLineItem> purchaseOrderLineItemList, String currencyCode, List<String> relatedBJF, String username) {
        this.vendorId = vendorId;
        this.purchaseOrderLineItemList = purchaseOrderLineItemList;
        this.currencyCode = currencyCode;
        this.relatedBJF = relatedBJF;
        this.username = username;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public List<PurchaseOrderLineItem> getPurchaseOrderLineItemList() {
        return purchaseOrderLineItemList;
    }

    public void setPurchaseOrderLineItemList(List<PurchaseOrderLineItem> purchaseOrderLineItemList) {
        this.purchaseOrderLineItemList = purchaseOrderLineItemList;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
