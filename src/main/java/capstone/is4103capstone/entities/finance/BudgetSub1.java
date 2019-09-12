package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BudgetSub1 extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetCategory_id")
    @JsonIgnore
    private BudgetCategory budgetCategory;

    @OneToMany(mappedBy = "budgetSub1")
    private List<BudgetSub2> budgetSub2s = new ArrayList<>();


    public BudgetSub1() {
    }

    public BudgetSub1(BudgetCategory budgetCategory, List<BudgetSub2> budgetSub2s) {
        this.budgetCategory = budgetCategory;
        this.budgetSub2s = budgetSub2s;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public List<BudgetSub2> getBudgetSub2s() {
        return budgetSub2s;
    }

    public void setBudgetSub2s(List<BudgetSub2> budgetSub2s) {
        this.budgetSub2s = budgetSub2s;
    }
}
