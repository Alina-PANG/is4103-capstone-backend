package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BJF extends DBEntityTemplate {
    @OneToMany(mappedBy = "bjf")
    private List<BudgetSub2> budgetSub2s = new ArrayList<>();

    @OneToMany(mappedBy = "bjf")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "bjf")
    private List<ApprovalForRequest> approvalForRequests = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    private String description;

    public BJF() {
    }

    public BJF(List<BudgetSub2> budgetSub2s, List<PurchaseOrder> purchaseOrders, List<ApprovalForRequest> approvalForRequests, Employee employee, String description) {
        this.budgetSub2s = budgetSub2s;
        this.purchaseOrders = purchaseOrders;
        this.approvalForRequests = approvalForRequests;
        this.employee = employee;
        this.description = description;
    }

    public List<BudgetSub2> getBudgetSub2s() {
        return budgetSub2s;
    }

    public void setBudgetSub2s(List<BudgetSub2> budgetSub2s) {
        this.budgetSub2s = budgetSub2s;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public List<ApprovalForRequest> getApprovalForRequests() {
        return approvalForRequests;
    }

    public void setApprovalForRequests(List<ApprovalForRequest> approvalForRequests) {
        this.approvalForRequests = approvalForRequests;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
