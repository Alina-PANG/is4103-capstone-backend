package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetSub1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetSub1Repository extends JpaRepository<BudgetSub1,String> {
    BudgetSub1 findBudgetSub1ByCode(String code);

    @Query(value = "SELECT * FROM budget_sub1 s WHERE s.is_deleted=false and s.budget_category_id=?1", nativeQuery = true)
    List<BudgetSub1> findBudgetSub1sByBudgetCategoryId(String id);

    @Query(value = "SELECT COUNT(*) FROM budget_sub1 s WHERE s.is_deleted=false and s.budget_category_id=?1 and s.object_name=?2",nativeQuery = true)
    Integer countBudgetSub1NameByCategoryId(String catId,String newName);
}
