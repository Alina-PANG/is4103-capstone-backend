package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.req.CreateSeatMapReq;
import capstone.is4103capstone.seat.model.res.CustomErrorRes;
import capstone.is4103capstone.seat.service.SeatMapService;
import capstone.is4103capstone.util.exception.SeatMapCreationException;
import capstone.is4103capstone.util.exception.SeatMapNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/seatmap")
public class SeatMapController {

    @Autowired
    private SeatMapService seatMapService;

    public SeatMapController() {
    }

    @GetMapping("/create")
    public ResponseEntity createSeatMap(CreateSeatMapReq createSeatMapReq) {
        try {
            String newSeatMapId = seatMapService.createNewSeatMap(createSeatMapReq);
            return ResponseEntity.ok("Created new seat map successfully.");
        } catch (SeatMapCreationException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getSeatMapById(@PathVariable String id) {
        try {
            SeatMap seatMap = seatMapService.getSeatMapById(id);
            return ResponseEntity.ok().body(seatMap);
        } catch (SeatMapNotFoundException ex) {
            CustomErrorRes error = new CustomErrorRes();
            error.setTimestamp(LocalDateTime.now());
            error.setError(ex.getMessage());
            error.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}
