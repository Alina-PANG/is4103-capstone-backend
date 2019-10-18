package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamService teamService;

    // TODO: refactor - same as getEmployeeEntityByUuid
    public Employee retrieveEmployeeById(String employeeId) throws EmployeeNotFoundException {
        if (employeeId == null || employeeId.trim().length() == 0) {
            throw new EmployeeNotFoundException("Invalid employee ID given!");
        }
        employeeId = employeeId.trim();
        Optional<Employee> optionalEmployee = employeeRepository.findUndeletedEmployeeById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " does not exist!");
        }

        return optionalEmployee.get();
    }

    // ===== CRUD METHODS =====
    // === CREATE ===
    @Transactional
    public Employee createNewEmployeeEntity(Employee input) {
        Employee newE = employeeRepository.save(input);
        newE = employeeRepository.findEmployeeById(newE.getId());
        newE.setCode("EMP-"+newE.getSeqNo());
        employeeRepository.save(newE);
        return newE;
    }

    public EmployeeDto createNewEmployee(EmployeeDto input) {
        return entityToDto(createNewEmployeeEntity(dtoToEntity(input)));
    }

    // === RETRIEVE ===
    public List<Employee> getAllEmployeeEntities() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) throw new Exception("No employees found in database!");
        return employees;
    }

    public List<EmployeeDto> getAllEmployees() throws Exception {
        List<EmployeeDto> employeeDtos = entityToDto(getAllEmployeeEntities(), true);
        if (employeeDtos.isEmpty()) throw new Exception("No active (non-deleted) employees found in the database.");
        return employeeDtos;
    }

    public Employee getEmployeeEntityByUuid(String input) throws Exception {
        Optional<Employee> employee = employeeRepository.findUndeletedEmployeeById(input);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new Exception("Employee with UUID " + input + " not found!");
        }
    }

    public EmployeeDto getEmployeeByUuid(String input) throws Exception {
        return entityToDto(getEmployeeEntityByUuid(input));
    }

    public Employee getEmployeeEntityBySid(String input) throws Exception {
        Employee employee = employeeRepository.findEmployeeBySecurityId(input);
        if (Objects.isNull(employee)) throw new Exception("Employee with SID " + input + " not found!");
        return employee;
    }

    public EmployeeDto getEmployeeBySid(String input) throws Exception {
        return entityToDto(getEmployeeEntityBySid(input));
    }

    public List<EmployeeDto> getEmployeeByTeamUuid(String teamUuid) throws Exception {
        // lookup the team
        Team team = teamService.getTeamEntityByUuid(teamUuid);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : team.getMembers()) {
            employeeDtos.add(entityToDto(employee));
        }
        return employeeDtos;
    }

    // === UPDATE ===
    @Transactional
    public Employee updateEmployeeEntity(Employee input) throws Exception {
        Employee working = getEmployeeEntityByUuid(input.getId());
        working.setFirstName(input.getFirstName());
        working.setMiddleName(input.getMiddleName());
        working.setLastName(input.getLastName());
        working.setUserName(input.getUserName());
        working.setEmail(input.getEmail());
        return working;
    }

    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto input) throws Exception {
        Employee working = getEmployeeEntityByUuid(input.getId().orElseThrow(() -> new NullPointerException("UUID is null!")));
        if (working.getDeleted())
            throw new Exception("Employee with UUID " + working.getId() + " has already been deleted and cannot be modified!");
        input.getFirstName().ifPresent(working::setFirstName);
        input.getMiddleName().ifPresent(working::setMiddleName);
        input.getLastName().ifPresent(working::setLastName);
        input.getUserName().ifPresent(working::setUserName);
        input.getPassword().ifPresent(working::setPassword);
        input.getEmail().ifPresent(working::setEmail);
        return entityToDto(working);
    }

    // === DELETE ===
    @Transactional
    public boolean deleteEmployee(String uuid) throws Exception {
        Employee employee = getEmployeeEntityByUuid(uuid);
        if (employee.getDeleted())
            throw new Exception("Employee with UUID " + employee.getId() + " has already been deleted and cannot be modified!");
        employee.setDeleted(true);
        return true;
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
        employeeDto.setEmail(Optional.of(input.getEmail()));
        return employeeDto;
    }

    public List<EmployeeDto> entityToDto(List<Employee> employees, boolean suppressDeleted) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            if (suppressDeleted) {
                if (!employee.getDeleted()) employeeDtos.add(entityToDto(employee));
            } else {
                employeeDtos.add(entityToDto(employee));
            }
        }
        return employeeDtos;
    }

    public Employee dtoToEntity(EmployeeDto input) {
        Employee employee = new Employee();
        input.getId().ifPresent(id -> employee.setId(id));
        input.getFirstName().ifPresent(name -> employee.setFirstName(name));
        input.getMiddleName().ifPresent(middleName -> employee.setMiddleName(middleName));
        input.getLastName().ifPresent(name -> employee.setLastName(name));
        input.getUserName().ifPresent(name -> employee.setUserName(name));
        input.getPassword().ifPresent(employee::setPassword);
        input.getEmail().ifPresent(employee::setEmail);
//        employee.setCode("EMP-" + input.getUserName());
        return employee;
    }

    public List<Employee> dtoToEntity(List<EmployeeDto> employeeDtos) {
        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto employeeDto : employeeDtos) {
            employees.add(dtoToEntity(employeeDto));
        }
        return employees;
    }

    public String getUsernameByUuid(String uuid){
        Employee e = employeeRepository.findEmployeeById(uuid);
        return e == null? null :e.getUserName();
    }

    public Employee validateUser(String idOrUsername) throws EntityNotFoundException {
        Optional<Employee> employee = employeeRepository.findUndeletedEmployeeById(idOrUsername);
        if (employee.isPresent())
            return employee.get();
        Employee e = employeeRepository.findEmployeeByUserName(idOrUsername);
        if (e != null)
            return e;

        throw new EntityNotFoundException("username or id not valid");
    }

}
