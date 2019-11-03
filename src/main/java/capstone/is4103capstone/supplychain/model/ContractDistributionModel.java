package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractDistributionModel implements Serializable {
    private BigDecimal draftPercentage;
    private BigDecimal pendingPercentage;
    private BigDecimal activePercentage;
    private BigDecimal terminatedPercentage;
    private BigDecimal mergedPercentage;
    private BigDecimal rejectedPercentage;

    public ContractDistributionModel() {
    }

    public ContractDistributionModel(BigDecimal draftPercentage, BigDecimal pendingPercentage, BigDecimal activePercentage, BigDecimal terminatedPercentage, BigDecimal mergedPercentage, BigDecimal rejectedPercentage) {
        this.draftPercentage = draftPercentage;
        this.pendingPercentage = pendingPercentage;
        this.activePercentage = activePercentage;
        this.terminatedPercentage = terminatedPercentage;
        this.mergedPercentage = mergedPercentage;
        this.rejectedPercentage = rejectedPercentage;
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

    public BigDecimal getActivePercentage() {
        return activePercentage;
    }

    public void setActivePercentage(BigDecimal activePercentage) {
        this.activePercentage = activePercentage;
    }

    public BigDecimal getTerminatedPercentage() {
        return terminatedPercentage;
    }

    public void setTerminatedPercentage(BigDecimal terminatedPercentage) {
        this.terminatedPercentage = terminatedPercentage;
    }

    public BigDecimal getMergedPercentage() {
        return mergedPercentage;
    }

    public void setMergedPercentage(BigDecimal mergedPercentage) {
        this.mergedPercentage = mergedPercentage;
    }

    public BigDecimal getRejectedPercentage() {
        return rejectedPercentage;
    }

    public void setRejectedPercentage(BigDecimal rejectedPercentage) {
        this.rejectedPercentage = rejectedPercentage;
    }
}
