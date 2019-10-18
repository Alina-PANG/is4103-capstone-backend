package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFAggregateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BjfRepository extends JpaRepository<BJF,String> {

    public BJF getBJFByCode(String id);

    @Query(value = "SELECT * FROM bjf WHERE requester_id=?1 AND is_deleted=0",nativeQuery = true)
    public List<BJF> getAllByRequesterId(String id);

    @Query(value="select f.id,f.code,f.created_date_time,s.object_name as service, v.object_name as vendor, f.bjf_status as status, c.code as cost_center_code, f.bjf_type, concat(f.estimated_budget,' ',f.currency) as total_budget, e.user_name as requester" +
            " FROM bjf f join service s join vendor v join employee e join cost_center c" +
            " ON f.service_id=s.id and f.vendor_id=v.id and f.requester_id=e.id and c.id=f.cost_center_id and f.is_deleted=0 and f.requester_id=?1",nativeQuery = true)
    public List<BJFAggregateModel> getSimpleBJFsByRequester(String id);

    @Query(value="select f.id,f.code,f.created_date_time,s.object_name as service, v.object_name as vendor, f.bjf_status as status, c.code as cost_center_code, f.bjf_type, concat(f.estimated_budget,' ',f.currency) as total_budget, e.user_name as requester" +
            " FROM bjf f join service s join vendor v join employee e join cost_center c" +
            " ON f.service_id=s.id and f.vendor_id=v.id and f.requester_id=e.id and c.id=f.cost_center_id and f.is_deleted=0 and f.vendor_id=?1 and f.purchase_order_number is null",nativeQuery = true)
    public List<BJFAggregateModel> getBjfByVendorWithoutPurchaseOrder(String vendorId);

}
