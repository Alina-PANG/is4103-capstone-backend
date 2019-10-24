package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutsourcingSelfAssessmentRepository extends JpaRepository<OutsourcingSelfAssessment, String> {
    @Query(value = "SELECT * FROM outsourcing_self_assessment o WHERE o.is_deleted=false AND o.outsourcing_id=?1",nativeQuery = true)
    List<OutsourcingSelfAssessment> findOutsourcingSelfAssessmentsByOutsourcingId(String outsourcingId);
}
