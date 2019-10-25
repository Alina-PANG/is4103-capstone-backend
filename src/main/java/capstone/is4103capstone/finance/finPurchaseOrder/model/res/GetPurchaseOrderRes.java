package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetPurchaseOrderRes extends GeneralRes {
    PurchaseOrder purchaseOrder;
    List<String> bjfCode;
    Vendor v;

    public GetPurchaseOrderRes(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
    }

    public GetPurchaseOrderRes(PurchaseOrder purchaseOrder, List<String> bjfCode, Vendor v) {
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
        this.v = v;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder, List<String> bjfCode, Vendor v) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
        this.v = v;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder, List<String> bjfCode) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
    }

    public List<String> getBjfCode() {
        return bjfCode;
    }

    public void setBjfCode(List<String> bjfCode) {
        this.bjfCode = bjfCode;
    }



    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
