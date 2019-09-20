package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetSub2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetSub2Repository extends JpaRepository<BudgetSub2,String> {
    public BudgetSub2 getBudgetSub2ByCode(String code);

    public List<BudgetSub2> getBudgetSub2sByBudgetSub1_Code(String sub1Code);

    public List<BudgetSub2> getBudgetSub2sByBudgetSub1_Id(String sub1Id);

}
