package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutsourcingAssessmentSectionRepository extends JpaRepository<OutsourcingAssessmentSection, String> {
    public List<OutsourcingAssessmentSection> findOutsourcingAssessmentSectionByCode(String Code);
}
