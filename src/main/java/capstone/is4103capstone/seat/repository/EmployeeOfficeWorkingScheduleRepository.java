package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeOfficeWorkingScheduleRepository extends JpaRepository<EmployeeOfficeWorkingSchedule, String> {

    @Query(value = "SELECT * FROM employee_office_working_schedule e WHERE e.is_deleted=false AND e.employee_id=?1 AND e.office_id=?2", nativeQuery = true)
    List<EmployeeOfficeWorkingSchedule> findByEmployeeIdAndOfficeId(String employeeId, String officeId);
}
