package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.finance.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,String> {
    @Query(value = "SELECT * FROM Plan p WHERE p.username=?1 AND p.planType=?2 AND p.objectName=?3 AND p.isDeleted=false AND p.budgetPlanStatus=DRAFT", nativeQuery = true)
    Plan findMostRecentPlanDraft(String username, String type, String name);


}
