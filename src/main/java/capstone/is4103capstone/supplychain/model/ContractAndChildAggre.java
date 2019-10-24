package capstone.is4103capstone.supplychain.model;

import org.springframework.beans.factory.annotation.Value;

public interface ContractAndChildAggre {

    @Value("#{target.}")
    String getChildContractId();
    String getContractId();
    String getChildContractCode();
    String getContractCode();
    String getChildContractValue();
    String getTotalContractValue();
    String getContractEndDate();
    String getContractStartDate();

}
/*
select ch.id as child_contract_id, c.id as contract_id, ch.code as child_contract_code, c.code as contract_code,
ch.contract_value as child_contract_value, c.total_contract_value as total_contract_value,
c.end_date as contract_end_date, c.start_date as contract_start_date
 */