package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.admin.model.AuditTrailModel;
import capstone.is4103capstone.entities.AuditTrailActivity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditTrailActivityRepo extends JpaRepository<AuditTrailActivity, String> {
    List<AuditTrailActivity> findAuditTrailActivitiesByActivity(String activity);
    List<AuditTrailActivity> findAuditTrailActivitiesByUserUuid(String userUuid);

    @Query(value = "select a.activity,a.time_stamp,a.user_uuid,e.user_name " +
            "from audit_trail_activity a join employee e on a.user_uuid=e.id"
            ,nativeQuery = true)
    List<AuditTrailModel> findAuditTrailActivityWithUsername();

    @Query(nativeQuery = true,
            value = "SELECT a.id AS auditTrailId, a.time_stamp, a.activity, a.modified_object_type, a.modified_object_uuid, a.operation_type, e.id AS employeeUuid, e.user_name " +
            "FROM audit_trail_activity a " +
            "JOIN employee e " +
            "ON a.user_uuid = e.id")
    List<AuditTrailModel> findAuditTrailActivityWithUsernamePageable(Pageable pageable);
}
