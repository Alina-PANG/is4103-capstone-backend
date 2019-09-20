package capstone.is4103capstone.finance.admin.model.req;

public class CreateBudgetCategoryRequest {
    private String categoryName;
    private String countryCode;
    private String username;

    public CreateBudgetCategoryRequest() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
