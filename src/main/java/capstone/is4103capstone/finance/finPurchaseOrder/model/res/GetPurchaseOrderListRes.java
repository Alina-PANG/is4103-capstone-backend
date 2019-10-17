package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetPurchaseOrderListRes extends GeneralRes {
    List<PurchaseOrder> purchaseOrderList;

    public GetPurchaseOrderListRes(String message, Boolean hasError, List<PurchaseOrder> purchaseOrderList) {
        super(message, hasError);
        this.purchaseOrderList = purchaseOrderList;
    }

    public GetPurchaseOrderListRes() {
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }
}
