package capstone.is4103capstone.finance.dashboard.model;


import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface VendorPurchaseAmountDBModel {
    @Value("#{target.vendor_name}")
    String getVendorName();
    @Value("#{target.vendor_code}")
    String getVendorCode();
    @Value("#{target.commited_sum}")
    BigDecimal getTotalCommitted();
    @Value("#{target.actual_sum}")
    BigDecimal getTotalActual();
    @Value("#{target.paid_sum}")
    BigDecimal getTotalPaid(); // need to manuall calculate the accurals.

}
