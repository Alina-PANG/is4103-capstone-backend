package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OutsourcingAssessmentRepository extends JpaRepository<OutsourcingAssessment, String> {
    public List<OutsourcingAssessment> findOutsourcingAssessmentByCode(String Code);

    @Query(value = "SELECT * FROM outsourcing_assessment p WHERE p.is_deleted=false AND p.outsourcing_assessment_status=0",nativeQuery = true)
    public OutsourcingAssessment findFormByStatus();

    @Query(value = "SELECT * FROM outsourcing_assessment p WHERE p.is_deleted=false AND p.id=?1",nativeQuery = true)
    Optional<OutsourcingAssessment> findUndeletedAssessmentFormById(String id);
}
