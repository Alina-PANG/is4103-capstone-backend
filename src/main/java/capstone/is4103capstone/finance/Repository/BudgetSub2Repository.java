package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetSub2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetSub2Repository extends JpaRepository<BudgetSub2,String> {
    public BudgetSub2 findBudgetSub2ByCode(String code);

    public List<BudgetSub2> getBudgetSub2sByBudgetSub1_Code(String sub1Code);

    @Query(value = "SELECT * FROM budget_sub2 s WHERE s.is_deleted=false and s.budget_sub1_id=?1", nativeQuery = true)
    public List<BudgetSub2> getBudgetSub2SByBudgetSub1Id(String sub1Id);

}
