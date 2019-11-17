package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanLineItem extends DBEntityTemplate {
    BigDecimal budgetAmount;
    BigDecimal budgetAmountInGBP;
    String currencyAbbr;
    String comment;
    String serviceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_plan")
    @JsonIgnore

    Plan planBelongsTo;

    private BudgetPlanEnum itemType;//budget or reforecast

    public PlanLineItem() {
    }

    public BigDecimal getBudgetAmountInGBP() {
        return budgetAmountInGBP;
    }

    public void setBudgetAmountInGBP(BigDecimal budgetAmountInGBP) {
        this.budgetAmountInGBP = budgetAmountInGBP;
    }

    public String getserviceCode() {
        return serviceCode;
    }

    public void setserviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public PlanLineItem(BigDecimal budgetAmount, String currencyAbbr, String comment, String serviceCode) {
        this.budgetAmount = budgetAmount;
        this.currencyAbbr = currencyAbbr;
        this.comment = comment;
        this.serviceCode = serviceCode;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getCurrencyAbbr() {
        return currencyAbbr;
    }

    public void setCurrencyAbbr(String currencyAbbr) {
        this.currencyAbbr = currencyAbbr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BudgetPlanEnum getItemType() {
        return itemType;
    }

    public void setItemType(BudgetPlanEnum itemType) {
        this.itemType = itemType;
    }


    public Plan getPlanBelongsTo() {
        return planBelongsTo;
    }

    public void setPlanBelongsTo(Plan planBelongsTo) {
        this.planBelongsTo = planBelongsTo;
    }
}
