package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, String> {

    @Query(value = "SELECT * From seat_allocation s WHERE s.is_deleted=false AND s.id=?1", nativeQuery = true)
    Optional<SeatAllocation> findUndeletedById(String allocationId);

    @Query(value = "SELECT * From seat_allocation s WHERE s.is_deleted=false AND s.employee_id=?1 AND s.is_active=true", nativeQuery = true)
    List<SeatAllocation> findActiveOnesByEmployeeId(String employeeId);

}
