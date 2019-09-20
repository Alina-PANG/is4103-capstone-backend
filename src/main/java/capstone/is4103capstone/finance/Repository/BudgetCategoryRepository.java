package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetCategory;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,String> {

    @Query(value = "SELECT * FROM budget_category c WHERE c.is_deleted=false and c.country_id=?1", nativeQuery = true)
    List<BudgetCategory> findBudgetCategoriesByCountry_Id(String countryId);

    BudgetCategory findBudgetCategoryByCode(String code);
}
