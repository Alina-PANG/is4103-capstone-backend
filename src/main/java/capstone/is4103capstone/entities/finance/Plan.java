package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Plan extends DBEntityTemplate {
    @OneToMany(mappedBy = "planBelongsTo",fetch = FetchType.LAZY)
    private List<PlanLineItem> lineItems = new ArrayList<>();

    private Integer forYear;
    private Integer forMonth; // only for planType == REFORECAST;
    private BudgetPlanEnum planType; //BUDGET OR REFORECAST

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costCenter_id")
    @JsonIgnore
    CostCenter costCenter;

    BudgetPlanStatusEnum budgetPlanStatus;

    Integer version;

    private String planDescription;

    public Plan() {
    }


    public Plan(Integer forYear, Integer version) {
        this.forYear = forYear;
        this.version = version;
    }

    public Plan(List<PlanLineItem> budgetItems, CostCenter costCenter, BudgetPlanStatusEnum budgetPlanStatusEnum, Integer version) {
        this.lineItems = budgetItems;
        this.costCenter = costCenter;
        this.budgetPlanStatus = budgetPlanStatusEnum;
        this.version = version;
    }

    public Integer getForMonth() {
        return forMonth;
    }

    public void setForMonth(Integer forMonth) {
        this.forMonth = forMonth;
    }

    public List<PlanLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<PlanLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public BudgetPlanStatusEnum getBudgetPlanStatus() {
        return budgetPlanStatus;
    }

    public void setBudgetPlanStatus(BudgetPlanStatusEnum budgetPlanStatus) {
        this.budgetPlanStatus = budgetPlanStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getForYear() {
        return forYear;
    }

    public void setForYear(Integer forYear) {
        this.forYear = forYear;
    }

    public BudgetPlanEnum getPlanType() {
        return planType;
    }

    public void setPlanType(BudgetPlanEnum planType) {
        this.planType = planType;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public BigDecimal calculateTotalValue(){
        BigDecimal total = BigDecimal.ZERO;
        for (PlanLineItem item: getLineItems()){
            total = total.add(item.getBudgetAmount());
        }
        return total;
    }


}
