package capstone.is4103capstone.finance.budget.model.req;

import capstone.is4103capstone.entities.finance.PlanLineItem;

import java.util.List;

public class CreateBudgetReq {
    private String username;
    private List<PlanLineItem> items;
    private Integer year;
    private boolean toSubmit;
    private boolean isBudget;

    public boolean isBudget() {
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
