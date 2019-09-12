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
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetPlan_id")
    @JsonIgnore
    BudgetPlan budgetPlan;

    @OneToMany(mappedBy = "budgetItem")
    private List<Item> items= new ArrayList<>();

    @OneToMany(mappedBy = "budgetItem")
    private List<BudgetLineItem> budgetLineItems = new ArrayList<>();

    public BudgetItem() {
    }

    public BudgetItem(BudgetSub2 budgetSub2, Contract contract, BudgetPlan budgetPlan, List<Item> items, List<BudgetLineItem> budgetLineItems) {
        this.budgetSub2 = budgetSub2;
        this.contract = contract;
        this.budgetPlan = budgetPlan;
        this.items = items;
        this.budgetLineItems = budgetLineItems;
    }

    public BudgetSub2 getBudgetSub2() {
        return budgetSub2;
    }

    public void setBudgetSub2(BudgetSub2 budgetSub2) {
        this.budgetSub2 = budgetSub2;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public BudgetPlan getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BudgetPlan budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<BudgetLineItem> getBudgetLineItems() {
        return budgetLineItems;
    }

    public void setBudgetLineItems(List<BudgetLineItem> budgetLineItems) {
        this.budgetLineItems = budgetLineItems;
    }
}
