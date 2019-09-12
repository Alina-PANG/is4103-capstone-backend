package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BudgetPlan extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costCenter_id")
    @JsonIgnore
    CostCenter costCenter;

    BudgetPlanStatusEnum budgetPlanStatusEnum;

    Double version;

    public BudgetPlan() {
    }

    public BudgetPlan(CostCenter costCenter, BudgetPlanStatusEnum budgetPlanStatusEnum, Double version) {
        this.costCenter = costCenter;
        this.budgetPlanStatusEnum = budgetPlanStatusEnum;
        this.version = version;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public BudgetPlanStatusEnum getBudgetPlanStatusEnum() {
        return budgetPlanStatusEnum;
    }

    public void setBudgetPlanStatusEnum(BudgetPlanStatusEnum budgetPlanStatusEnum) {
        this.budgetPlanStatusEnum = budgetPlanStatusEnum;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
