package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetSub1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetSub1Repository extends JpaRepository<BudgetSub1,String> {
    BudgetSub1 findBudgetSub1ByCode(String code);
}
