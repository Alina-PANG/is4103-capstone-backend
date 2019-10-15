package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.finance.budget.model.CompareLineItemModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.math.BigDecimal;
import java.util.List;

public class PlanCompareRes extends GeneralRes {

    private List<CompareLineItemModel> insertion;
    private List<CompareLineItemModel> deletion;
    private List<CompareLineItemModel[]> change;

    private BigDecimal beforeTotal;
    private BigDecimal afterTotal;

    public PlanCompareRes() {
    }

    public PlanCompareRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public PlanCompareRes(String message, Boolean hasError, List<CompareLineItemModel> insertion, List<CompareLineItemModel> deletion, List<CompareLineItemModel[]> change, BigDecimal beforeTotal, BigDecimal afterTotal) {
        super(message, hasError);
        this.insertion = insertion;
        this.deletion = deletion;
        this.change = change;
        this.beforeTotal = beforeTotal;
        this.afterTotal = afterTotal;
    }

    public List<CompareLineItemModel> getInsertion() {
        return insertion;
    }

    public void setInsertion(List<CompareLineItemModel> insertion) {
        this.insertion = insertion;
    }

    public List<CompareLineItemModel> getDeletion() {
        return deletion;
    }

    public void setDeletion(List<CompareLineItemModel> deletion) {
        this.deletion = deletion;
    }

    public List<CompareLineItemModel[]> getChange() {
        return change;
    }

    public void setChange(List<CompareLineItemModel[]> change) {
        this.change = change;
    }

    public BigDecimal getBeforeTotal() {
        return beforeTotal;
    }

    public void setBeforeTotal(BigDecimal beforeTotal) {
        this.beforeTotal = beforeTotal;
    }

    public BigDecimal getAfterTotal() {
        return afterTotal;
    }

    public void setAfterTotal(BigDecimal afterTotal) {
        this.afterTotal = afterTotal;
    }
}
