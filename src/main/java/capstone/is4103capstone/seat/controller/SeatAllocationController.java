package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.SeatAllocationModelForFunction;
import capstone.is4103capstone.seat.model.SeatAllocationModelForTeam;
import capstone.is4103capstone.seat.model.SeatModelForAllocation;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation")
public class SeatAllocationController {

    @Autowired
    private SeatAllocationService seatAllocationService;

    public SeatAllocationController() {
    }

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

    @DeleteMapping("/function")
    public ResponseEntity deallocateSeatsFromFunction(@RequestBody List<SeatModelForAllocation> seatModels) {
        seatAllocationService.deallocateSeatsFromFunction(seatModels);
        return ResponseEntity.ok("Deallocated seats from the function successfully");
    }

    @DeleteMapping("/team")
    public ResponseEntity deallocateSeatsFromTeam(@RequestBody List<SeatModelForAllocation> seatModels) {
        seatAllocationService.deallocateSeatsFromTeam(seatModels);
        return ResponseEntity.ok("Deallocated seats from the team successfully");
    }

    @PostMapping("/hotdesk")
    public ResponseEntity assignHotDesks(@RequestBody SeatModelForAllocation seatModelForAllocation) {
        seatAllocationService.assignHotDesk(seatModelForAllocation);
        return ResponseEntity.ok("Assigned hot desk successfully");
    }

    @PostMapping("/fixed/permanent")
    public ResponseEntity assignFixedSeatToPermanentEmployee(@RequestBody SeatModelForAllocation seatModelForAllocation) {
        seatAllocationService.assignFixedSeatToPermanentEmployee(seatModelForAllocation);
        return ResponseEntity.ok("Assigned fixed seat successfully");
    }

    @PostMapping("/fixed/temporary")
    public ResponseEntity assignFixedSeatToTemporaryEmployee(@RequestBody SeatModelForAllocation seatModelForAllocation) {
        seatAllocationService.assignFixedSeatToTemporaryEmployee(seatModelForAllocation);
        return ResponseEntity.ok("Assigned fixed seat successfully");
    }

    @PostMapping("/fixed/shared")
    public ResponseEntity assignSharedSeatToEmployee(@RequestBody SeatModelForAllocation seatModelForAllocation) {
        seatAllocationService.assignSharedSeatToEmployee(seatModelForAllocation);
        return ResponseEntity.ok("Assigned fixed seat successfully");
    }


}
