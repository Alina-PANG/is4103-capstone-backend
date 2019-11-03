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

//    private String poId;
    private String serviceId;
    private BigDecimal spendingAmt;
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "po_id",nullable = true)
    @JsonIgnore
    private PurchaseOrder relatedPO;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "actual_table_id")
//    @JsonIgnore
//    private ActualsTable actuals;

    public SpendingRecord() {
    }

    public SpendingRecord(String serviceId, BigDecimal spendingAmt, String currencyCode, PurchaseOrder relatedPO) {
        this.serviceId = serviceId;
        if (spendingAmt == null)
            this.spendingAmt = BigDecimal.ZERO;
        else
            this.spendingAmt = spendingAmt;
        this.currencyCode = currencyCode;
        this.relatedPO = relatedPO;
    }



    public PurchaseOrder getRelatedPO() {
        return relatedPO;
    }

    public void setRelatedPO(PurchaseOrder relatedPO) {
        this.relatedPO = relatedPO;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

//    public String getPoId() {
//        return poId;
//    }
//
//    public void setPoId(String poId) {
//        this.poId = poId;
//    }

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

//    public ActualsTable getActuals() {
//        return actuals;
//    }
//
//    public void setActuals(ActualsTable actuals) {
//        this.actuals = actuals;
//    }
}
