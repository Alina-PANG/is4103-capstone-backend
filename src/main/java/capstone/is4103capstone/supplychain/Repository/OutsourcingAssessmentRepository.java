package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.AssessmentFormSimpleModel;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutsourcingAssessmentRepository extends JpaRepository<OutsourcingAssessment, String> {
    public List<OutsourcingAssessment> findOutsourcingAssessmentByCode(String Code);

    @Query(value = "SELECT * FROM outsourcing_assessment p WHERE p.is_deleted=false AND p.outsourcing_assessment_status=0",nativeQuery = true)
    public OutsourcingAssessment findTemplate();

    @Query(value = "SELECT * FROM purchase_order p WHERE p.is_deleted=false AND p.approver=?1",nativeQuery = true)
    public List<OutsourcingAssessment> findAllByApprover(String approver);

    @Query(value = "SELECT * FROM purchase_order p WHERE p.is_deleted=false AND p.created_by=?1",nativeQuery = true)
    public List<OutsourcingAssessment> findAllByCreatedBy(String requestor);

    @Query(value = "select o.last_modified_date_time as last_modified_dt, o.id as form_id,o.code as form_code,b.code as bjf_code,b.id as bjf_id,o.created_by as requester, e2.user_name as bm_approver, " +
            "o.business_case_description as description,o.outsourcing_assessment_status as form_status,o.outsourcing as outsourcing_id " +
            "from bjf b join outsourcing_assessment o join employee e join employee e2 join approval_for_request a " +
            "where b.id=o.related_bjf_id and e.user_name=o.created_by and o.is_deleted=0 and a.requested_item_id=o.id and e2.id=a.approver_id",nativeQuery = true)
    public List<AssessmentFormSimpleModel> getAllAssessmentsForms();  // for outsourcing staff

    @Query(value = "select o.last_modified_date_time as last_modified_dt,o.id as form_id,o.code as form_code,b.code as bjf_code,b.id as bjf_id,o.created_by as requester, e2.user_name as bm_approver, " +
            "o.business_case_description as description,o.outsourcing_assessment_status as form_status,o.outsourcing as outsourcing_id  " +
            "from bjf b join outsourcing_assessment o join employee e join employee e2 join approval_for_request a " +
            "where b.id=o.related_bjf_id and e.user_name=o.created_by and o.is_deleted=0 and a.requested_item_id=o.id and e2.id=a.approver_id " +
            "and a.approver_id=?1",nativeQuery = true)
    public List<AssessmentFormSimpleModel> getAssessmentsUnderMyApproval(String approverId); //for direct managers;

}
