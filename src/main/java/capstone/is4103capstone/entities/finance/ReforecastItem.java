package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ReforecastItem extends DBEntityTemplate {
    Double price;

    String currencyAbbr;

    @OneToMany(mappedBy = "budgetItem")
    private List<BudgetLineItem> budgetLineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reforecastPlan_id")
    @JsonIgnore
    ReforecastPlan reforecastPlan;

    public ReforecastItem() {
    }

    public ReforecastItem(Double price, String currencyAbbr, List<BudgetLineItem> budgetLineItems, ReforecastPlan reforecastPlan) {
        this.price = price;
        this.currencyAbbr = currencyAbbr;
        this.budgetLineItems = budgetLineItems;
        this.reforecastPlan = reforecastPlan;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyAbbr() {
        return currencyAbbr;
    }

    public void setCurrencyAbbr(String currencyAbbr) {
        this.currencyAbbr = currencyAbbr;
    }

    public List<BudgetLineItem> getBudgetLineItems() {
        return budgetLineItems;
    }

    public void setBudgetLineItems(List<BudgetLineItem> budgetLineItems) {
        this.budgetLineItems = budgetLineItems;
    }

    public ReforecastPlan getReforecastPlan() {
        return reforecastPlan;
    }

    public void setReforecastPlan(ReforecastPlan reforecastPlan) {
        this.reforecastPlan = reforecastPlan;
    }
}
