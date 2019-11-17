package capstone.is4103capstone.finance.dashboard.model.dbModel;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;

public interface SpendingRecordAggreDBModel {
    @Value("#{target.service_name}")
    String getServiceName();
    @Value("#{target.service_code}")
    String getServiceCode();
    @Value("#{target.total_spending_per_serv}")
    BigDecimal getTotalSpendingPerService();
    @Value("#{target.country_name}")
    String getCountryName();
    @Value("#{target.country_code}")
    String getCountryCode();
    @Value("#{target.country_hp}")
    String getCountryHP();
}