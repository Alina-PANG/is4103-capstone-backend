package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Employee findEmployeeByObjectName(String username);
    Employee findEmployeeByUserName(String username);

    Employee findEmployeeByCode(String employeeCode);

    @Override
    @Query(value = "SELECT * FROM employee e WHERE e.id = ?1 AND e.is_deleted=false", nativeQuery = true)
    Optional<Employee> findById(String id);

    public Employee findEmployeeById(String id);
}
