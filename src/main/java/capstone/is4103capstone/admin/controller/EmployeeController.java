package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.controller.model.res.EmployeeRes;
import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/ce/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // CREATE (POST)
    @PostMapping
    public ResponseEntity<EmployeeRes> createNewEmployee(@RequestBody EmployeeDto input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new EmployeeRes(null, false, Optional.of(Collections.singletonList(employeeService.createNewEmployee(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EmployeeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // RETRIEVE (GET)
    @GetMapping
    public ResponseEntity<EmployeeRes> getAllEmployees(@RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new EmployeeRes(null, false, Optional.of(employeeService.getAllEmployees())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EmployeeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeRes> getEmployee(@PathVariable(name = "uuid") String input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new EmployeeRes(null, false, Optional.of(Collections.singletonList(employeeService.getEmployeeByUuid(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EmployeeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // UPDATE (PUT)
    @PutMapping
    public ResponseEntity<EmployeeRes> updateEmployee(@RequestBody EmployeeDto input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new EmployeeRes(null, false, Optional.of(Collections.singletonList(employeeService.updateEmployee(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EmployeeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // DELETE (DELETE)
    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteEmployee(@PathVariable(name = "uuid") String input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            employeeService.deleteEmployee(input);
            return ResponseEntity.ok().body(new EmployeeRes("Employee with UUID " + input + " successfully deleted!", false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EmployeeRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
