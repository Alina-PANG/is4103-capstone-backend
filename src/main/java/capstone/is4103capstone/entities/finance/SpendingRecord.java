package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.ActualsTable;
import capstone.is4103capstone.util.enums.SpendingTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class SpendingRecord extends DBEntityTemplate {

    private String relatedItem;
    private BigDecimal spendingAmt;
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_table_id")
    @JsonIgnore
    private ActualsTable actuals;

    public SpendingRecord() {
    }

    public String getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(String relatedItem) {
        this.relatedItem = relatedItem;
    }

    public BigDecimal getSpendingAmt() {
        return spendingAmt;
    }

    public void setSpendingAmt(BigDecimal spendingAmt) {
        this.spendingAmt = spendingAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ActualsTable getActuals() {
        return actuals;
    }

    public void setActuals(ActualsTable actuals) {
        this.actuals = actuals;
    }
}
