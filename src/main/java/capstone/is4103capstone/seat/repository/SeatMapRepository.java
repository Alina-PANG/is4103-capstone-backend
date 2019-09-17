package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatMapRepository extends JpaRepository<SeatMap, String> {

    @Override
    @Query(value = "SELECT * FROM SeatMap s WHERE s.isDeleted=false", nativeQuery = true)
    Optional<SeatMap> findById(String id);

    @Override
    @Query(value = "SELECT * FROM SeatMap s WHERE s.isDeleted=false", nativeQuery = true)
    public List<SeatMap> findAll();

    //Soft delete.
    @Query(value = "UPDATE SeatMap s SET s.isDeleted=true WHERE s.id=?1", nativeQuery = true)
    @Modifying
    public void softDelete(String id);
}
