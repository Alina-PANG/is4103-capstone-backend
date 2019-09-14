package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BudgetSub2 extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub1_id")
    @JsonIgnore
    private BudgetSub1 budgetSub1;

    @OneToMany(mappedBy = "budgetSub2")
    private List<Merchandise> merchandises = new ArrayList<>();

    public BudgetSub2() {
    }


    public BudgetSub2(String sub2CatName, String sub2CatCode, String hierachyPath) {
        super(sub2CatName, sub2CatCode, hierachyPath);
    }

    public BudgetSub2(BudgetSub1 budgetSub1, List<Merchandise> budgetItems) {
        this.budgetSub1 = budgetSub1;
        this.merchandises = budgetItems;
    }

    public BudgetSub1 getBudgetSub1() {
        return budgetSub1;
    }

    public void setBudgetSub1(BudgetSub1 budgetSub1) {
        this.budgetSub1 = budgetSub1;
    }

    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }



}
