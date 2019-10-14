package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.PurchaseOrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineItemRepository extends JpaRepository<PurchaseOrderLineItem,String> {
}
