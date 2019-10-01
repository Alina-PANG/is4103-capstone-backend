package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;

import java.util.List;

public class BudgetModel {
    private List<BudgetLineItemModel> items;
    private Integer forYear;
    private Integer forMonth;
    private String name;
    private String id;
    private BudgetPlanStatusEnum budgetPlanStatus;
    private BudgetPlanEnum planType;
    private String createBy;
    private String lastModifiedTime;
    private String costCenterCode;
    private String teamCode;
    private String countryCode;
    private String functionCode;
    private String description;


    public BudgetModel() {
    }

    public BudgetModel(Integer forYear, Integer forMonth, String name, String id, BudgetPlanStatusEnum budgetPlanStatus, BudgetPlanEnum planType) {
        this.forYear = forYear;
        this.forMonth = forMonth;
        this.name = name;
        this.id = id;
        this.budgetPlanStatus = budgetPlanStatus;
        this.planType = planType;
    }

    public BudgetModel(Integer forYear, Integer forMonth, String name, String id, BudgetPlanStatusEnum budgetPlanStatus, BudgetPlanEnum planType, String createBy, String lastModifiedTime) {
        this.forYear = forYear;
        this.forMonth = forMonth;
        this.name = name;
        this.id = id;
        this.budgetPlanStatus = budgetPlanStatus;
        this.planType = planType;
        this.createBy = createBy;
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public BudgetPlanEnum getPlanType() {
        return planType;
    }

    public void setPlanType(BudgetPlanEnum planType) {
        this.planType = planType;
    }

    public List<BudgetLineItemModel> getItems() {
        return items;
    }

    public void setItems(List<BudgetLineItemModel> items) {
        this.items = items;
    }

    public Integer getForYear() {
        return forYear;
    }

    public void setForYear(Integer forYear) {
        this.forYear = forYear;
    }

    public Integer getForMonth() {
        return forMonth;
    }

    public void setForMonth(Integer forMonth) {
        this.forMonth = forMonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BudgetPlanStatusEnum getBudgetPlanStatus() {
        return budgetPlanStatus;
    }

    public void setBudgetPlanStatus(BudgetPlanStatusEnum budgetPlanStatus) {
        this.budgetPlanStatus = budgetPlanStatus;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

//    public BudgetModel(Integer forYear, Integer forMonth, String name, String id, BudgetPlanStatusEnum budgetPlanStatus, BudgetPlanEnum planType, String createBy, String lastModifiedTime, String costCenterCode, String teamCode, String countryCode, String functionCode, String description) {
//        this.forYear = forYear;
//        this.forMonth = forMonth;
//        this.name = name;
//        this.id = id;
//        this.budgetPlanStatus = budgetPlanStatus;
//        this.planType = planType;
//        this.createBy = createBy;
//        this.lastModifiedTime = lastModifiedTime;
//        this.costCenterCode = costCenterCode;
//        this.teamCode = teamCode;
//        this.countryCode = countryCode;
//        this.functionCode = functionCode;
//        this.description = description;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
