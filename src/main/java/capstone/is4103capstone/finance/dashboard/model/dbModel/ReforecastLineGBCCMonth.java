package capstone.is4103capstone.finance.dashboard.model.dbModel;


import org.springframework.beans.factory.annotation.Value;

public interface ReforecastLineGBCCMonth extends BudgetLineDBModel {
    /*
    select l.budget_amount as amount, l.currency_abbr as currency_code,
l.service_code as service_code, p.object_name as plan_name,MAX(p.for_month),
cc.code as cc_code
from plan_line_item l join plan p join cost_center cc
on l.in_plan = p.id and cc.id=p.cost_center_id
where l.item_type=1 and l.is_deleted=0 and p.is_deleted=0
and p.budget_plan_status = 3
and p.for_year = 2019 and cc.hierachy_path like '%:SG:%'
and p.for_month = (select MAX(p0.for_month) from plan p0 where p0.cost_center_id=p.cost_center_id)
group by cc.code, l.service_code
;
     */
    @Value("#{target.lastest_reforecast_month}")
    Integer getLatestReforecastMonth();

    @Value("#{target.cc_code}")
    String getCostCenterCode();
}
