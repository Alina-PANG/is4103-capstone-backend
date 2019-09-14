package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.ContractLine;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    @NotNull
    private String measureUnit;

//  We don't need to view purchase orders under merchandise right, no this use case.
    // only know from po who are the items, which shows in the purchaseOrderLineItem
//    @ManyToMany
//    @JoinTable(name = "po_merchandise",
//            joinColumns = @JoinColumn(name = "po_id"),
//            inverseJoinColumns = @JoinColumn(name = "merchandise_id")
//    )
//    private List<PurchaseOrder> purchaseOrders;

    private BigDecimal currentPrice;

    @OneToOne(mappedBy = "merchandise")
    private ContractLine currentContract;

    private String currencyCode;


    public Merchandise() {
    }

    public ContractLine getCurrentContract() {
        return currentContract;
    }

    public void setCurrentContract(ContractLine currentContract) {
        this.currentContract = currentContract;
    }

    public Merchandise(String objectName, String code,String hierachyPath, String measureUnit, String opsUser) {
        super(objectName, code,hierachyPath);
        this.setCreatedBy(opsUser);
        this.setLastModifiedBy(opsUser);
        this.measureUnit = measureUnit;

    }


    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Merchandise(String merchandiseName, String merchandiseCode, String hierachyPath) {
        super(merchandiseName, merchandiseCode, hierachyPath);
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


    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
