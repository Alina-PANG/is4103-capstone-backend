package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,String> {
}
