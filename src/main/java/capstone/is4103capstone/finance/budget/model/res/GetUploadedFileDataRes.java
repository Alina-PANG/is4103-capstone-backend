package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class GetUploadedFileDataRes extends GeneralRes {
    List<BudgetLineItemModel> budgetLineItemModelList;

    public GetUploadedFileDataRes() {
    }

    public GetUploadedFileDataRes(List<BudgetLineItemModel> budgetLineItemModelList) {
        this.budgetLineItemModelList = budgetLineItemModelList;
    }

    public GetUploadedFileDataRes(String message, Boolean hasError, List<BudgetLineItemModel> budgetLineItemModelList) {
        super(message, hasError);
        this.budgetLineItemModelList = budgetLineItemModelList;
    }

    public List<BudgetLineItemModel> getBudgetLineItemModelList() {
        return budgetLineItemModelList;
    }

    public void setBudgetLineItemModelList(List<BudgetLineItemModel> budgetLineItemModelList) {
        this.budgetLineItemModelList = budgetLineItemModelList;
    }
}
