package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.finance.finPurchaseOrder.model.InvoiceModel;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetInvoiceRes extends GeneralRes {
    InvoiceModel invoice;

    public GetInvoiceRes(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    public GetInvoiceRes(String message, Boolean hasError, InvoiceModel invoice) {
        super(message, hasError);
        this.invoice = invoice;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }
}
