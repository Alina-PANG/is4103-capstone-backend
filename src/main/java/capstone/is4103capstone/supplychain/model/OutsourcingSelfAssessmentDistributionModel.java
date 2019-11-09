package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutsourcingSelfAssessmentDistributionModel implements Serializable {
    private BigDecimal draft;
    private BigDecimal pending;
    private BigDecimal rejected;
    private BigDecimal approved;

    public OutsourcingSelfAssessmentDistributionModel() {
    }

    public OutsourcingSelfAssessmentDistributionModel(BigDecimal draft, BigDecimal pending, BigDecimal rejected, BigDecimal approved) {
        this.draft = draft;
        this.pending = pending;
        this.rejected = rejected;
        this.approved = approved;
    }

    public BigDecimal getDraft() {
        return draft;
    }

    public void setDraft(BigDecimal draft) {
        this.draft = draft;
    }

    public BigDecimal getPending() {
        return pending;
    }

    public void setPending(BigDecimal pending) {
        this.pending = pending;
    }

    public BigDecimal getRejected() {
        return rejected;
    }

    public void setRejected(BigDecimal rejected) {
        this.rejected = rejected;
    }

    public BigDecimal getApproved() {
        return approved;
    }

    public void setApproved(BigDecimal approved) {
        this.approved = approved;
    }
}

