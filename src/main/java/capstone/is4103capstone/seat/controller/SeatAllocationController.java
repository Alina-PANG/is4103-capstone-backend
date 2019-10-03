package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.finance.budget.controller.BudgetController;
import capstone.is4103capstone.seat.model.*;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatAllocationController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private SeatAllocationService seatAllocationService;

    public SeatAllocationController() {
    }

    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping("/function")
    public ResponseEntity allocateSeatsToFunction(@RequestBody SeatAllocationModelForFunction seatAllocationModelForFunction) {
        seatAllocationService.allocateSeatsToFunction(seatAllocationModelForFunction);
        return ResponseEntity.ok("Allocated Seats to the function successfully");
    }

    @PostMapping("/team")
    public ResponseEntity allocateSeatsToTeam(@RequestBody SeatAllocationModelForTeam seatAllocationModelForTeam) {
        seatAllocationService.allocateSeatsToTeam(seatAllocationModelForTeam);
        return ResponseEntity.ok("Allocated Seats to the team successfully");
    }

    @PostMapping("/hotdesk")
    public ResponseEntity assignHotDesks(@RequestBody SeatModelForAllocation seatModelForAllocation) {
        seatAllocationService.assignHotDesk(seatModelForAllocation);
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

        // Function and team
        CompanyFunction function = seat.getFunctionAssigned();
        GroupModel functionModel = new GroupModel();
        if (function != null) {
            functionModel.setId(function.getId());
            functionModel.setName(function.getObjectName());
            functionModel.setCode(function.getCode());
        }
        seatModelForAllocation.setFunctionAssigned(functionModel);

        Team team = seat.getTeamAssigned();
        GroupModel teamModel = new GroupModel();
        if (team != null) {
            teamModel.setId(team.getId());
            teamModel.setName(team.getObjectName());
            teamModel.setCode(team.getCode());
        }
        seatModelForAllocation.setTeamAssigned(teamModel);

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

    // ---------------------------------- DELETE: Delete ----------------------------------

    @DeleteMapping("/function")
    public ResponseEntity deallocateSeatsFromFunction(@RequestBody List<String> seatIds) {
        seatAllocationService.deallocateSeatsFromFunction(seatIds);
        return ResponseEntity.ok("Deallocated seats from the function successfully");
    }

    @DeleteMapping("/team")
    public ResponseEntity deallocateSeatsFromTeam(@RequestBody List<String> seatIds) {
        seatAllocationService.deallocateSeatsFromTeam(seatIds);
        return ResponseEntity.ok("Deallocated seats from the team successfully");
    }

    @DeleteMapping("/deallocation/{id}")
    public ResponseEntity deallocateSeatAllocation(@PathVariable String id) {
        seatAllocationService.deleteAllocation(id);
        return ResponseEntity.ok("Deallocated seat successfully");
    }
}
