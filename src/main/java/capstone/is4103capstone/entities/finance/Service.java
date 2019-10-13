package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Service extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budgetSub2_id")
    @JsonIgnore
    private BudgetSub2 budgetSub2;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "service_vendor",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id"))
    @JsonIgnore
    private List<Vendor> vendor;

    private String measureUnit;

//  We don't need to view purchase orders under service right, no this use case.
    // only know from po who are the items, which shows in the purchaseOrderLineItem
//    @ManyToMany
//    @JoinTable(name = "po_service",
//            joinColumns = @JoinColumn(name = "po_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id")
//    )
//    private List<PurchaseOrder> purchaseOrders;

    private BigDecimal referencePrice;
    private String currencyCode;

    public Service() {
    }

    public Service(String objectName, String measureUnit, BigDecimal referencePrice, String currencyCode) {
        super(objectName);
        this.measureUnit = measureUnit;
        this.referencePrice = referencePrice;
        this.currencyCode = currencyCode;
    }

    public Service(String objectName, BudgetSub2 budgetSub2, List<Vendor> vendor, String measureUnit, BigDecimal referencePrice, String currencyCode) {
        super(objectName);
        this.budgetSub2 = budgetSub2;
        this.vendor = vendor;
        this.measureUnit = measureUnit;
        this.referencePrice = referencePrice;
        this.currencyCode = currencyCode;
    }

    public Service(String objectName, String code, String hierachyPath, String measureUnit, String opsUser) {
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

    public Service(String serviceName, String serviceCode, String hierachyPath) {
        super(serviceName, serviceCode, hierachyPath);
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


    public List<Vendor> getVendor() {
        return vendor;
    }

    public void setVendor(List<Vendor> vendor) {
        this.vendor = vendor;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
