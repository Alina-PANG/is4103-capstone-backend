package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForAllocation;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import capstone.is4103capstone.seat.service.SeatMapService;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.EntityModelConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation/request")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatAllocationRequestController {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationRequestController.class);

    @Autowired
    private SeatAllocationService seatAllocationService;
    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping
    public ResponseEntity retrieveSeatWithItsAllocations(@RequestParam(name="hierarchy", required=true) String hierarchy,
                                                         @RequestParam(name="hierarchyId", required=true) String hierarchyId,
                                                         @RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {

        // Convert the seat allocation model to seat allocation object
        SeatAllocation seatAllocation = new SeatAllocation();
        Employee employee = employeeService.retrieveEmployeeById(seatAllocationModelForEmployee.getEmployee().getId());
        seatAllocation.setEmployee(employee);
        seatAllocation.setActive(false);
        if (seatAllocationModelForEmployee.getType() == null || seatAllocationModelForEmployee.getType().trim().length() == 0) {
            throw new EntityModelConversionException("Seat Allocation Validation failed: seat allocation type is required!");
        }
        if (seatAllocationModelForEmployee.getType().equals("FIXED")) {
            seatAllocation.setAllocationType(SeatAllocationTypeEnum.FIXED);
        } else if (seatAllocationModelForEmployee.getType().equals("SHARED")) {
            seatAllocation.setAllocationType(SeatAllocationTypeEnum.SHARED);
        } else if (seatAllocationModelForEmployee.getType().equals("HOTDESK")) {
            seatAllocation.setAllocationType(SeatAllocationTypeEnum.HOTDESK);
        } else {
            throw new EntityModelConversionException("Seat Allocation Validation failed: invalid seat allocation type!");
        }

        seatAllocation.setSchedule(entityModelConversionService.convertScheduleModelToSchedule(seatAllocationModelForEmployee.getSchedule(),
                seatAllocation.getAllocationType().toString()));
        List<SeatMapModelForAllocation> seatMapModels = seatMapService.retrieveAvailableSeatMapsForAllocationByHierarchy(hierarchy, hierarchyId, seatAllocation);
        return ResponseEntity.ok(seatMapModels);
    }
}
