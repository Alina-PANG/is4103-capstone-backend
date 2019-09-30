package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocationInactivationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SeatAllocationInactivationLogRepository extends JpaRepository<SeatAllocationInactivationLog, String> {
    @Query(value = "SELECT * FROM SeatAllocationInactivationLog s WHERE s.is_done = false AND s.inactivate_time <= ?1", nativeQuery = true)
    List<SeatAllocationInactivationLog> getLogsByCurrentTime(Date now);
}
