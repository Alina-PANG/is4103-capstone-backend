package capstone.is4103capstone.finance.budget.model;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface CompareLineItemModel {

    @Value("#{target.code}")
    String getServiceCode();
    @Value("#{target.service_name}")
    String getServiceName();
    @Value("#{target.budget_amount}")
    BigDecimal getAmount();
    @Value("#{target.currency_abbr}")
    String getCurrency();
    @Value("#{target.comment}")
    String getComment();
    @Value("#{target.plan_name}")
    String getPlanName();
}
