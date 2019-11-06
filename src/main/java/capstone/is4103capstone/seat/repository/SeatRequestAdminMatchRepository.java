package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocationInactivationLog;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRequestAdminMatchRepository extends JpaRepository<SeatRequestAdminMatch, String> {

    @Query(value = "SELECT * FROM seat_request_admin_match s WHERE s.is_deleted=false AND s.hierarchy_id=?1", nativeQuery = true)
    Optional<SeatRequestAdminMatch> findUndeletedByHierarchyId(String hierarchyId);

    @Query(value = "SELECT * FROM seat_request_admin_match s WHERE s.is_deleted=false AND s.seat_admin_id=?1", nativeQuery = true)
    List<SeatRequestAdminMatch> findUndeletedOnesByAdminId(String adminId);

    @Query(value = "SELECT * FROM seat_request_admin_match s WHERE s.is_deleted=false AND s.hierarchy_id=?1 AND s.seat_admin_id=?2", nativeQuery = true)
    Optional<SeatRequestAdminMatch> findUndeletedOnesByEntityAndAdminId(String hierarchyId, String adminID);

    @Query(value = "SELECT * FROM seat_request_admin_match s WHERE s.is_deleted=false AND s.hierarchy_id=?1 AND s.seat_admin_id=?2 AND s.hierarchy_type=?3", nativeQuery = true)
    Optional<SeatRequestAdminMatch> findUndeletedOnesByEntityAndAdminAndHierarchyType(String hierarchyId, String adminID, int hierarchyType);
}
