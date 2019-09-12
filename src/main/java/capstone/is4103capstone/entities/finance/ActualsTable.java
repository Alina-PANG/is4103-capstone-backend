package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.CostCenter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ActualsTable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costCenter_id")
    @JsonIgnore
    private CostCenter costCenter;

    @OneToMany(mappedBy = "purchaseOrderLineItem")
    private List<PurchaseOrderLineItem> purchaseOrderLineItems= new ArrayList<>();

    public ActualsTable() {
    }

    public ActualsTable(CostCenter costCenter, List<PurchaseOrderLineItem> purchaseOrderLineItems) {
        this.costCenter = costCenter;
        this.purchaseOrderLineItems = purchaseOrderLineItems;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public List<PurchaseOrderLineItem> getPurchaseOrderLineItems() {
        return purchaseOrderLineItems;
    }

    public void setPurchaseOrderLineItems(List<PurchaseOrderLineItem> purchaseOrderLineItems) {
        this.purchaseOrderLineItems = purchaseOrderLineItems;
    }
}
