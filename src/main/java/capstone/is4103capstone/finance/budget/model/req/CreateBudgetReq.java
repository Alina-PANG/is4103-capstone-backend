package capstone.is4103capstone.finance.budget.model.req;

import capstone.is4103capstone.entities.finance.PlanLineItem;

import java.io.Serializable;
import java.util.List;

public class CreateBudgetReq implements Serializable {
    private String username;
    private List<PlanLineItem> items;
    private Integer year;
    private Boolean toSubmit; // submit or draft
    private Boolean isBudget; // planType
    private String costCenterCode;
    private Integer month;

    private String description;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public Boolean getToSubmit() {
        return toSubmit;
    }

    public void setToSubmit(Boolean toSubmit) {
        this.toSubmit = toSubmit;
    }

    public Boolean getBudget() {
        return isBudget;
    }

    public void setBudget(Boolean budget) {
        isBudget = budget;
    }

    public Boolean getIsBudget() {
        return isBudget;
    }

    public void setIsBudget(Boolean budget) {
        isBudget = budget;
    }

    public Boolean isBudget() {
        return isBudget;
    }

    public void setBudget(boolean budget) {
        isBudget = budget;
    }

    public boolean isToSubmit() {
        return toSubmit;
    }

    public void setToSubmit(boolean toSubmit) {
        this.toSubmit = toSubmit;
    }

    public CreateBudgetReq() {
    }

    public List<PlanLineItem> getItems() {
        return items;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setItems(List<PlanLineItem> items) {
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
