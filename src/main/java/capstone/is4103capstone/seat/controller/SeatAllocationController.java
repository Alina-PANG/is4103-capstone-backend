package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.finance.budget.controller.BudgetController;
import capstone.is4103capstone.seat.model.*;
import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelForDeallocationViaSeatMap;
import capstone.is4103capstone.seat.model.seatAllocation.*;
import capstone.is4103capstone.seat.model.seatMap.SeatMapGroupModelForDeallocation;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForDeallocation;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import capstone.is4103capstone.seat.service.SeatManagementBackgroundService;
import capstone.is4103capstone.seat.service.SeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.*;

@RestController
@RequestMapping("/api/seatAllocation")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatAllocationController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private SeatAllocationService seatAllocationService;
    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private SeatManagementBackgroundService seatManagementBackgroundService;
    @Autowired
    TaskScheduler taskScheduler;

    public SeatAllocationController() {
    }

    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping("/function")
    public ResponseEntity allocateSeatsToFunction(@RequestBody SeatAllocationModelForFunction seatAllocationModelForFunction) {
        seatAllocationService.allocateSeatsToFunction(seatAllocationModelForFunction);
        return ResponseEntity.ok("Allocated Seats to the function successfully");
    }

    @PostMapping("/businessUnit")
    public ResponseEntity allocateSeatsToBusinessUnit(@RequestBody SeatAllocationModelForBusinessUnit seatAllocationModelForBusinessUnit) {
        seatAllocationService.allocateSeatsToBusinessUnit(seatAllocationModelForBusinessUnit);
        return ResponseEntity.ok("Allocated Seats to the business unit successfully");
    }

    @PostMapping("/team")
    public ResponseEntity allocateSeatsToTeam(@RequestBody SeatAllocationModelForTeam seatAllocationModelForTeam) {
        seatAllocationService.allocateSeatsToTeam(seatAllocationModelForTeam);
        return ResponseEntity.ok("Allocated Seats to the team successfully");
    }

    @PostMapping("/hotdesk")
    public ResponseEntity assignHotDesks(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
        seatAllocationService.assignHotDesk(seatAllocationModelForEmployee);
        return ResponseEntity.ok("Assigned hot desk successfully");
    }

    @PostMapping("/fixed/permanent")
    public ResponseEntity assignFixedSeatToPermanentEmployee(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
        seatAllocationService.assignFixedSeatToPermanentEmployee(seatAllocationModelForEmployee);
        return ResponseEntity.ok("Assigned fixed seat successfully");
    }

    @PostMapping("/fixed/temporary")
    public ResponseEntity assignFixedSeatToTemporaryEmployee(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
        seatAllocationService.assignFixedSeatToTemporaryEmployee(seatAllocationModelForEmployee);
        return ResponseEntity.ok("Assigned fixed seat successfully");
    }

    @PostMapping("/shared")
    public ResponseEntity assignSharedSeatToEmployee(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
        seatAllocationService.assignSharedSeatToEmployee(seatAllocationModelForEmployee);
        return ResponseEntity.ok("Assigned shared seat successfully");
    }

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/seat/{id}")
    public ResponseEntity retrieveSeatWithItsAllocations(@PathVariable String id) {
        Seat seat = seatAllocationService.retrieveSeatWithAllocationsBySeatId(id);
        SeatModelForAllocation seatModelForAllocation = new SeatModelForAllocation();

        // Basic info
        seatModelForAllocation.setId(seat.getId());
        seatModelForAllocation.setCode(seat.getCode());
        seatModelForAllocation.setType(seat.getType().toString());

        // Function, business unit and team
        CompanyFunction function = seat.getFunctionAssigned();
        if (function != null) {
            GroupModel functionModel = new GroupModel();
            functionModel.setId(function.getId());
            functionModel.setName(function.getObjectName());
            functionModel.setCode(function.getCode());
            seatModelForAllocation.setFunctionAssigned(functionModel);
        }

        BusinessUnit businessUnit = seat.getBusinessUnitAssigned();
        if (businessUnit != null) {
            GroupModel businessUnitModel = new GroupModel();
            businessUnitModel.setId(businessUnit.getId());
            businessUnitModel.setName(businessUnit.getObjectName());
            businessUnitModel.setCode(businessUnit.getCode());
            seatModelForAllocation.setBusinessUnitAssigned(businessUnitModel);
        }

        Team team = seat.getTeamAssigned();
        if (team != null) {
            GroupModel teamModel = new GroupModel();
            teamModel.setId(team.getId());
            teamModel.setName(team.getObjectName());
            teamModel.setCode(team.getCode());
            seatModelForAllocation.setTeamAssigned(teamModel);
        }


        // Current occupancy
        SeatAllocationModelForEmployee currentOccupancyModel = new SeatAllocationModelForEmployee();
        SeatAllocation currentOccupancy = seat.getCurrentOccupancy();
        if (currentOccupancy != null) {
            currentOccupancyModel.setId(currentOccupancy.getId());
            currentOccupancyModel.setType(currentOccupancy.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(currentOccupancy.getEmployee().getId(), currentOccupancy.getEmployee().getFullName());
            currentOccupancyModel.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = currentOccupancy.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek:
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }
            currentOccupancyModel.setSchedule(scheduleModel);
        }
        seatModelForAllocation.setCurrentOccupancy(currentOccupancyModel);

        // Active allocations
        for (SeatAllocation allocation :
                seat.getActiveSeatAllocations()) {
            SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
            allocationModelForEmployee.setId(allocation.getId());
            allocationModelForEmployee.setType(allocation.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
            allocationModelForEmployee.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = allocation.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek:
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }

            allocationModelForEmployee.setSchedule(scheduleModel);
            seatModelForAllocation.getAllocations().add(allocationModelForEmployee);
        }
        return ResponseEntity.ok(seatModelForAllocation);
    }

    @GetMapping("/seatmap")
    public ResponseEntity retrieveSeatMapsContainingActiveEmployeeSeatAllocations(@RequestParam(name="employeeId", required=true) String employeeId) {
        List<SeatMap> seatMaps = seatMapService.retrieveSeatMapsWithActiveEmployeeAllocations(employeeId);
        List<GroupModel> seatMapModels = new ArrayList<>();
        for (SeatMap seatMap :
                seatMaps) {
            GroupModel seatMapModel = new GroupModel();
            seatMapModel.setId(seatMap.getId());
            seatMapModel.setName(seatMap.getObjectName());
            seatMapModel.setCode(seatMap.getCode());
            seatMapModels.add(seatMapModel);
        }
        SeatMapGroupModelForDeallocation seatMapGroupModelForDeallocation = new SeatMapGroupModelForDeallocation(seatMapModels);
        return ResponseEntity.ok(seatMapGroupModelForDeallocation);
    }

    @GetMapping("/seatmap/{seatMapId}")
    public ResponseEntity retrieveSeatMapContainingActiveEmployeeSeatAllocations(@PathVariable String seatMapId,
                                                                                 @RequestParam(name="employeeId", required=true) String employeeId) {
        Optional<SeatMap> optionalSeatMap = seatMapService.retrieveSeatMapWithActiveEmployeeAllocations(seatMapId, employeeId);
        if (optionalSeatMap.isPresent()) {
            SeatMap seatMap = optionalSeatMap.get();
            SeatMapModelForDeallocation seatMapModelForDeallocation = new SeatMapModelForDeallocation();
            seatMapModelForDeallocation.setId(seatMap.getId());
            seatMapModelForDeallocation.setCode(seatMap.getCode());

            List<SeatModelForDeallocationViaSeatMap> seatModelsForDeallocationViaSeatMap = new ArrayList<>();
            for (Seat seat :
                    seatMap.getSeats()) {
                // Basic info
                SeatModelForDeallocationViaSeatMap seatModelForDeallocationViaSeatMap = new SeatModelForDeallocationViaSeatMap();
                seatModelForDeallocationViaSeatMap.setId(seat.getId());
                seatModelForDeallocationViaSeatMap.setCode(seat.getCode());
                seatModelForDeallocationViaSeatMap.setSerialNumber(seat.getSerialNumber());
                seatModelForDeallocationViaSeatMap.setType(seat.getType().toString());
                seatModelForDeallocationViaSeatMap.setX(seat.getxCoordinate());
                seatModelForDeallocationViaSeatMap.setY(seat.getyCoordinate());
                seatModelForDeallocationViaSeatMap.setUnderOffice(seat.isUnderOffice());
                if (seat.isUnderOffice() && seat.getAdjacentSeatSeqNum() != null) {
                    seatModelForDeallocationViaSeatMap.setAdjacentSeatSeqNum(seat.getAdjacentSeatSeqNum());
                }

                // Function, business unit and team
                CompanyFunction function = seat.getFunctionAssigned();
                if (function != null) {
                    GroupModel functionModel = new GroupModel();
                    functionModel.setId(function.getId());
                    functionModel.setName(function.getObjectName());
                    functionModel.setCode(function.getCode());
                    seatModelForDeallocationViaSeatMap.setFunctionAssigned(functionModel);
                }

                BusinessUnit businessUnit = seat.getBusinessUnitAssigned();
                if (businessUnit != null) {
                    GroupModel businessUnitModel = new GroupModel();
                    businessUnitModel.setId(businessUnit.getId());
                    businessUnitModel.setName(businessUnit.getObjectName());
                    businessUnitModel.setCode(businessUnit.getCode());
                    seatModelForDeallocationViaSeatMap.setBusinessUnitAssigned(businessUnitModel);
                }

                Team team = seat.getTeamAssigned();
                if (team != null) {
                    GroupModel teamModel = new GroupModel();
                    teamModel.setId(team.getId());
                    teamModel.setName(team.getObjectName());
                    teamModel.setCode(team.getCode());
                    seatModelForDeallocationViaSeatMap.setTeamAssigned(teamModel);
                }

                // Current occupancy
                SeatAllocationModelForEmployee currentOccupancyModel = new SeatAllocationModelForEmployee();
                SeatAllocation currentOccupancy = seat.getCurrentOccupancy();
                if (currentOccupancy != null) {
                    currentOccupancyModel.setId(currentOccupancy.getId());
                    currentOccupancyModel.setType(currentOccupancy.getAllocationType().toString());

                    EmployeeModel employeeModel = new EmployeeModel(currentOccupancy.getEmployee().getId(), currentOccupancy.getEmployee().getFullName());
                    currentOccupancyModel.setEmployee(employeeModel);

                    ScheduleModel scheduleModel = new ScheduleModel();
                    Schedule schedule = currentOccupancy.getSchedule();
                    scheduleModel.setId(schedule.getId());
                    scheduleModel.setRecurring(schedule.isRecurring());
                    scheduleModel.setStartDateTime(schedule.getStartDateTime());
                    if (schedule.getEndDateTime() != null) {
                        scheduleModel.setEndDateTime(schedule.getEndDateTime());
                    }
                    if (schedule.getRecurringBasis() != null) {
                        scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
                    }
                    scheduleModel.setRecurringDates(schedule.getRecurringDates());
                    List<Integer> recurringWeekdays = new ArrayList<>();
                    for (DayOfWeek dayOfWeek:
                            schedule.getRecurringWeekdays()) {
                        recurringWeekdays.add(dayOfWeek.getValue());
                    }
                    scheduleModel.setRecurringWeekdays(recurringWeekdays);
                    if (schedule.getRecurringStartTime() != null) {
                        scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
                    }
                    if (schedule.getRecurringEndTime() != null) {
                        scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
                    }
                    currentOccupancyModel.setSchedule(scheduleModel);
                }
                seatModelForDeallocationViaSeatMap.setCurrentOccupancy(currentOccupancyModel);

                // Active allocations
                for (SeatAllocation allocation :
                        seat.getActiveSeatAllocations()) {
                    SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
                    allocationModelForEmployee.setId(allocation.getId());
                    allocationModelForEmployee.setType(allocation.getAllocationType().toString());

                    EmployeeModel employeeModel = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
                    allocationModelForEmployee.setEmployee(employeeModel);

                    ScheduleModel scheduleModel = new ScheduleModel();
                    Schedule schedule = allocation.getSchedule();
                    scheduleModel.setId(schedule.getId());
                    scheduleModel.setRecurring(schedule.isRecurring());
                    scheduleModel.setStartDateTime(schedule.getStartDateTime());
                    if (schedule.getEndDateTime() != null) {
                        scheduleModel.setEndDateTime(schedule.getEndDateTime());
                    }
                    if (schedule.getRecurringBasis() != null) {
                        scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
                    }
                    scheduleModel.setRecurringDates(schedule.getRecurringDates());
                    List<Integer> recurringWeekdays = new ArrayList<>();
                    for (DayOfWeek dayOfWeek:
                            schedule.getRecurringWeekdays()) {
                        recurringWeekdays.add(dayOfWeek.getValue());
                    }
                    scheduleModel.setRecurringWeekdays(recurringWeekdays);
                    if (schedule.getRecurringStartTime() != null) {
                        scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
                    }
                    if (schedule.getRecurringEndTime() != null) {
                        scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
                    }
                    if (allocation.getEmployee().getId().equals(employeeId)) {
                        seatModelForDeallocationViaSeatMap.setNeedHighlightForEmployee(true);
                    }

                    allocationModelForEmployee.setSchedule(scheduleModel);
                    seatModelForDeallocationViaSeatMap.getAllocations().add(allocationModelForEmployee);
                }

                seatModelsForDeallocationViaSeatMap.add(seatModelForDeallocationViaSeatMap);
            }

            seatMapModelForDeallocation.setSeatModelsForDeallocationViaSeatMap(seatModelsForDeallocationViaSeatMap);
            Collections.sort(seatMapModelForDeallocation.getSeatModelsForDeallocationViaSeatMap());
            return ResponseEntity.ok(seatMapModelForDeallocation);
        }

        return ResponseEntity.ok("No seat map was found to satisfy the condition.");
    }

    @GetMapping("/service/inactivate")
    public ResponseEntity triggerAllocationInactivationBackgroundService() {
        taskScheduler.scheduleAtFixedRate(seatManagementBackgroundService.runInactivateAllocations(), new Date(), 5000 );
        return ResponseEntity.ok("Services triggered successfully");
    }

    @GetMapping("/service/currentOccupancy")
    public ResponseEntity triggerCurrentOccupancyUpdateBackgroundService() {
        taskScheduler.scheduleAtFixedRate(seatManagementBackgroundService.runUpdateCurrentOccupancy(), new Date(), 5000 );
        return ResponseEntity.ok("Services triggered successfully");
    }

    // ---------------------------------- DELETE: Delete ----------------------------------

    @DeleteMapping("/function")
    public ResponseEntity deallocateSeatsFromFunction(@RequestBody BulkDeallocationModel bulkDeallocationModel) {
        seatAllocationService.deallocateSeatsFromFunction(bulkDeallocationModel);
        return ResponseEntity.ok("Deallocated seats from the function successfully");
    }

    @DeleteMapping("/businessUnit")
    public ResponseEntity deallocateSeatsFromBusinessUnit(@RequestBody BulkDeallocationModel bulkDeallocationModel) {
        seatAllocationService.deallocateSeatsFromBusinessUnit(bulkDeallocationModel);
        return ResponseEntity.ok("Deallocated seats from the business unit successfully");
    }

    @DeleteMapping("/team")
    public ResponseEntity deallocateSeatsFromTeam(@RequestBody BulkDeallocationModel bulkDeallocationModel) {
        seatAllocationService.deallocateSeatsFromTeam(bulkDeallocationModel);
        return ResponseEntity.ok("Deallocated seats from the team successfully");
    }

    @DeleteMapping("/deallocation/{id}")
    public ResponseEntity deallocateSeatAllocation(@PathVariable String id) {
        seatAllocationService.deleteAllocationByAllocationId(id);
        return ResponseEntity.ok("Deallocated seat successfully");
    }

    @DeleteMapping("/deallocation/employee")
    public ResponseEntity deallocateSeatAllocationByEmployeeId(@RequestParam(name="employeeId", required=true) String employeeId) {
        seatAllocationService.deleteAllocationsByEmployeeId(employeeId);
        return ResponseEntity.ok("Deallocated seat successfully");
    }

    @DeleteMapping("/deallocation/seat")
    public ResponseEntity deallocateSeatAllocationBySeatId(@RequestParam(name="seatId", required=true) String seatId) {
        seatAllocationService.deleteAllocationsBySeatId(seatId);
        return ResponseEntity.ok("Deallocated seat successfully");
    }
}
