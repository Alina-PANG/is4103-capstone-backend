package capstone.is4103capstone.finance.finPurchaseOrder.model;

import capstone.is4103capstone.entities.finance.PurchaseOrderLineItem;

import java.util.List;

public class createPOReq {
    private String vendorId;
    private List<PurchaseOrderLineItem> purchaseOrderLineItemList;
    private String currencyCode;

    public createPOReq() {
    }

    public createPOReq(String vendorId, List<PurchaseOrderLineItem> purchaseOrderLineItemList, String currencyCode) {
        this.vendorId = vendorId;
        this.purchaseOrderLineItemList = purchaseOrderLineItemList;
        this.currencyCode = currencyCode;
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
