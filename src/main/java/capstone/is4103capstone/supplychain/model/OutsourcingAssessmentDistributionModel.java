package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutsourcingAssessmentDistributionModel implements Serializable {
    private BigDecimal pendingBM;
    private BigDecimal pendingOutsourcing;
    private BigDecimal approved;
    private BigDecimal rejected;
    private BigDecimal template;
    private BigDecimal outsourcingCreated;

    public OutsourcingAssessmentDistributionModel() {
    }

    public OutsourcingAssessmentDistributionModel(BigDecimal pendingBM, BigDecimal pendingOutsourcing, BigDecimal approved, BigDecimal rejected, BigDecimal template, BigDecimal outsourcingCreated) {
        this.pendingBM = pendingBM;
        this.pendingOutsourcing = pendingOutsourcing;
        this.approved = approved;
        this.rejected = rejected;
        this.template = template;
        this.outsourcingCreated = outsourcingCreated;
    }

    public BigDecimal getPendingBM() {
        return pendingBM;
    }

    public void setPendingBM(BigDecimal pendingBM) {
        this.pendingBM = pendingBM;
    }

    public BigDecimal getPendingOutsourcing() {
        return pendingOutsourcing;
    }

    public void setPendingOutsourcing(BigDecimal pendingOutsourcing) {
        this.pendingOutsourcing = pendingOutsourcing;
    }

    public BigDecimal getApproved() {
        return approved;
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

    public BigDecimal getOutsourcingCreated() {
        return outsourcingCreated;
    }

    public void setOutsourcingCreated(BigDecimal outsourcingCreated) {
        this.outsourcingCreated = outsourcingCreated;
    }
}