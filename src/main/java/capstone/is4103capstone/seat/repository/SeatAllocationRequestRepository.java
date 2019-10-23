package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatAllocationRequestRepository extends JpaRepository<SeatAllocationRequest, String> {
    @Query(value = "SELECT * From seat_allocation_request s WHERE s.is_deleted=false AND s.id=?1", nativeQuery = true)
    Optional<SeatAllocationRequest> findUndeletedById(String allocationId);

    @Query(value = "SELECT * From seat_allocation_request s WHERE s.is_deleted=false AND s.requester_id=?1", nativeQuery = true)
    List<SeatAllocationRequest> findOnesByRequesterId(String requesterId);

    @Query(value = "SELECT * From seat_allocation_request s WHERE s.is_deleted=false AND EXISTS " +
            "(SELECT * FROM approval_for_request a WHERE a.requested_item_id=s.id AND a.approver_id=?1 AND a.is_deleted=false)", nativeQuery = true)
    List<SeatAllocationRequest> findOnesByReviewerId(String reviewerId);

    @Query(value = "SELECT * From seat_allocation_request s WHERE EXISTS " +
            "(SELECT * FROM seat_allocation_request a, approval_for_request b WHERE " +
            "s.id=a.id AND " +
            "b.requested_item_id=a.id AND " +
            "a.is_deleted=false AND " +
            "b.id=?1 AND b.is_deleted=false)", nativeQuery = true)
    Optional<SeatAllocationRequest> findUndeletedByTicketId(String ticketId);
}
