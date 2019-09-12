package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(mappedBy = "merchandise")
//    private BJF bjf;

//    @ManyToMany
//    @JoinTable(name = "po_merchandise",
//            joinColumns = @JoinColumn(name = "merchandise_id"),
//            inverseJoinColumns = @JoinColumn(name = "po_id")
//    )
//    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "contract_merchandise",
            joinColumns = @JoinColumn(name = "merchandise_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    private List<Contract> contractList = new ArrayList<>();

    private Double price;

    private String currencyCode;

    public Merchandise() {
    }

    public Merchandise(String merchandiseName, String merchandiseCode, String hierachyPath, Double price, String currencyCode) {
        super(merchandiseName, merchandiseCode, hierachyPath);
        this.price = price;
        this.currencyCode = currencyCode;
    }

//    public BJF getBjf() {
//        return bjf;
//    }
//
//    public void setBjf(BJF bjf) {
//        this.bjf = bjf;
//    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
