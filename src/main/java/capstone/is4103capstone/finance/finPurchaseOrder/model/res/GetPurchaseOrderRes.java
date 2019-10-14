package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetPurchaseOrderRes extends GeneralRes {
    PurchaseOrder purchaseOrder;

    public GetPurchaseOrderRes(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, PurchaseOrder purchaseOrder) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
