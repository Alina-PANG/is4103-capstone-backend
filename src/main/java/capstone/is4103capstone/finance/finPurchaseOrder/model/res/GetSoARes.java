package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetSoARes extends GeneralRes {
    List<StatementOfAcctLineItem> lineItemList;

    public GetSoARes() {
    }

    public GetSoARes(List<StatementOfAcctLineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }

    public GetSoARes(String message, Boolean hasError, List<StatementOfAcctLineItem> lineItemList) {
        super(message, hasError);
        this.lineItemList = lineItemList;
    }

    public List<StatementOfAcctLineItem> getLineItemList() {
        return lineItemList;
    }

    public void setLineItemList(List<StatementOfAcctLineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }
}
