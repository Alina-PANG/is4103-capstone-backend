package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SeatUtilisationLogRepository extends JpaRepository<SeatUtilisationLog, String> {

    @Query(value = "SELECT * FROM seat_utilisation_log s WHERE s.level_entity_id=?1 AND " +
            "s.year=?2 AND s.month=?3 AND s.day_of_month=?4", nativeQuery = true)
    Optional<SeatUtilisationLog> findOneByBusinessEntityIdAndDate(String businessEntityId, Integer year, Integer month, Integer dayOfMonth);

    @Query(value = "SELECT * FROM seat_utilisation_log s WHERE s.created_time>?1 AND s.created_time<?2", nativeQuery = true)
    List<SeatUtilisationLog> findOnesByBusinessEntityIdDuringPeriod(String businessEntityId, Date startDate, Date endDate);

    @Query(value = "SELECT * FROM seat_utilisation_log s WHERE s.level_entity_id=?1 AND s.year=?2 AND " +
            "s.month=?3 AND s.day_of_month=?4 AND s.office_id=?5", nativeQuery = true)
    Optional<SeatUtilisationLog> findOneByBusinessEntityIdAndDateAndOffice(String businessEntityId, Integer year, Integer month,
                                                                           Integer dayOfMonth, String officeId);

    @Query(value = "SELECT * FROM seat_utilisation_log s WHERE s.level_entity_id=?1 AND s.created_time>?2 AND " +
            "s.created_time<?3 AND s.office_id=?4", nativeQuery = true)
    List<SeatUtilisationLog> findOnesByBusinessEntityIdDuringPeriodAndOffice(String businessEntityId, Date startDate, Date endDate, String officeId);
}
