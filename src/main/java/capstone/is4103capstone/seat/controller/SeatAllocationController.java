package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.CustomErrorRes;
import capstone.is4103capstone.seat.model.SeatAllocationModelForFunction;
import capstone.is4103capstone.seat.model.SeatAllocationModelForTeam;
import capstone.is4103capstone.seat.model.SeatModelForAllocation;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import capstone.is4103capstone.util.exception.SeatAllocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        try {
            seatAllocationService.allocateSeatsToFunction(seatAllocationModelForFunction);
            return ResponseEntity.ok("Allocated Seats to the function successfully");
        } catch (SeatAllocationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/team")
    public ResponseEntity allocateSeatsToTeam(@RequestBody SeatAllocationModelForTeam seatAllocationModelForTeam) {
        try {
            seatAllocationService.allocateSeatsToTeam(seatAllocationModelForTeam);
            return ResponseEntity.ok("Allocated Seats to the team successfully");
        } catch (SeatAllocationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/function")
    public ResponseEntity deallocateSeatsFromFunction(@RequestBody List<SeatModelForAllocation> seatModels) {
        try {
            seatAllocationService.deallocateSeatsFromFunction(seatModels);
            return ResponseEntity.ok("Deallocated seats from the function successfully");
        } catch (SeatAllocationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/team")
    public ResponseEntity deallocateSeatsFromTeam(@RequestBody List<SeatModelForAllocation> seatModels) {
        try {
            seatAllocationService.deallocateSeatsFromTeam(seatModels);
            return ResponseEntity.ok("Deallocated seats from the team successfully");
        } catch (SeatAllocationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
