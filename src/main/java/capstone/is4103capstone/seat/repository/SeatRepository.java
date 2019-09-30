package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, String> {

    @Override
    @Query(value = "SELECT * FROM Seat s WHERE s.id = ?1 AND s.is_deleted=false", nativeQuery = true)
    Optional<Seat> findById(String id);

    @Override
    @Query(value = "SELECT * FROM Seat s WHERE s.is_deleted=false", nativeQuery = true)
    List<Seat> findAll();


    @Query(value = "SELECT * FROM Seat s WHERE s.is_deleted=false AND s.id EXISTS (SELECT a.seat_id FROM " +
            "SeatAllocation a WHERE a.seat_id=s.id AND a.is_deleted=false AND a.id=?1 AND a.is_active=true", nativeQuery = true)
    Optional<Seat> findByActiveSeatAllocationId(String allocationId);

    //Soft delete.
    @Query(value = "UPDATE Seat s SET s.is_deleted=true WHERE s.id=?1", nativeQuery = true)
    @Modifying
    void softDelete(String id);
}
