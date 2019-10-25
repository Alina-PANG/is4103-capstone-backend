package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.util.enums.ContractStatusEnum;
import org.springframework.beans.factory.annotation.Value;

public interface ContractAndChildAggre {

    @Value("#{target.child_contract_id}")
    String getChildContractId();
    @Value("#{target.contract_id}")
    String getContractId();
    @Value("#{target.child_contract_code}")
    String getChildContractCode();
    @Value("#{target.contract_code}")
    String getContractCode();
    @Value("#{target.child_contract_value}")
    String getChildContractValue();
    @Value("#{target.total_contract_value}")
    String getTotalContractValue();
    @Value("#{target.contract_end_date}")
    String getContractEndDate();
    @Value("#{target.contract_start_date}")
    String getContractStartDate();
    @Value("#{target.contract_status}")
    ContractStatusEnum getContractStatus();
}
