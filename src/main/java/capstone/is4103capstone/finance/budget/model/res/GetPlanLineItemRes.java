package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetPlanLineItemRes extends GeneralRes {
    List<PlanLineItem> items;

    public GetPlanLineItemRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public GetPlanLineItemRes(String message, Boolean hasError, List<PlanLineItem> items) {
        super(message, hasError);
        this.items = items;
    }

    public GetPlanLineItemRes() {
    }

    public List<PlanLineItem> getItems() {
        return items;
    }

    public void setItems(List<PlanLineItem> items) {
        this.items = items;
    }
}
