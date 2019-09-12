package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class PurchaseOrderLineItem extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrder_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "actualsTable_id")
//    @JsonIgnore
//    private ActualsTable actualsTable;

    public PurchaseOrderLineItem() {
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

//    public ActualsTable getActualsTable() {
//        return actualsTable;
//    }
//
//    public void setActualsTable(ActualsTable actualsTable) {
//        this.actualsTable = actualsTable;
//    }
}
