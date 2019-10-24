package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.seatDemandForecast.MonthlySeatForecastModelForTeam;
import capstone.is4103capstone.seat.model.seatMap.SeatMapGroupModelForDeallocation;
import capstone.is4103capstone.seat.service.SeatDemandForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation/forecast")
@CrossOrigin
public class SeatDemandForecastController {

    @Autowired
    private SeatDemandForecastService seatDemandForecastService;

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/team/6-month")
    public ResponseEntity getSeatDemandForecastForNext6MonthsByTeam(String teamId) {
        SeatMapGroupModelForDeallocation seatMapGroupModelForDeallocation = new SeatMapGroupModelForDeallocation();
        List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam = seatDemandForecastService.forecastSeatDemandForNext6MonthsByTeam(teamId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/team/12-month")
    public ResponseEntity getSeatDemandForecastForNext12MonthsByTeam(String teamId) {
        SeatMapGroupModelForDeallocation seatMapGroupModelForDeallocation = new SeatMapGroupModelForDeallocation();
        List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam = seatDemandForecastService.forecastSeatDemandForNext12MonthsByTeam(teamId);
        return ResponseEntity.ok("OK");
    }
}
