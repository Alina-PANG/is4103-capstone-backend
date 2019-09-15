package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

    public Employee findEmployeeByUserName(String username);

    public Employee findEmployeeByCode(String employeeCode);

}
