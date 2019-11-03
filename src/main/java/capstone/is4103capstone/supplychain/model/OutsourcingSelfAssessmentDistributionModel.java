package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OutsourcingSelfAssessmentDistributionModel implements Serializable {
    private BigDecimal draftPercentage;
    private BigDecimal pendingPercentage;
    private BigDecimal rejectedPercentage;
    private BigDecimal approvedPercentage;

    public OutsourcingSelfAssessmentDistributionModel() {
    }

    public OutsourcingSelfAssessmentDistributionModel(BigDecimal draftPercentage, BigDecimal pendingPercentage, BigDecimal rejectedPercentage, BigDecimal approvedPercentage) {
        this.draftPercentage = draftPercentage;
        this.pendingPercentage = pendingPercentage;
        this.rejectedPercentage = rejectedPercentage;
        this.approvedPercentage = approvedPercentage;
    }

    public BigDecimal getDraftPercentage() {
        return draftPercentage;
    }

    public void setDraftPercentage(BigDecimal draftPercentage) {
        this.draftPercentage = draftPercentage;
    }

    public BigDecimal getPendingPercentage() {
        return pendingPercentage;
    }

    public void setPendingPercentage(BigDecimal pendingPercentage) {
        this.pendingPercentage = pendingPercentage;
    }

    public BigDecimal getRejectedPercentage() {
        return rejectedPercentage;
    }

    public void setRejectedPercentage(BigDecimal rejectedPercentage) {
        this.rejectedPercentage = rejectedPercentage;
    }

    public BigDecimal getApprovedPercentage() {
        return approvedPercentage;
    }

    public void setApprovedPercentage(BigDecimal approvedPercentage) {
        this.approvedPercentage = approvedPercentage;
    }
}

