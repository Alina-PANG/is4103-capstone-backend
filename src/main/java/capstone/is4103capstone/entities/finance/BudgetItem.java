package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BudgetItem extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub2_id")
    @JsonIgnore
    private BudgetSub2 budgetSub2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

    Double price;

    String currencyAbbr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetPlan_id")
    @JsonIgnore
    BudgetPlan budgetPlan;

    @OneToMany(mappedBy = "budgetItem")
    private List<BudgetLineItem> budgetLineItems = new ArrayList<>();

    public BudgetItem() {
    }

    public BudgetItem(BudgetSub2 budgetSub2, Vendor vendor, Double price, String currencyAbbr, Contract contract) {
        this.budgetSub2 = budgetSub2;
        this.vendor = vendor;
        this.price = price;
        this.currencyAbbr = currencyAbbr;
        this.contract = contract;
    }

    public BudgetSub2 getBudgetSub2() {
        return budgetSub2;
    }

    public void setBudgetSub2(BudgetSub2 budgetSub2) {
        this.budgetSub2 = budgetSub2;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
