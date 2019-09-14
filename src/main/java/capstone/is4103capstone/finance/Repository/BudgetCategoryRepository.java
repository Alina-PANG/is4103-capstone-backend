package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,String> {
    List<BudgetCategory> findBudgetCategoriesByCountry_Id(String countryId);
}
