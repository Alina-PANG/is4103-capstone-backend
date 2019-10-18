package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatAllocationRequestRepository extends JpaRepository<SeatAllocationRequest, String> {
    @Query(value = "SELECT * From seat_allocation_request s WHERE s.is_deleted=false AND s.id=?1", nativeQuery = true)
    Optional<SeatAllocationRequest> findUndeletedById(String allocationId);
}
