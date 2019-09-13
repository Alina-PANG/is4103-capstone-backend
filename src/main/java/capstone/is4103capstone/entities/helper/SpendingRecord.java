package capstone.is4103capstone.entities.helper;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
public class SpendingRecord {
    private String poCode;
    private Date createdDate;
    private BigDecimal spendingAmt;

    public SpendingRecord() {
    }

    public SpendingRecord(String poCode, Date createdDate, BigDecimal spendingAmt) {
        this.poCode = poCode;
        this.createdDate = createdDate;
        this.spendingAmt = spendingAmt;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getSpendingAmt() {
        return spendingAmt;
    }

    public void setSpendingAmt(BigDecimal spendingAmt) {
        this.spendingAmt = spendingAmt;
    }
}
