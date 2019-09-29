package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ce/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // CREATE (POST)
    @PostMapping
    public EmployeeDto createNewEmployee(@RequestBody EmployeeDto input) {
        return employeeService.createNewEmployee(input);
    }

    // RETRIEVE (GET)
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{uuid}")
    public EmployeeDto getEmployee(@PathVariable(name = "uuid") String input) {
        try {
            return employeeService.getEmployeeByUuid(input);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // UPDATE (PUT)
    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto input) {
        return employeeService.updateEmployee(input);
    }

    // DELETE (DELETE)
    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteEmployee(@PathVariable(name = "uuid") String input) {
        try {
            employeeService.deleteEmployee(input);
            return ResponseEntity.status(200).body("Employee " + input + " deleted successfully!");
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
