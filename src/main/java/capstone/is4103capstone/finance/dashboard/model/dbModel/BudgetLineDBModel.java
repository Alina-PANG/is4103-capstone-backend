package capstone.is4103capstone.finance.dashboard.model.dbModel;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface BudgetLineDBModel {

    @Value("#{target.service_code}")
    String getServiceCode();
    @Value("#{target.amount}")
    BigDecimal getAmount();
}
