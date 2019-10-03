package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocationInactivationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SeatAllocationInactivationLogRepository extends JpaRepository<SeatAllocationInactivationLog, String> {
    @Query(value = "SELECT * FROM seat_allocation_inactivation_log s WHERE s.is_done = false AND s.is_cancelled=false " +
            "AND s.inactivate_time <= ?1", nativeQuery = true)
    List<SeatAllocationInactivationLog> getLogsByCurrentTime(Date now);

    @Query(value = "SELECT * FROM seat_allocation_inactivation_log s WHERE s.is_done = false AND s.is_cancelled=false", nativeQuery = true)
    List<SeatAllocationInactivationLog> getUndoneLogs();

    @Query(value = "SELECT * FROM seat_allocation_inactivation_log s WHERE s.allocation_id=?1 AND s.is_cancelled=false", nativeQuery = true)
    Optional<SeatAllocationInactivationLog> getUncancelledById(String id);
}
