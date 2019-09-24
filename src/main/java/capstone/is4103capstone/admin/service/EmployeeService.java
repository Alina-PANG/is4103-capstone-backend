package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee retrieveEmployeeById(String employeeId) throws EmployeeNotFoundException {
        if (employeeId == null || employeeId.trim().length() == 0) {
            throw new EmployeeNotFoundException("Invalid employee ID given!");
        }
        employeeId = employeeId.trim();
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " does not exist!");
        }

        return optionalEmployee.get();
    }
}
