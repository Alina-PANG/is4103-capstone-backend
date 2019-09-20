package capstone.is4103capstone.finance.admin.model.res;

import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.finance.admin.model.CategoryModel;
import capstone.is4103capstone.finance.admin.service.CategoryService;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class BudgetCategoryRes extends GeneralRes {
    CategoryModel category;

    public BudgetCategoryRes(String message, Boolean hasError, CategoryModel category) {
        super(message, hasError);
        this.category = category;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }
    //    public BudgetCategoryRes(String message, Boolean hasError) {
//        super(message, hasError);
//    }
}
