package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.ActualsTable;
import capstone.is4103capstone.entities.finance.Plan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CostCenter extends DBEntityTemplate {

    @OneToMany(mappedBy = "defaultCostCenter",fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "costCenter",fetch = FetchType.LAZY)
    private List<Plan> plans = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccmanager")
//    @JsonIgnore
    private Employee costCenterManager;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "function_id")
//    private CompanyFunction function;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id")
//    private Country country;

    @OneToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @OneToMany(mappedBy = "costCenter",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ActualsTable> actuals = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bm_approver_id")
    private Employee bmApprover;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="function_approver_id")
    private Employee functionApprover;

    public CostCenter() {
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Employee getBmApprover() {
        return bmApprover;
    }

    public void setBmApprover(Employee bmApprover) {
        this.bmApprover = bmApprover;
    }

    public Employee getFunctionApprover() {
        return functionApprover;
    }

    public void setFunctionApprover(Employee functionApprover) {
        this.functionApprover = functionApprover;
    }

//    public CompanyFunction getFunction() {
//        return function;
//    }
//
//    public void setFunction(CompanyFunction function) {
//        this.function = function;
//    }
//
//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }

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
