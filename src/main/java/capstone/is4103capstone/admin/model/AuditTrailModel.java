package capstone.is4103capstone.admin.model;

import capstone.is4103capstone.util.enums.OperationTypeEnum;
import org.springframework.beans.factory.annotation.Value;

public interface AuditTrailModel {
    @Value("#{target.auditTrailId}")
    String getAuditTrailId();
    @Value("#{target.time_stamp}")
    String getTimeStamp();
    @Value("#{target.activity}")
    String getActivity();
    @Value("#{target.modified_object_type}")
    String getModifiedObjectType();
    @Value("#{target.modified_object_uuid}")
    String getModifiedObjectUuid();
    @Value("#{target.operation_type}")
    OperationTypeEnum getOperationType();
    @Value("#{target.employeeUuid}")
    String getEmployeeUuid();
    @Value("#{target.user_name}")
    String getEmployeeUsername();

}
