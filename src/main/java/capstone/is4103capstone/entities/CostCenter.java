package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.ActualsTable;
import capstone.is4103capstone.entities.finance.Plan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CostCenter extends DBEntityTemplate {

    @OneToMany(mappedBy = "defaultCostCenter")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "costCenter")
    private List<Plan> plans = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccmanager")
    @JsonIgnore
    private Employee costCenterManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id")
    private CompanyFunction function;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;


    @OneToMany(mappedBy = "costCenter",fetch = FetchType.EAGER)
    private List<ActualsTable> actuals = new ArrayList<>();

    public CostCenter() {
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<ActualsTable> getActuals() {
        return actuals;
    }

    public void setActuals(List<ActualsTable> actuals) {
        this.actuals = actuals;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getCostCenterManager() {
        return costCenterManager;
    }

    public void setCostCenterManager(Employee costCenterManager) {
        this.costCenterManager = costCenterManager;
    }
}
