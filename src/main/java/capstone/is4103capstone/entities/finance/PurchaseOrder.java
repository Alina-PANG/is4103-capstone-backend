package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseOrder extends DBEntityTemplate {
    @Convert(converter = StringListConverter.class)
    private List<String> relatedBJF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToMany(mappedBy = "purchaseOrder",fetch = FetchType.EAGER)
    private List<PurchaseOrderLineItem> purchaseOrderLineItems = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder")
    private List<StatementOfAcctLineItem> statementOfAccount = new ArrayList<>();

    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public PurchaseOrder() {
    }

    public PurchaseOrder(String poCode, String hierachyPath) {
        super(poCode, hierachyPath);
    }

    public PurchaseOrder(Employee employee, List<PurchaseOrderLineItem> purchaseOrderLineItems, List<Invoice> invoices, List<StatementOfAcctLineItem> statementOfAccounts, List<Merchandise> merchandises) {
        this.purchaseOrderLineItems = purchaseOrderLineItems;
        this.invoices = invoices;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public List<PurchaseOrderLineItem> getPurchaseOrderLineItems() {
        return purchaseOrderLineItems;
    }

    public void setPurchaseOrderLineItems(List<PurchaseOrderLineItem> purchaseOrderLineItems) {
        this.purchaseOrderLineItems = purchaseOrderLineItems;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<StatementOfAcctLineItem> getStatementOfAccount() {
        return statementOfAccount;
    }

    public void setStatementOfAccount(List<StatementOfAcctLineItem> statementOfAccount) {
        this.statementOfAccount = statementOfAccount;
    }

    public Double calculateTotalPrice(){
        if (this.purchaseOrderLineItems.size() == 0){
            return Double.valueOf(0.0);
        }
        Double total = 0d;
        for (PurchaseOrderLineItem lineItem: purchaseOrderLineItems){
            total += lineItem.getPrice().doubleValue() * lineItem.getQuantity();
        }
        return total;
    }
}
