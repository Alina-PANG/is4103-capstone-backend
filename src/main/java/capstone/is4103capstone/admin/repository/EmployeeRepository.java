package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findEmployeeByObjectName(String username);

    Employee findEmployeeByUserName(String username);

    Employee findEmployeeByCode(String employeeCode);

    Employee findEmployeeBySecurityId(String sid);

    Employee findEmployeeById(String id);

    @Query(value = "SELECT * From employee e WHERE e.is_deleted=false AND e.id=?1", nativeQuery = true)
    Optional<Employee> findUndeletedEmployeeById(String id);

    @Query(value = "SELECT * From employee e WHERE e.is_deleted=false AND e.username=?1", nativeQuery = true)
    Optional<Employee> findUndeletedEmployeeByUsername(String username);

}
