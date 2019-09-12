package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class Invoice extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    private String description;

    private Double paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

    public Invoice() {
    }

    public Invoice(String objectName, String code, String description, Double paymentAmount) {
        super(objectName, code);
        this.description = description;
        this.paymentAmount = paymentAmount;
    }

    public Invoice(PurchaseOrder purchaseOrder, String description, Double paymentAmount, Vendor vendor) {
        this.purchaseOrder = purchaseOrder;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.vendor = vendor;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }




    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
