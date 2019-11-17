package capstone.is4103capstone.finance.dashboard.model.dbModel;


import org.springframework.beans.factory.annotation.Value;

public interface ReforecastLineGBCCMonth extends BudgetLineDBModel {
    @Value("#{target.lastest_reforecast_month}")
    Integer getLatestReforecastMonth();

    @Value("#{target.cc_code}")
    String getCostCenterCode();
}
