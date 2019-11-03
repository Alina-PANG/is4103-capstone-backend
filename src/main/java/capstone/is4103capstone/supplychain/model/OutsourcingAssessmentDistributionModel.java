package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutsourcingAssessmentDistributionModel implements Serializable {
    private BigDecimal pendingBMPercentage;
    private BigDecimal pendingOutsourcingPercentage;
    private BigDecimal approvedPercentage;
    private BigDecimal rejectedPercentage;
    private BigDecimal templatePercentage;
    private BigDecimal outsourcingCreatedPercentage;

    public OutsourcingAssessmentDistributionModel() {
    }

    public OutsourcingAssessmentDistributionModel(BigDecimal pendingBMPercentage, BigDecimal pendingOutsourcingPercentage, BigDecimal approvedPercentage, BigDecimal rejectedPercentage, BigDecimal templatePercentage, BigDecimal outsourcingCreatedPercentage) {
        this.pendingBMPercentage = pendingBMPercentage;
        this.pendingOutsourcingPercentage = pendingOutsourcingPercentage;
        this.approvedPercentage = approvedPercentage;
        this.rejectedPercentage = rejectedPercentage;
        this.templatePercentage = templatePercentage;
        this.outsourcingCreatedPercentage = outsourcingCreatedPercentage;
    }

    public BigDecimal getPendingBMPercentage() {
        return pendingBMPercentage;
    }

    public void setPendingBMPercentage(BigDecimal pendingBMPercentage) {
        this.pendingBMPercentage = pendingBMPercentage;
    }

    public BigDecimal getPendingOutsourcingPercentage() {
        return pendingOutsourcingPercentage;
    }

    public void setPendingOutsourcingPercentage(BigDecimal pendingOutsourcingPercentage) {
        this.pendingOutsourcingPercentage = pendingOutsourcingPercentage;
    }

    public BigDecimal getApprovedPercentage() {
        return approvedPercentage;
    }

    public void setApprovedPercentage(BigDecimal approvedPercentage) {
        this.approvedPercentage = approvedPercentage;
    }

    public BigDecimal getRejectedPercentage() {
        return rejectedPercentage;
    }

    public void setRejectedPercentage(BigDecimal rejectedPercentage) {
        this.rejectedPercentage = rejectedPercentage;
    }

    public BigDecimal getTemplatePercentage() {
        return templatePercentage;
    }

    public void setTemplatePercentage(BigDecimal templatePercentage) {
        this.templatePercentage = templatePercentage;
    }

    public BigDecimal getOutsourcingCreatedPercentage() {
        return outsourcingCreatedPercentage;
    }

    public void setOutsourcingCreatedPercentage(BigDecimal outsourcingCreatedPercentage) {
        this.outsourcingCreatedPercentage = outsourcingCreatedPercentage;
    }
}
