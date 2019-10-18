package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetPurchaseOrderRes extends GeneralRes {
    PurchaseOrder purchaseOrder;
    List<String> bjfCode;
    List<String> bjfId;

    public GetPurchaseOrderRes(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
    }

    public GetPurchaseOrderRes(PurchaseOrder purchaseOrder, List<String> bjfCode, List<String> bjfId) {
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
        this.bjfId = bjfId;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder, List<String> bjfCode, List<String> bjfId) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
        this.bjfId = bjfId;
    }

    public List<String> getBjfCode() {
        return bjfCode;
    }

    public void setBjfCode(List<String> bjfCode) {
        this.bjfCode = bjfCode;
    }

    public List<String> getBjfId() {
        return bjfId;
    }

    public void setBjfId(List<String> bjfId) {
        this.bjfId = bjfId;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
