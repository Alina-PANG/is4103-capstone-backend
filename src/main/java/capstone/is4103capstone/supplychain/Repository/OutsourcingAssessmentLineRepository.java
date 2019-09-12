package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutsourcingAssessmentLineRepository extends JpaRepository<OutsourcingAssessmentLine, String> {
    public List<OutsourcingAssessmentLine> findOutsourcingAssessmentLineByCode(String Code);
}
