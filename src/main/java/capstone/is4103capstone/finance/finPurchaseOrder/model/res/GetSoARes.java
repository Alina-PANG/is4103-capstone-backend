package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.finance.finPurchaseOrder.model.SOAModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetSoARes extends GeneralRes {
    List<SOAModel> lineItemList;

    public GetSoARes() {
    }


    public GetSoARes(List<SOAModel> lineItemList) {
        this.lineItemList = lineItemList;
    }

    public GetSoARes(String message, Boolean hasError, List<SOAModel> lineItemList) {
        super(message, hasError);
        this.lineItemList = lineItemList;
    }

    public List<SOAModel> getLineItemList() {
        return lineItemList;
    }

    public void setLineItemList(List<SOAModel> lineItemList) {
        this.lineItemList = lineItemList;
    }
}
