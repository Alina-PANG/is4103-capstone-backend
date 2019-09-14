package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    public Employee findEmployeeByCode(String employeeCode);

}
