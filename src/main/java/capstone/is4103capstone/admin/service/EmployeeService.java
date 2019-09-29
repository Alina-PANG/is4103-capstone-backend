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
        Employee working = employeeRepository.getOne(input.getId().orElseThrow(() -> new NullPointerException("UUID is null!")));
        input.getFirstName().ifPresent(working::setFirstName);
        input.getMiddleName().ifPresent(working::setMiddleName);
        input.getLastName().ifPresent(working::setLastName);
        input.getUserName().ifPresent(working::setUserName);
        input.getPassword().ifPresent(working::setPassword);
        return entityToDto(working);
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
        employeeDto.setCode(Optional.of(input.getCode()));
        employeeDto.setFirstName(Optional.of(input.getFirstName()));
        employeeDto.setMiddleName(Optional.of(input.getMiddleName()));
        employeeDto.setLastName(Optional.of(input.getLastName()));
        employeeDto.setUserName(Optional.of(input.getUserName()));
        employeeDto.setSecurityId(Optional.of(input.getSecurityId()));
        return employeeDto;
    }

    public Employee dtoToEntity(EmployeeDto input) {
        Employee employee = new Employee();
        input.getId().ifPresent(id -> employee.setId(id));
        input.getFirstName().ifPresent(name -> employee.setFirstName(name));
        input.getMiddleName().ifPresent(middleName -> employee.setMiddleName(middleName));
        input.getLastName().ifPresent(name -> employee.setLastName(name));
        input.getUserName().ifPresent(name -> employee.setUserName(name));
        input.getPassword().ifPresent(employee::setPassword);
        employee.setCode("EMP-" + input.getUserName());
        return employee;
    }

}
