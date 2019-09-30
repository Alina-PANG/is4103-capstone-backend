package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, String> {
    @Override
    @Query(value = "SELECT * From seat_allocation s WHERE s.is_deleted=false AND s.id=?1", nativeQuery = true)
    Optional<SeatAllocation> findById(String allocationId);

    //Soft delete.
    @Query(value = "UPDATE seat_allocation s SET s.is_deleted=true WHERE s.id=?1", nativeQuery = true)
    @Modifying
    void softDelete(String id);
}
