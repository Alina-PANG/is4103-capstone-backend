package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.seatDemandForecast.MonthlySeatForecastModelForTeam;
import capstone.is4103capstone.seat.model.seatDemandForecast.SeatForecastGroupModelForTeam;
import capstone.is4103capstone.seat.model.seatMap.SeatMapGroupModelForDeallocation;
import capstone.is4103capstone.seat.service.SeatDemandForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation/forecast")
@CrossOrigin
public class SeatDemandForecastController {

    @Autowired
    private SeatDemandForecastService seatDemandForecastService;

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/team/6-month")
    public ResponseEntity getSeatDemandForecastForNext6MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatForecastGroupModelForTeam seatForecastGroupModelForTeam = new SeatForecastGroupModelForTeam();
        List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam = seatDemandForecastService.forecastSeatDemandForNext6MonthsByTeam(teamId);
        seatForecastGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatForecastGroupModelForTeam);
    }

    @GetMapping("/team/12-month")
    public ResponseEntity getSeatDemandForecastForNext12MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatForecastGroupModelForTeam seatForecastGroupModelForTeam = new SeatForecastGroupModelForTeam();
        List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam = seatDemandForecastService.forecastSeatDemandForNext12MonthsByTeam(teamId);
        seatForecastGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatForecastGroupModelForTeam);
    }
}
