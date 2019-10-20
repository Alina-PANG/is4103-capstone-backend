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
    Optional<SeatMap> findByOfficeIdAndFloor(String officeId, String floor);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false", nativeQuery = true)
    List<SeatMap> findAllUndeleted();

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.id IN (" +
            "SELECT a.seatmap_id FROM seat a JOIN seat_allocation b ON a.id=b.seat_id WHERE " +
            "a.seatmap_id=s.id AND a.is_deleted=false AND " +
            "b.employee_id=?1 AND b.is_active=true AND b.is_deleted=false)", nativeQuery = true)
    List<SeatMap> findByActiveEmployeeSeatAllocation(String employeeId);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.id=?1 AND EXISTS (" +
            "SELECT * FROM seat a JOIN seat_allocation b ON a.id=b.seat_id WHERE " +
            "a.seatmap_id=s.id AND a.is_deleted=false AND " +
            "b.employee_id=?2 AND b.is_active=true AND b.is_deleted=false)", nativeQuery = true)
    Optional<SeatMap> findBySeatMapIdAndActiveEmployeeSeatAllocation(String seatMapId, String employeeId);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.id IN (" +
            "SELECT a.seatmap_id FROM seat a JOIN team b ON a.team_id=b.id WHERE " +
            "a.seatmap_id=s.id AND a.is_deleted=false AND " +
            "b.id=?1 AND b.is_deleted=false)", nativeQuery = true)
    List<SeatMap> findOnesWithSeatsAllocatedToTeam(String teamId);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.id IN (" +
            "SELECT a.seatmap_id FROM seat a JOIN business_unit b ON a.business_unit_id=b.id WHERE " +
            "a.seatmap_id=s.id AND a.is_deleted=false AND " +
            "b.id=?1 AND b.is_deleted=false)", nativeQuery = true)
    List<SeatMap> findOnesWithSeatsAllocatedToBusinessUnit(String businessUnitId);

    @Query(value = "SELECT * FROM seat_map s WHERE s.is_deleted=false AND s.id IN (" +
            "SELECT a.seatmap_id FROM seat a JOIN company_function b ON a.function_id=b.id WHERE " +
            "a.seatmap_id=s.id AND a.is_deleted=false AND " +
            "b.id=?1 AND b.is_deleted=false)", nativeQuery = true)
    List<SeatMap> findOnesWithSeatsAllocatedToFunction(String functionId);

}
