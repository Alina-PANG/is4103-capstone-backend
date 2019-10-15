package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.finance.budget.controller.BudgetController;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForSetup;
import capstone.is4103capstone.seat.model.seat.SeatModelForSeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.seat.service.SeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/seatmap")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatMapController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

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


    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping
    public ResponseEntity createSeatMap(@RequestBody SeatMapModelForSetup createSeatMapReq) {
        String newSeatMapId = seatMapService.createNewSeatMap(createSeatMapReq);
        return ResponseEntity.ok("Created new seat map successfully: seatmap ID: " + newSeatMapId);
    }

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/{id}")
    public ResponseEntity getSeatMapById(@PathVariable String id) {
        SeatMap seatMap = seatMapService.getSeatMapById(id);
        Office office = seatMap.getOffice();
        Country country = office.getCountry();
        String region = office.getCountry().getRegion().getObjectName();
        SeatMapModelForSetup response = new SeatMapModelForSetup(seatMap.getId(), region, country.getObjectName(), country.getId(), office.getObjectName(), office.getId(), seatMap.getFloor(), new ArrayList<>());

        for (Seat seat:
                seatMap.getSeats()) {
            SeatModelForSeatMap seatModel = new SeatModelForSeatMap(seat.getSerialNumber(), seat.getId(),
                    seat.getActiveSeatAllocations().size() != 0, seat.getxCoordinate(), seat.getyCoordinate(), seat.isUnderOffice(), seat.getAdjacentSeatSeqNum());
            response.getSeats().add(seatModel);
        }

        Collections.sort(response.getSeats());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity getAllSeatMaps() {
        List<SeatMap> seatmaps = seatMapService.getAllSeatMaps();
        List<SeatMapModelForSetup> response = new ArrayList<>();

        for (SeatMap seatMap :
                seatmaps) {
            Office office = seatMap.getOffice();
            Country country = office.getCountry();
            String region = office.getCountry().getRegion().getObjectName();
            SeatMapModelForSetup seatMapModelForSetup = new SeatMapModelForSetup(seatMap.getId(), region, country.getObjectName(), country.getId(), office.getObjectName(), office.getId(), seatMap.getFloor(), new ArrayList<>());

            for (Seat seat:
                    seatMap.getSeats()) {
                SeatModelForSeatMap seatModel = new SeatModelForSeatMap(seat.getSerialNumber(), seat.getId(),
                        seat.getActiveSeatAllocations().size() != 0, seat.getxCoordinate(), seat.getyCoordinate(), seat.isUnderOffice(), seat.getAdjacentSeatSeqNum());
                seatMapModelForSetup.getSeats().add(seatModel);
            }

            Collections.sort(seatMapModelForSetup.getSeats());
            response.add(seatMapModelForSetup);
        }
        return ResponseEntity.ok().body(response);
    }

    // ---------------------------------- PUT: Update ----------------------------------

    @PutMapping
    public ResponseEntity editSeatMap(@RequestBody SeatMapModelForSetup editSeatMapReq) {
        seatMapService.updateSeatMap(editSeatMapReq);
        return ResponseEntity.ok("Edited seatmap successfully.");
    }

    // ---------------------------------- DELETE: Delete ----------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSeatMap(@PathVariable String id) {
        seatMapService.deleteSeatMapById(id);
        return ResponseEntity.ok("Deleted seat map successfully.");
    }
}
