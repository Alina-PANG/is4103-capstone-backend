package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.seat.model.schedule.EmployeeOfficeWorkingScheduleModel;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/workingSchedule")
@CrossOrigin
public class WorkingScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(WorkingScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;


    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping("/new")
    public ResponseEntity createNewEmployeeOfficeWorkingSchedule(@RequestBody EmployeeOfficeWorkingScheduleModel employeeOfficeWorkingScheduleModel) {
        EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule = entityModelConversionService.convertNewEmployeeOfficeWorkingScheduleToEntity(employeeOfficeWorkingScheduleModel);
        scheduleService.createNewEmployeeOfficeWorkingSchedule(employeeOfficeWorkingSchedule);
        return ResponseEntity.ok("Create employee office working schedule successfully!");
    }

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping
    public ResponseEntity retrieveEmployeeOfficeWorkingSchedule(@RequestParam(name = "employeeId", required = true) String employeeId,
                                                                @RequestParam(name = "officeId", required = true) String officeId) {
        Optional<EmployeeOfficeWorkingSchedule> optionalEmployeeOfficeWorkingSchedule = scheduleService.retrieveEmployeeOfficeWorkingSchedulesByEmployeeAndOffice(employeeId, officeId);
        if (!optionalEmployeeOfficeWorkingSchedule.isPresent()) {
            return ResponseEntity.ok("No required schedule found.");
        } else {
            EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule = optionalEmployeeOfficeWorkingSchedule.get();
            EmployeeOfficeWorkingScheduleModel employeeOfficeWorkingScheduleModel = entityModelConversionService.convertEmployeeOfficeWorkingScheduleEntityToModel(employeeOfficeWorkingSchedule);
            return ResponseEntity.ok(employeeOfficeWorkingScheduleModel);
        }
    }
}
