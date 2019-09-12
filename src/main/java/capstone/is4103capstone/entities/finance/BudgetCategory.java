package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BudgetCategory extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @OneToMany(mappedBy = "budgetCategory")
    private List<BudgetSub1> budgetSub1s = new ArrayList<>();

    public BudgetCategory() {
    }

    public BudgetCategory(Country country, List<BudgetSub1> budgetSub1s) {
        this.country = country;
        this.budgetSub1s = budgetSub1s;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<BudgetSub1> getBudgetSub1s() {
        return budgetSub1s;
    }

    public void setBudgetSub1s(List<BudgetSub1> budgetSub1s) {
        this.budgetSub1s = budgetSub1s;
    }
}
