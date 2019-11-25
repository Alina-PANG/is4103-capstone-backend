package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutsourcingAssessmentDistributionModel implements Serializable {
    private BigDecimal pending_BM_approval;
    private BigDecimal pending_outsourcing_approval;
    private BigDecimal approved;
    private BigDecimal rejected;
    private BigDecimal template;
    private BigDecimal outsourcing_record_created;

    public OutsourcingAssessmentDistributionModel() {
    }

    public OutsourcingAssessmentDistributionModel(BigDecimal pending_BM_approval, BigDecimal pending_outsourcing_approval, BigDecimal approved, BigDecimal rejected, BigDecimal outsourcing_record_created) {
        this.pending_BM_approval = pending_BM_approval;
        this.pending_outsourcing_approval = pending_outsourcing_approval;
        this.approved = approved;
        this.rejected = rejected;
        this.outsourcing_record_created = outsourcing_record_created;
    }

    public OutsourcingAssessmentDistributionModel(BigDecimal pending_BM_approval, BigDecimal pending_outsourcing_approval, BigDecimal approved, BigDecimal rejected, BigDecimal template, BigDecimal outsourcing_record_created) {
        this.pending_BM_approval = pending_BM_approval;
        this.pending_outsourcing_approval = pending_outsourcing_approval;
        this.approved = approved;
        this.rejected = rejected;
        this.template = template;
        this.outsourcing_record_created = outsourcing_record_created;
    }

    public BigDecimal getPending_BM_approval() {
        return pending_BM_approval;
    }

    public void setPending_BM_approval(BigDecimal pending_BM_approval) {
        this.pending_BM_approval = pending_BM_approval;
    }

    public BigDecimal getPending_outsourcing_approval() {
        return pending_outsourcing_approval;
    }

    public void setPending_outsourcing_approval(BigDecimal pending_outsourcing_approval) {
        this.pending_outsourcing_approval = pending_outsourcing_approval;
    }

    public BigDecimal getApproved() {
        return approved;
    }

    public BigDecimal getOutsourcing_record_created() {
        return outsourcing_record_created;
    }

    public void setOutsourcing_record_created(BigDecimal outsourcing_record_created) {
        this.outsourcing_record_created = outsourcing_record_created;
    }

    public void setApproved(BigDecimal approved) {
        this.approved = approved;
    }

    public BigDecimal getRejected() {
        return rejected;
    }

    public void setRejected(BigDecimal rejected) {
        this.rejected = rejected;
    }

    public BigDecimal getTemplate() {
        return template;
    }

    public void setTemplate(BigDecimal template) {
        this.template = template;
    }
}