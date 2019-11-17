package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, String> {

    @Query(value = "SELECT * FROM seat s WHERE s.id = ?1 AND s.is_deleted=false", nativeQuery = true)
    Optional<Seat> findUndeletedById(String id);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false", nativeQuery = true)
    List<Seat> findAllUndeleted();

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.id IN (SELECT a.seat_id FROM seat_allocation a WHERE a.seat_id=s.id AND a.is_deleted=false AND a.id=?1 AND a.is_active=true)", nativeQuery = true)
    Optional<Seat> findByActiveSeatAllocationId(String allocationId);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.team_id IS NOT NULL AND s.team_id=?1", nativeQuery = true)
    List<Seat> findOnesByTeamId(String teamId);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.business_unit_id IS NOT NULL AND s.business_unit_id=?1", nativeQuery = true)
    List<Seat> findOnesByBusinessUnitId(String businessUnitId);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.function_id IS NOT NULL AND s.function_id=?1", nativeQuery = true)
    List<Seat> findOnesByCompanyFunctionId(String functionId);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.in IN " +
            "(SELECT a.id FROM seat a, seat_map b WHERE " +
            "a.seat_map_id=b.id AND " +
            "a.is_deleted=false AND b.is_deleted=false AND " +
            "b.office_id=?1)", nativeQuery = true)
    List<Seat> findOnesByOfficeId(String office);

    @Query(value = "SELECT * FROM seat s WHERE s.is_deleted=false AND s.seatmap_id=?1 AND s.under_office=true", nativeQuery = true)
    List<Seat> findOfficeOnesBySeatMapId(String seatMapId);
}
