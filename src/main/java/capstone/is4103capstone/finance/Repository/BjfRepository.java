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

    @Query(value="select f.id,f.code,f.created_date_time,s.object_name as service, v.object_name as vendor, " +
            "f.bjf_status as bjf_status, c.code as cost_center_code, f.bjf_type, f.oa_id as assessment_form_id, " +
            "concat(f.estimated_budget,' ',f.currency) as total_budget, e1.user_name as approver, re.user_name as requester " +
            "FROM employee re join bjf f join service s join vendor v join employee e1 join cost_center c  " +
            "ON f.service_id=s.id and f.vendor_id=v.id and f.approver_id=e1.id and f.requester_id=re.id " +
            "and c.id=f.cost_center_id and f.is_deleted=0 and f.requester_id=?1",nativeQuery = true)
    public List<BJFAggregateModel> getSimpleBJFsByRequester(String id);

    @Query(value="select f.id,f.code,f.created_date_time,s.object_name as service, v.object_name as vendor, \n" +
            "f.bjf_status as bjf_status, c.code as cost_center_code, f.bjf_type, f.oa_id as assessment_form_id,\n" +
            "concat(f.estimated_budget,' ',f.currency) as total_budget, e1.user_name as approver, re.user_name as requester\n" +
            "FROM employee re join bjf f join service s join vendor v join employee e1 join cost_center c \n" +
            "ON f.service_id=s.id and f.vendor_id=v.id and f.approver_id=e1.id and f.requester_id=re.id\n" +
            "and c.id=f.cost_center_id and f.is_deleted=0 and f.approver_id=?1",nativeQuery = true)
    public List<BJFAggregateModel> getSimpleBJFsByApprover(String id);

    @Query(value="select f.id,f.code,f.created_date_time,s.object_name as service, v.object_name as vendor, f.oa_id as assessment_form_id, " +
            "f.bjf_status as bjf_status, c.code as cost_center_code, f.bjf_type, concat(f.estimated_budget,' ',f.currency) as total_budget, e2.user_name as approver,e1.user_name as requester" +
            " FROM bjf f join service s join vendor v join employee e1 join employee e2 join cost_center c" +
            " ON f.service_id=s.id and f.vendor_id=v.id and f.requester_id=e1.id and f.approver_id=e2.id and c.id=f.cost_center_id and f.is_deleted=0 and f.vendor_id=?1 and f.approval_status=1 and f.purchase_order_number is null",nativeQuery = true)
    public List<BJFAggregateModel> getBjfByVendorWithoutPurchaseOrder(String vendorId);

    public List<BJF> getBJFByVendorIdAndServiceId(String vendorId, String serviceId);

    public List<BJF> getBJFByServiceIdAndCostCenterId(String serviceId, String costCenterId);

}
