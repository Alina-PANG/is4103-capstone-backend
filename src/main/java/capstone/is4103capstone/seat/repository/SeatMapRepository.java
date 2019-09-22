package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatMapRepository extends JpaRepository<SeatMap, String> {

    @Override
    @Query(value = "SELECT * FROM seat_map s WHERE s.id = ?1 AND s.is_deleted=false", nativeQuery = true)
    Optional<SeatMap> findById(String id);

    @Override
    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false", nativeQuery = true)
    List<SeatMap> findAll();

    //Soft delete.
    @Query(value = "UPDATE seat_map s SET s.is_deleted=true WHERE s.id=?1", nativeQuery = true)
    @Modifying
    void softDelete(String id);
}
