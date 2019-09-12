package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.supplyChain.Contract;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class PurchaseOrderLineItem {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    private Merchandise merchandise;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contractReferredTo;

    public PurchaseOrderLineItem() {
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }


    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Contract getContractReferredTo() {
        return contractReferredTo;
    }

    public void setContractReferredTo(Contract contractReferredTo) {
        this.contractReferredTo = contractReferredTo;
    }


}
