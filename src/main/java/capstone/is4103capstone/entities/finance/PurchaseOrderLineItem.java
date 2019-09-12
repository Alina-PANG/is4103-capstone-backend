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

    private String merchandiseCode;
    private Integer quantity;
    private Double price;

    public PurchaseOrderLineItem() {
    }

    public PurchaseOrderLineItem(PurchaseOrder purchaseOrder, String merchandiseCode, Integer quantity, Double price) {
        this.purchaseOrder = purchaseOrder;
        this.merchandiseCode = merchandiseCode;
        this.quantity = quantity;
        this.price = price;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
