package capstone.is4103capstone.entities.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PurchaseOrderLineItem {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actualsTable_id")
    @JsonIgnore
    private ActualsTable actualsTable;

    public PurchaseOrderLineItem() {
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public ActualsTable getActualsTable() {
        return actualsTable;
    }

    public void setActualsTable(ActualsTable actualsTable) {
        this.actualsTable = actualsTable;
    }
}
