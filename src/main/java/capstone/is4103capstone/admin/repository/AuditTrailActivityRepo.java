package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.AuditTrailActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditTrailActivityRepo extends JpaRepository<AuditTrailActivity, String> {

    public List<AuditTrailActivity> findAuditTrailActivitiesByActivity(String activity);

    public List<AuditTrailActivity> findAuditTrailActivitiesByUserUuid(String userUuid);

}
