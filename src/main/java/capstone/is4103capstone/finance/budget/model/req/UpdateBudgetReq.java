package capstone.is4103capstone.finance.budget.model.req;

import capstone.is4103capstone.entities.finance.PlanLineItem;

import java.util.List;

public class UpdateBudgetReq {
    private String username;
    private String id;
    private List<PlanLineItem> items;
    private Integer year;
    private boolean toSubmit;
    private String costCenterCode;
    private String description;

    public boolean isToSubmit() {
        return toSubmit;
    }

    public void setToSubmit(boolean toSubmit) {
        this.toSubmit = toSubmit;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlanLineItem> getItems() {
        return items;
    }

    public void setItems(List<PlanLineItem> items) {
        this.items = items;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public UpdateBudgetReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
