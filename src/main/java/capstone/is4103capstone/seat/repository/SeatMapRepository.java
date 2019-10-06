package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatMapRepository extends JpaRepository<SeatMap, String> {

    @Query(value = "SELECT * FROM seat_map s WHERE s.id = ?1 AND s.is_deleted=false", nativeQuery = true)
    Optional<SeatMap> findUndeletedById(String id);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.office_id=?1 AND s.floor=?2", nativeQuery = true)
    List<SeatMap> findByOfficeIdAndFloor(String officeId, String floor);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false", nativeQuery = true)
    List<SeatMap> findAllUndeleted();

}
