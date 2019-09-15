package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, String> {

    @Override
    @Query(value = "SELECT * FROM Seat s WHERE s.isDeleted=false", nativeQuery = true)
    Optional<Seat> findById(String id);

    @Override
    @Query(value = "SELECT * FROM Seat s WHERE s.isDeleted=false", nativeQuery = true)
    public List<Seat> findAll();

    //Soft delete.
    @Query(value = "UPDATE Seat s SET s.isDeleted=true WHERE s.id=?1", nativeQuery = true)
    @Modifying
    public void softDelete(String id);
}
