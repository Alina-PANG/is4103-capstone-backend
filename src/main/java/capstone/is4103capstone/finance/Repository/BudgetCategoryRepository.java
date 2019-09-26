package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,String> {

    @Query(value = "SELECT * FROM budget_category c WHERE c.is_deleted=false and c.country_id=?1", nativeQuery = true)
    List<BudgetCategory> findBudgetCategoriesByCountry_Id(String countryId);

    BudgetCategory findBudgetCategoryByCode(String code);

    @Query(value = "SELECT COUNT(*) FROM budget_category cat WHERE cat.is_deleted=false and cat.country_id=?1 and UPPER(cat.object_name)=UPPER(?2)",nativeQuery = true)
    Integer countCategoryNameByCountryId(String countryId,String newName);
}
