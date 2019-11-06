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
    List<SeatRequestAdminMatch> findUndeletedOnesByAdminId(String hierarchyId);
}
