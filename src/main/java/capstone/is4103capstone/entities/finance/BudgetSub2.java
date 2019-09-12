package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BudgetSub2 extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub1_id")
    @JsonIgnore
    private BudgetSub1 budgetSub1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bjf_id")
    @JsonIgnore
    private BJF bjf;

    @OneToMany(mappedBy = "budgetSub2")
    private List<BudgetItem> budgetItems = new ArrayList<>();

    public BudgetSub2() {
    }

    public BudgetSub2(BudgetSub1 budgetSub1, List<BudgetItem> budgetItems) {
        this.budgetSub1 = budgetSub1;
        this.budgetItems = budgetItems;
    }

    public BudgetSub1 getBudgetSub1() {
        return budgetSub1;
    }

    public void setBudgetSub1(BudgetSub1 budgetSub1) {
        this.budgetSub1 = budgetSub1;
    }

    public List<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public void setBudgetItems(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    public BJF getBjf() {
        return bjf;
    }

    public void setBjf(BJF bjf) {
        this.bjf = bjf;
    }
}
