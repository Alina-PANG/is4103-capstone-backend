package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class BJF extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @JsonIgnore
    private Merchandise merchandise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    private BjfTypeEnum BjfType;

    private String justification;

    private String currencyCode;

    private Double ongoingCost;

    private Double totalAmt;

    private String costCenterCode;

    private Double projectCost = null; // if type == project

    private String projectCode = null; // if type == project



    @OneToMany(mappedBy = "bjf")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "bjf")
    private List<ApprovalForRequest> approvalForRequests = new ArrayList<>();



    public BJF() {
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

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
