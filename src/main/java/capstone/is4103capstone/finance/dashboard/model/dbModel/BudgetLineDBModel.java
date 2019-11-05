package capstone.is4103capstone.finance.dashboard.model.dbModel;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface BudgetLineDBModel {

    /*

select SUM(l.budget_amount) as total_budget_per_service, l.currency_abbr,
l.service_code, cc.hierachy_path as cc_hp
from plan_line_item l join plan p join cost_center cc

on l.in_plan = p.id and cc.id=p.cost_center_id

where l.item_type=0 and l.is_deleted=0 and p.is_deleted=0
and p.budget_plan_status = 3 -- appproved plans
and p.for_year = 2019 and cc.hierachy_path like '%SG%'
group by l.service_code
     */
    @Value("#{target.currency_code}")
    String getCurrency();
    @Value("#{target.service_code}")
    String getServiceCode();
    @Value("#{target.amount}")
    BigDecimal getAmount(); // total budget per service
}
