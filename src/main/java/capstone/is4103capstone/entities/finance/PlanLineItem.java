package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class PlanLineItem extends DBEntityTemplate {
    Double budgetAmount;
    String currencyAbbr;
    String comment;
    String merchandiseCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_plan")
    @JsonIgnore
    Plan planBelongsTo;

    private BudgetPlanEnum itemType;//budget or reforecast

    public PlanLineItem() {
    }


    public PlanLineItem(Double budgetAmount, String currencyAbbr, String comment, String merchandiseCode) {
        this.budgetAmount = budgetAmount;
        this.currencyAbbr = currencyAbbr;
        this.comment = comment;
        this.merchandiseCode = merchandiseCode;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
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

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
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
