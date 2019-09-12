package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @Convert(converter = StringListConverter.class)
    private List<String> relatedBJF;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderLineItem> purchaseOrderLineItems = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder")
    private List<StatementOfAcctLineItem> statementOfAccounts= new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "purchaseOrders")
    private List<Merchandise> merchandises = new ArrayList<>();

    public PurchaseOrder() {
    }

    public PurchaseOrder(String poName, String poCode, String hierachyPath) {
        super(poName, poCode, hierachyPath);
    }

    public PurchaseOrder(Employee employee, BJF bjf, List<PurchaseOrderLineItem> purchaseOrderLineItems, List<Invoice> invoices, List<StatementOfAcctLineItem> statementOfAccounts, List<Merchandise> merchandises) {
        this.employee = employee;
        this.purchaseOrderLineItems = purchaseOrderLineItems;
        this.invoices = invoices;
        this.merchandises = merchandises;
    }


    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public List<StatementOfAcctLineItem> getStatementOfAccounts() {
        return statementOfAccounts;
    }

    public void setStatementOfAccounts(List<StatementOfAcctLineItem> statementOfAccounts) {
        this.statementOfAccounts = statementOfAccounts;
    }
}
