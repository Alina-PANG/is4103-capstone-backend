package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.util.enums.BJFStatusEnum;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import org.springframework.beans.factory.annotation.Value;

public interface BJFAggregateModel {

    @Value("#{target.service}")
    String getServiceName();
    @Value("#{target.code}")
    String getBjfCode();
    @Value("#{target.vendor}")
    String getVendorName();
    @Value("#{target.total_budget}")
    String getTotalBudget();//currency + amt
    @Value("#{target.requester}")
    String getRequesterUsername();
    @Value("#{target.created_date_time}")
    String getCreatedDateTime();
    @Value("#{target.status}")
    BJFStatusEnum getBjfStatus();
    @Value("#{target.id}")
    String getId();
    @Value("#{target.cost_center_code}")
    String costCenterCode();
    @Value("#{target.bjf_type}")
    BjfTypeEnum getBjfType();
}
