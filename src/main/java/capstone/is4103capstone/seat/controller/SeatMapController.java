package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.SeatMapModel;
import capstone.is4103capstone.seat.model.CustomErrorRes;
import capstone.is4103capstone.seat.model.SeatModelForSeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.seat.service.SeatMapService;
import capstone.is4103capstone.util.exception.SeatMapCreationException;
import capstone.is4103capstone.util.exception.SeatMapNotFoundException;
import capstone.is4103capstone.util.exception.SeatMapUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/seatmap")
public class SeatMapController {

    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatMapRepository seatMapRepository;

    public SeatMapController() {
    }

    @PostMapping
    public ResponseEntity createSeatMap(@RequestBody SeatMapModel createSeatMapReq) {

        try {
            String newSeatMapId = seatMapService.createNewSeatMap(createSeatMapReq);
            return ResponseEntity.ok("Created new seat map successfully: seatmap ID: " + newSeatMapId);
        } catch (SeatMapCreationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getSeatMapById(@PathVariable String id) {
        try {
            SeatMap seatMap = seatMapService.getSeatMapById(id);
            Office office = seatMap.getOffice();
            String country = office.getCountry().getObjectName();
            String region = office.getCountry().getRegion().getObjectName();
            SeatMapModel response = new SeatMapModel(seatMap.getId(), region, country, office.getObjectName(), seatMap.getFloor(), new ArrayList<>());

            for (Seat seat:
                 seatMap.getSeats()) {
                SeatModelForSeatMap seatModel = new SeatModelForSeatMap(seat.getSerialNumber(), seat.getId(),
                        seat.getActiveSeatAllocations().size() != 0, seat.getxCoordinate(), seat.getyCoordinate());
                response.getSeats().add(seatModel);
            }

            Collections.sort(response.getSeats());
            return ResponseEntity.ok().body(response);
        } catch (SeatMapNotFoundException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity editSeatMap(@RequestBody SeatMapModel editSeatMapReq) {
        try {
            seatMapService.updateSeatMap(editSeatMapReq);
            return ResponseEntity.ok("Edited seatmap successfully.");
        } catch (SeatMapUpdateException | SeatMapNotFoundException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteSeatMap(@PathVariable String id) {
        try {
            seatMapService.deleteSeatMapById(id);
            return ResponseEntity.ok("Deleted seat map successfully.");
        } catch (SeatMapCreationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
