package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "costcenter")
public class CostCenter extends DBEntityTemplate {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter_employees")//uni-directional
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter_manager")
    @JsonIgnore
    private Employee costCenterManager;

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
