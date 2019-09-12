package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Item extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetItem_id")
    @JsonIgnore
    private BudgetItem budgetItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    private Double price;

    private String currencyAbbr;

    public Item() {
    }

    public Item(BudgetItem budgetItem, Vendor vendor, PurchaseOrder purchaseOrder, Double price, String currencyAbbr) {
        this.budgetItem = budgetItem;
        this.vendor = vendor;
        this.purchaseOrder = purchaseOrder;
        this.price = price;
        this.currencyAbbr = currencyAbbr;
    }

    public BudgetItem getBudgetItem() {
        return budgetItem;
    }

    public void setBudgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyAbbr() {
        return currencyAbbr;
    }

    public void setCurrencyAbbr(String currencyAbbr) {
        this.currencyAbbr = currencyAbbr;
    }
}
