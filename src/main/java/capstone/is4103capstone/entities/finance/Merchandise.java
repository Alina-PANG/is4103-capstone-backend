package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class Merchandise extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub2_id")
    @JsonIgnore
    private BudgetSub2 budgetSub2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

//  We don't need to view purchase orders under merchandise right, no this use case.
    // only know from po who are the items, which shows in the purchaseOrderLineItem
//    @ManyToMany
//    @JoinTable(name = "po_merchandise",
//            joinColumns = @JoinColumn(name = "po_id"),
//            inverseJoinColumns = @JoinColumn(name = "merchandise_id")
//    )
//    private List<PurchaseOrder> purchaseOrders;

    private Double currentPrice;

    private String activeContractCode;

    private String currencyCode;

    //Contract Line Item

    public Merchandise() {
    }


    public Merchandise(String merchandiseName, String merchandiseCode, String hierachyPath) {
        super(merchandiseName, merchandiseCode, hierachyPath);
    }

    public String getActiveContractCode() {
        return activeContractCode;
    }

    public void setActiveContractCode(String activeContractCode) {
        this.activeContractCode = activeContractCode;
    }

    public BudgetSub2 getBudgetSub2() {
        return budgetSub2;
    }

    public void setBudgetSub2(BudgetSub2 budgetSub2) {
        this.budgetSub2 = budgetSub2;
    }

//    public List<PurchaseOrder> getPurchaseOrders() {
//        return purchaseOrders;
//    }
//
//    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
//        this.purchaseOrders = purchaseOrders;
//    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }


    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
