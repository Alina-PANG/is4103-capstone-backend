package capstone.is4103capstone.admin.model;

import org.springframework.beans.factory.annotation.Value;

public interface AuditTrailModel {
    @Value("#{target.user_name}")
    String getUsername();
    @Value("#{target.user_uuid}")
    String getUserUuid();
    @Value("#{target.activity}")
    String getActivity();
    @Value("#{target.time_stamp}")
    String getTimeStamp();
}
