package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class BudgetLineItem extends DBEntityTemplate {
    Double budgetAmount;
    String currencyAbbr;
    String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetItem_id")
    @JsonIgnore
    BudgetItem budgetItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reforecastItem_id")
    @JsonIgnore
    ReforecastItem reforecastItem;

    public BudgetLineItem() {
    }


    public BudgetLineItem(Double budgetAmount, String currencyAbbr, String comment, BudgetItem budgetItem, ReforecastItem reforecastItem) {
        this.budgetAmount = budgetAmount;
        this.currencyAbbr = currencyAbbr;
        this.comment = comment;
        this.budgetItem = budgetItem;
        this.reforecastItem = reforecastItem;
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

    public BudgetItem getBudgetItem() {
        return budgetItem;
    }

    public void setBudgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
    }

    public ReforecastItem getReforecastItem() {
        return reforecastItem;
    }

    public void setReforecastItem(ReforecastItem reforecastItem) {
        this.reforecastItem = reforecastItem;
    }
}
