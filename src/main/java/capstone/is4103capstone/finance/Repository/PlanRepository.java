package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.finance.budget.model.CompareLineItemModel;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.mockito.stubbing.ValidableAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,String> {

//    @Query(value = "SELECT * FROM Plan p WHERE p.cost_center_id = ?1")
    List<Plan> findByCostCenterId(String id);

    @Query(value = "SELECT * FROM plan WHERE is_deleted=0 AND id=?1",nativeQuery = true)
    Optional<Plan> findUndeletedPlanById(String id);


    //should be able to compare reforecast with reforecast, reforecast with budget
//    @Query(value = "",nativeQuery = true)
//    List<Plan> getCorrespondBudgetPlanByAnotherId(String id);

    @Query(value = "select * from plan where cost_center_id=?1 and for_year=?2 and is_deleted=0 and plan_type=?4 and budget_plan_status=?3",nativeQuery = true)
    Optional<Plan> findBudgetByCCAndYear(String ccId, int year, int status, int planType);


    @Query(value = "select m.code,m.object_name,i.budget_amount,i.currency_abbr,i.comment from plan_line_item i join service m on i.service_code=m.code where i.in_plan=?1"
            ,nativeQuery = true)
    List<CompareLineItemModel> findPlanLineItemsWithPlanId(String planId);


}
