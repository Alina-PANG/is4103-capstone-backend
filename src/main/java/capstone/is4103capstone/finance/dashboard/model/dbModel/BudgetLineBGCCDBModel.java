package capstone.is4103capstone.finance.dashboard.model.dbModel;

import org.springframework.beans.factory.annotation.Value;

public interface BudgetLineBGCCDBModel extends  BudgetLineDBModel {
    @Value("#{target.cc_code}")
    String getCostCenterCode();
}
