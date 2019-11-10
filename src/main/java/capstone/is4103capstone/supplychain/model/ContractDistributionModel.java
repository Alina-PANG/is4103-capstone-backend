package capstone.is4103capstone.supplychain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractDistributionModel implements Serializable {
    private BigDecimal draft;
    private BigDecimal pending;
    private BigDecimal active;
    private BigDecimal terminated;
    private BigDecimal merged;
    private BigDecimal rejected;

    public ContractDistributionModel() {
    }

    public ContractDistributionModel(BigDecimal draft, BigDecimal pending, BigDecimal active, BigDecimal terminated, BigDecimal merged, BigDecimal rejected) {
        this.draft = draft;
        this.pending = pending;
        this.active = active;
        this.terminated = terminated;
        this.merged = merged;
        this.rejected = rejected;
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

    public BigDecimal getActive() {
        return active;
    }

    public void setActive(BigDecimal active) {
        this.active = active;
    }

    public BigDecimal getTerminated() {
        return terminated;
    }

    public void setTerminated(BigDecimal terminated) {
        this.terminated = terminated;
    }

    public BigDecimal getMerged() {
        return merged;
    }

    public void setMerged(BigDecimal merged) {
        this.merged = merged;
    }

    public BigDecimal getRejected() {
        return rejected;
    }

    public void setRejected(BigDecimal rejected) {
        this.rejected = rejected;
    }
}
