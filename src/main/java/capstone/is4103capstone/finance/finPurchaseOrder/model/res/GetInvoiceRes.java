package capstone.is4103capstone.finance.finPurchaseOrder.model.res;

import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetInvoiceRes extends GeneralRes {
    Invoice invoice;

    public GetInvoiceRes() {
    }

    public GetInvoiceRes(Invoice invoice) {
        this.invoice = invoice;
    }

    public GetInvoiceRes(String message, Boolean hasError, Invoice invoice) {
        super(message, hasError);
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
