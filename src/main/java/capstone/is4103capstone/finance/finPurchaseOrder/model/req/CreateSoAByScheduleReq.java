package capstone.is4103capstone.finance.finPurchaseOrder.model.req;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreateSoAByScheduleReq implements Serializable {
    private String startDate;
    private String endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
