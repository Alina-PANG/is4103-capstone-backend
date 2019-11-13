package capstone.is4103capstone.finance.dashboard.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PoPmtScheduleModel implements Serializable {

    private String time;
    private BigDecimal committed;
    private BigDecimal paid;
    private BigDecimal expectedActual;

    public PoPmtScheduleModel() {
    }

    public PoPmtScheduleModel(String time, BigDecimal committed, BigDecimal paid) {
        this.time = time;
        this.committed = committed == null? BigDecimal.ZERO : committed;
        this.paid = paid == null? BigDecimal.ZERO:paid;
    }

    public PoPmtScheduleModel(String time, BigDecimal expectedActual) {
        this.time = time;
        this.expectedActual = expectedActual == null?BigDecimal.ZERO:expectedActual;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getCommitted() {
        return committed;
    }

    public void setCommitted(BigDecimal committed) {
        this.committed = committed;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public BigDecimal getExpectedActual() {
        return expectedActual;
    }

    public void setExpectedActual(BigDecimal expectedActual) {
        this.expectedActual = expectedActual;
    }
}
