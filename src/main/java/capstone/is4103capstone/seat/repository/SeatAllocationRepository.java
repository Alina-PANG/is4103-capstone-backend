package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, String> {
}
