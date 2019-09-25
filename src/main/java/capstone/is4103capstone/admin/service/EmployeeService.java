package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // ===== CRUD METHODS =====
    // === CREATE ===
    public Employee createNewEmployeeEntity(Employee input) {
        return employeeRepository.save(input);
    }

    public EmployeeDto createNewEmployee(EmployeeDto input) {
        return entityToDto(createNewEmployeeEntity(dtoToEntity(input)));
    }

    // === RETRIEVE ===
    public List<Employee> getAllEmployeeEntities() {
        return employeeRepository.findAll();
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : getAllEmployeeEntities()) {
            if (!employee.getDeleted()) employeeDtos.add(entityToDto(employee));
        }
        return employeeDtos;
    }

    public Employee getEmployeeEntityByUuid(String input) {
        try {
            return employeeRepository.getOne(input);
        } catch (EntityNotFoundException ex) {
            throw new DbObjectNotFoundException("Employee with UUID " + input + " not found!");
        }
    }

    public EmployeeDto getEmployeeByUuid(String input) {
        return entityToDto(getEmployeeEntityByUuid(input));
    }

    // === UPDATE ===
    @Transactional
    public Employee updateEmployeeEntity(Employee input) {
        Employee working = employeeRepository.getOne(input.getId());
        working.setFirstName(input.getFirstName());
        working.setMiddleName(input.getMiddleName());
        working.setLastName(input.getLastName());
        working.setUserName(input.getUserName());
        return working;
    }

    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto input) {
        return entityToDto(updateEmployeeEntity(dtoToEntity(input)));
    }

    // === DELETE ===
    @Transactional
    public boolean deleteEmployee(String uuid) {
        try {
            employeeRepository.getOne(uuid).setDeleted(true);
            return true;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Employee " + uuid + " not found!");
        }
    }

    // ===== ENTITY TO DTO CONVERTER METHODS =====
    public EmployeeDto entityToDto(Employee input) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(Optional.of(input.getId()));
        employeeDto.setCode(input.getCode());
        employeeDto.setFirstName(input.getFirstName());
        employeeDto.setMiddleName(Optional.of(input.getMiddleName()));
        employeeDto.setLastName(input.getLastName());
        employeeDto.setUserName(input.getUserName());
        employeeDto.setSecurityId(Optional.of(input.getSecurityId()));
        return employeeDto;
    }

    public Employee dtoToEntity(EmployeeDto input) {
        Employee employee = new Employee();
        input.getId().ifPresent(id -> employee.setId(id));
        employee.setFirstName(input.getFirstName());
        input.getMiddleName().ifPresent(middleName -> employee.setMiddleName(middleName));
        employee.setLastName(input.getLastName());
        employee.setUserName(input.getUserName());
        employee.setCode("EMP-" + input.getUserName());
        return employee;
    }

}
