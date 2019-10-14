package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,String> {
    @Query(value = "SELECT * FROM purchase_order p WHERE p.is_deleted=false AND p.status=?1",nativeQuery = true)
    public List<PurchaseOrder> findPurchaseOrderByStatus(int status);
}
