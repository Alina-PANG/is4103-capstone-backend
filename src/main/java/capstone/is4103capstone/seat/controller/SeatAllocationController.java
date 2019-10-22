package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.*;
import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import capstone.is4103capstone.seat.model.seatAllocation.*;
import capstone.is4103capstone.seat.model.seatMap.SeatMapGroupModelForDeallocation;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForAllocation;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForAllocationWithHighlight;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import capstone.is4103capstone.seat.service.SeatManagementBackgroundService;
import capstone.is4103capstone.seat.service.SeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/seatAllocation")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatAllocationController {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationController.class);

    @Autowired
    private SeatAllocationService seatAllocationService;
    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private SeatManagementBackgroundService seatManagementBackgroundService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;
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
    public ResponseEntity markSeatsAsHotDesks(@RequestBody BulkSeatIdsModel bulkSeatIdsModel) {
        seatAllocationService.markSeatsAsHotDesk(bulkSeatIdsModel);
        return ResponseEntity.ok("Mark seats as hot desks successfully");
    }

    @PostMapping("/hotdesk/employee")
    public ResponseEntity assignHotDesksToEmployee(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
        seatAllocationService.assignHotDeskToAnEmployee(seatAllocationModelForEmployee);
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

//    @PostMapping("/shared")
//    public ResponseEntity assignSharedSeatToEmployee(@RequestBody SeatAllocationModelForEmployee seatAllocationModelForEmployee) {
//        seatAllocationService.assignSharedSeatToEmployee(seatAllocationModelForEmployee);
//        return ResponseEntity.ok("Assigned shared seat successfully");
//    }

    @PostMapping("/shared")
    public ResponseEntity assignSharedSeatToEmployeeWithMultipleSchedules(@RequestBody SharedSeatAllocationModelForEmployee sharedSeatAllocationModelForEmployee) {
        seatAllocationService.assignSharedSeatToEmployeeWithMultipleSchedules(sharedSeatAllocationModelForEmployee);
        return ResponseEntity.ok("Assigned shared seat successfully");
    }

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/seat/{id}")
    public ResponseEntity retrieveSeatWithItsAllocations(@PathVariable String id) {
        Seat seat = seatAllocationService.retrieveSeatWithAllocationsBySeatId(id);
        SeatModelForAllocation seatModelForAllocation = entityModelConversionService.convertSeatToSeatModelForAllocation(seat);
        return ResponseEntity.ok(seatModelForAllocation);
    }

    @GetMapping("/seatmap")
    public ResponseEntity retrieveSeatMapWithSeatAllocations(@RequestParam(name="seatMapId", required=true) String seatMapId) {
        SeatMap seatMap = seatMapService.getSeatMapById(seatMapId);
        SeatMapModelForAllocation seatMapModelForAllocation = new SeatMapModelForAllocation();
        seatMapModelForAllocation.setId(seatMapId);
        seatMapModelForAllocation.setCode(seatMap.getCode());
        for (Seat seat :
                seatMap.getSeats()) {
            SeatModelForAllocation seatModelForAllocation = entityModelConversionService.convertSeatToSeatModelForAllocation(seat);
            seatMapModelForAllocation.getSeatModelsForAllocation().add(seatModelForAllocation);
        }
        Collections.sort(seatMapModelForAllocation.getSeatModelsForAllocation());
        return ResponseEntity.ok(seatMapModelForAllocation);
    }

    @GetMapping("/seatmaps")
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
            SeatMapModelForAllocationWithHighlight seatMapModelForAllocationWithHighlight = new SeatMapModelForAllocationWithHighlight();
            seatMapModelForAllocationWithHighlight.setId(seatMap.getId());
            seatMapModelForAllocationWithHighlight.setCode(seatMap.getCode());

            List<SeatModelWithHighlighting> seatModelsForDeallocationViaSeatMap = entityModelConversionService.convertSeatsToSeatModelsWithHighlightingEmployee(seatMap.getSeats(), employeeId);
            seatMapModelForAllocationWithHighlight.setSeatModelsForAllocationViaSeatMap(seatModelsForDeallocationViaSeatMap);
            Collections.sort(seatMapModelForAllocationWithHighlight.getSeatModelsForAllocationViaSeatMap());
            return ResponseEntity.ok(seatMapModelForAllocationWithHighlight);
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
    public ResponseEntity deallocateSeatsFromFunction(@RequestBody BulkSeatIdsModel bulkSeatIdsModel) {
        seatAllocationService.deallocateSeatsFromFunction(bulkSeatIdsModel);
        return ResponseEntity.ok("Deallocated seats from the function successfully");
    }

    @DeleteMapping("/businessUnit")
    public ResponseEntity deallocateSeatsFromBusinessUnit(@RequestBody BulkSeatIdsModel bulkSeatIdsModel) {
        seatAllocationService.deallocateSeatsFromBusinessUnit(bulkSeatIdsModel);
        return ResponseEntity.ok("Deallocated seats from the business unit successfully");
    }

    @DeleteMapping("/team")
    public ResponseEntity deallocateSeatsFromTeam(@RequestBody BulkSeatIdsModel bulkSeatIdsModel) {
        seatAllocationService.deallocateSeatsFromTeam(bulkSeatIdsModel);
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
