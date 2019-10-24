package capstone.is4103capstone.finance.finPurchaseOrder.model.req;

import java.math.BigDecimal;

public class UpdateSoAReq {
    BigDecimal paid;

    public UpdateSoAReq() {
    }

    public UpdateSoAReq(BigDecimal paid) {
        this.paid = paid;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
