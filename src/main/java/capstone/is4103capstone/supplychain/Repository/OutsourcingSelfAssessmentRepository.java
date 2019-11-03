package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OutsourcingSelfAssessmentRepository extends JpaRepository<OutsourcingSelfAssessment, String> {
    @Query(value = "SELECT * FROM outsourcing_self_assessment o WHERE o.is_deleted=false AND o.outsourcing_id=?1",nativeQuery = true)
    List<OutsourcingSelfAssessment> findOutsourcingSelfAssessmentsByOutsourcingId(String outsourcingId);

    @Query(value = "SELECT COUNT(*) FROM outsourcing_self_assessment a WHERE a.is_deleted=false AND a.outsourcing_self_assessment_status=?1",nativeQuery = true)
    BigDecimal findNumberOfSelfAssessmentByStatus(String status);
}
