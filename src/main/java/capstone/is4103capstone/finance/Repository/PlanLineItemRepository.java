package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.dashboard.model.dbModel.BudgetLineBGCCDBModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.BudgetLineDBModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.ReforecastLineGBCCMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanLineItemRepository extends JpaRepository<PlanLineItem, String> {


    @Query(value = "select l.* from plan_line_item l join plan p on l.in_plan=p.id " +
            "where p.for_year=?1 and p.cost_center_id=?3 and service_code=?2 " +
            "and p.budget_plan_status=3;",nativeQuery = true)
    List<PlanLineItem> findItemsByServiceAndCostCenter(int forYear, String serviceCode, String ccId);


    @Query(value = "select SUM(l.budget_amount_ingbp) as amount,  \n" +
            "l.service_code as service_code\n" +
            "from plan_line_item l join plan p join cost_center cc\n" +
            "on l.in_plan = p.id and cc.id=p.cost_center_id\n" +
            "where l.item_type=0 and l.is_deleted=0 and p.is_deleted=0 \n" +
            "and p.budget_plan_status = 3" +
            "and p.for_year = ?2 and cc.hierachy_path like CONCAT('%:',?1,'%')\n" +
            "group by l.service_code\n", nativeQuery = true)
    List<BudgetLineDBModel> findTotalBudgetByServiceAndCountry(String countryOrRegionCode, int year);
    //above: for budget only, not for reforecast


    @Query(value = "select l.budget_amount_ingbp as amount,  \n" +
            "l.service_code as service_code, cc.code as cc_code\n" +
            "from plan_line_item l join plan p join cost_center cc\n" +
            "on l.in_plan = p.id and cc.id=p.cost_center_id\n" +
            "where l.item_type=0 and l.is_deleted=0 and p.is_deleted=0 \n" +
            "and p.budget_plan_status = 3\n" +
            "and p.for_year = ?2 and cc.hierachy_path like CONCAT('%:',?1,'%')\n" +
            "group by l.service_code,cc.code\n", nativeQuery = true)
    List<BudgetLineBGCCDBModel> findTotalBudgetGroupByCountryAndService(String countryOrRegionCode, int year);

    @Query(value = "select l.budget_amount_ingbp as amount,  \n" +
            "l.service_code as service_code, p.for_month as lastest_reforecast_month,\n" +
            "cc.code as cc_code \n" +
            "from plan_line_item l join plan p join cost_center cc\n" +
            "on l.in_plan = p.id and cc.id=p.cost_center_id\n" +
            "where l.item_type=1 and l.is_deleted=0 and p.is_deleted=0 \n" +
            "and p.budget_plan_status = 3 \n" +
            "and p.for_year = ?2 and cc.hierachy_path like CONCAT('%:',?1,'%')\n" +
            "and p.for_month = (select MAX(p0.for_month) from plan p0 where p0.cost_center_id=p.cost_center_id)\n" +
            "group by cc.code, l.service_code\n",nativeQuery = true)
    List<ReforecastLineGBCCMonth> findLatestReforecastLinesGroupByCountryAndService(String countryOrRegionCode, int year);


}
