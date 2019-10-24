package capstone.is4103capstone.finance.finPurchaseOrder.model.req;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreateSoAByScheduleReq implements Serializable {
    private Date startDate;
    private Date endDate;
    private int frequency;
    private String poId;
    private int numFrequency;
    private BigDecimal totalAmount;

    public CreateSoAByScheduleReq() {
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumFrequency() {
        return numFrequency;
    }

    public void setNumFrequency(int numFrequency) {
        this.numFrequency = numFrequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
