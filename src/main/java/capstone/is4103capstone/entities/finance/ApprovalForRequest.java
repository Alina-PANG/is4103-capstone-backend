package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ApprovalForRequest extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bjf_id")
    @JsonIgnore
    private BJF bjf;

    private BudgetPlanStatusEnum budgetPlanStatusEnum;

    public ApprovalForRequest() {
    }

    public ApprovalForRequest(Employee employee, BJF bjf, BudgetPlanStatusEnum budgetPlanStatusEnum) {
        this.employee = employee;
        this.bjf = bjf;
        this.budgetPlanStatusEnum = budgetPlanStatusEnum;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BJF getBjf() {
        return bjf;
    }

    public void setBjf(BJF bjf) {
        this.bjf = bjf;
    }

    public BudgetPlanStatusEnum getBudgetPlanStatusEnum() {
        return budgetPlanStatusEnum;
    }

    public void setBudgetPlanStatusEnum(BudgetPlanStatusEnum budgetPlanStatusEnum) {
        this.budgetPlanStatusEnum = budgetPlanStatusEnum;
    }
}

