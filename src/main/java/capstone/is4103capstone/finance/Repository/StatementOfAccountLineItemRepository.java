package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatementOfAccountLineItemRepository extends JpaRepository<StatementOfAcctLineItem, String>{
//    @Query(value = "SELECT * FROM statement_of_acct_line_item p WHERE p.is_deleted=false AND p.=?1",nativeQuery = true)
//    public List<PurchaseOrder> findAllByCreatedBy(String requestor);
}
