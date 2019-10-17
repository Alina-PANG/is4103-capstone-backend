package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutsourcingAssessmentRepository extends JpaRepository<OutsourcingAssessment, String> {
    public List<OutsourcingAssessment> findOutsourcingAssessmentByCode(String Code);
    public OutsourcingAssessment findOutsourcingAssessmentByOutsourcingAssessmentStatusEnum(OutsourcingAssessmentStatusEnum status);
}
