package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.seat.model.EmployeeOfficeWorkingScheduleModel;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.ScheduleService;
import capstone.is4103capstone.util.exception.EntityModelConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workingSchedule")
@CrossOrigin
public class WorkingScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;

    private static final Logger logger = LoggerFactory.getLogger(WorkingScheduleController.class);

    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping("/new")
    public ResponseEntity createNewEmployeeOfficeWorkingSchedule(@RequestBody EmployeeOfficeWorkingScheduleModel employeeOfficeWorkingScheduleModel) {
        EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule = entityModelConversionService.convertNewEmployeeOfficeWorkingScheduleToEntity(employeeOfficeWorkingScheduleModel);
        scheduleService.createNewEmployeeOfficeWorkingSchedule(employeeOfficeWorkingSchedule);
        return ResponseEntity.ok("Create employee office working schedule successfully!");
    }
}
