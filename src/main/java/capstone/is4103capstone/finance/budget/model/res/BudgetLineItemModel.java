package capstone.is4103capstone.finance.budget.model.res;

import java.math.BigDecimal;

public class BudgetLineItemModel {
    private String id;
    private String categoryName;
    private String categoryCode;
    private String sub1Name;
    private String sub1Code;
    private String sub2Name;
    private String sub2Code;
    private String merchandiseName;
    private String merchandiseCode;
    private BigDecimal amount;
    private String currency;
    private String comment;

    public BudgetLineItemModel() {
    }

    public BudgetLineItemModel(String id, String categoryName, String categoryCode, String sub1Name, String sub1Code, String sub2Name, String sub2Code, String merchandiseName, String merchandiseCode, BigDecimal amount, String currency, String comment) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.sub1Name = sub1Name;
        this.sub1Code = sub1Code;
        this.sub2Name = sub2Name;
        this.sub2Code = sub2Code;
        this.merchandiseName = merchandiseName;
        this.merchandiseCode = merchandiseCode;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSub1Name() {
        return sub1Name;
    }

    public void setSub1Name(String sub1Name) {
        this.sub1Name = sub1Name;
    }

    public String getSub1Code() {
        return sub1Code;
    }

    public void setSub1Code(String sub1Code) {
        this.sub1Code = sub1Code;
    }

    public String getSub2Name() {
        return sub2Name;
    }

    public void setSub2Name(String sub2Name) {
        this.sub2Name = sub2Name;
    }

    public String getSub2Code() {
        return sub2Code;
    }

    public void setSub2Code(String sub2Code) {
        this.sub2Code = sub2Code;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}