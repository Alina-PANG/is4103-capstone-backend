package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanLineItemRepository extends JpaRepository<PlanLineItem, String> {


    @Query(value = "select l.* from plan_line_item l join plan p on l.in_plan=p.id " +
            "where p.for_year=?1 and p.cost_center_id=?3 and service_code=?2 " +
            "and p.budget_plan_status=3;",nativeQuery = true)
    List<PlanLineItem> findItemsByServiceAndCostCenter(int forYear, String serviceCode, String ccId);



}
