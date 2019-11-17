package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatementOfAccountLineItemRepository extends JpaRepository<StatementOfAcctLineItem, String>{
//    @Query(value = "SELECT * FROM statement_of_acct_line_item p WHERE p.is_deleted=false AND p.=?1",nativeQuery = true)
//    public List<PurchaseOrder> findAllByCreatedBy(String requestor);

    @Query(value = "select s.* from statement_of_acct_line_item s \n" +
            "join purchase_order po on po.id = s.purchase_order_id \n" +
            "where s.is_deleted=0 and s.last_modified_date_time<=?3 and s.last_modified_date_time >=?2 \n" +
            "and po.is_deleted=0 and po.country_id=?1 ",nativeQuery = true)
    public List<StatementOfAcctLineItem> findRecordsBetweenDatesInCountryLstMdf(String country, String startDate, String endDate);

    @Query(value = "select s.* from statement_of_acct_line_item s \n" +
            "join purchase_order po on po.id = s.purchase_order_id \n" +
            "where s.is_deleted=0 and s.schedule_date<=?3 and s.schedule_date >=?2 \n" +
            "and po.is_deleted=0 and po.country_id=?1 ",nativeQuery = true)
    public List<StatementOfAcctLineItem> findRecordsBetweenDatesInCountryScheduleDate(String country, String startDate, String endDate);




}
