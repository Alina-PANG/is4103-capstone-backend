package capstone.is4103capstone.finance.dashboard.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface VendorAndContractDBModel {
    @Value("#{target.vendor_name}")
    String getVendorName();
    @Value("#{target.vendor_code}")
    String getVendorCode();
    @Value("#{target.contract_code}")
    String getContractCode();
    @Value("#{target.contract_status}")
    ContractStatusEnum getContractStatus();
    @Value("#{target.all_contract_value}")
    BigDecimal getActiveContractValueByVendor();
}
