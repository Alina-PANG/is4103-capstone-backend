package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BudgetSub2 extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub1_id")
    @JsonIgnore
    private BudgetSub1 budgetSub1;

    @OneToMany(mappedBy = "budgetSub2")
    private List<Service> services = new ArrayList<>();

    public BudgetSub2() {
    }


    public BudgetSub2(String sub2CatName) {
        super(sub2CatName);
    }


    public BudgetSub1 getBudgetSub1() {
        return budgetSub1;
    }

    public void setBudgetSub1(BudgetSub1 budgetSub1) {
        this.budgetSub1 = budgetSub1;
    }

    public List<Service> getservices() {
        return services;
    }

    public void setservices(List<Service> services) {
        this.services = services;
    }



}
