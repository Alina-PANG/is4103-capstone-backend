package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findEmployeeByObjectName(String username);

    Employee findEmployeeByUserName(String username);

    Employee findEmployeeByCode(String employeeCode);

    Employee findEmployeeBySecurityId(String sid);

    Employee findEmployeeById(String id);

    Optional<Employee> findUndeletedEmployeeById(String id);

}
