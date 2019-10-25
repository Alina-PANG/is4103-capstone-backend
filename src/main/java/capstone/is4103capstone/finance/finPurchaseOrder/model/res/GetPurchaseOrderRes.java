package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.finPurchaseOrder.model.POModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetPurchaseOrderRes extends GeneralRes {
    POModel purchaseOrder;
    List<String> bjfCode;

    public GetPurchaseOrderRes() {
    }

    public GetPurchaseOrderRes(String message, Boolean hasError, POModel purchaseOrder, List<String> bjfCode) {
        super(message, hasError);
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;
    }

    public GetPurchaseOrderRes(POModel purchaseOrder, List<String> bjfCode) {
        this.purchaseOrder = purchaseOrder;
        this.bjfCode = bjfCode;

    }

    public POModel getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(POModel purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<String> getBjfCode() {
        return bjfCode;
    }

    public void setBjfCode(List<String> bjfCode) {
        this.bjfCode = bjfCode;
    }

}

