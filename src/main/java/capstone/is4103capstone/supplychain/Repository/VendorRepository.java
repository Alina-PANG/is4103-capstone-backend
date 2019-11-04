package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.dashboard.model.VendorAndContractDBModel;
import capstone.is4103capstone.finance.dashboard.model.VendorPurchaseAmountDBModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
select v.object_name as vendor_name, v.code as vendor_code, c.code as contract_code,
c.contract_status as contract_status, SUM(c.total_contract_value) as all_contract_value
from vendor v join contract c
on v.id=c.vendor_id
where c.contract_status = 3 or c.contract_status = 5 -- active contract
group by v.code
;
 */
public interface VendorRepository extends JpaRepository<Vendor, String> {
    public Vendor findVendorByCode(String vendorCode);

    @Query(value = "select v.object_name as vendor_name, v.code as vendor_code, c.code as contract_code,\n" +
            "c.contract_status as contract_status, SUM(c.total_contract_value) as all_contract_value \n" +
            "from vendor v join contract c \n" +
            "on v.id=c.vendor_id \n" +
            "where (c.contract_status = 3 or c.contract_status = 4) or (c.contract_status=5 and c.end_date >= ?1)\n" +
            "group by v.code",nativeQuery = true)
    public List<VendorAndContractDBModel> retrieveVendorAndItsTotalActiveContractValue(String thisYearFirstDay);
    // all vendor
    @Query(value = "SELECT v.object_name as vendor_name,v.code as vendor_code,\n" +
            "SUM(po.total_amount) as commited_sum, \n" +
            "SUM(l.actual_pmt) as actual_sum, SUM(l.paid_amt) as paid_sum \n" +
            "FROM vendor v join statement_of_acct_line_item l join purchase_order po \n" +
            "on l.purchase_order_id = po.id and v.id=po.purchase_order_vendor\n" +
            "where l.is_deleted=0 and po.is_deleted=0 and v.is_deleted=0 and l.schedule_date <=?1  and l.schedule_date >?2  \n" +
            "group by v.code;",
            nativeQuery = true)
    public List<VendorPurchaseAmountDBModel> retrieveVendorAndAllPurchaseByYearAndToday(String beforeDate, String thisYearStartDate);
}
