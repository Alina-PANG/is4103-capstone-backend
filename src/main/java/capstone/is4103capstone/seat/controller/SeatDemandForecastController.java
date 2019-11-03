package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.seatFutureDemand.MonthlySeatFutureDemandModelForTeam;
import capstone.is4103capstone.seat.model.seatFutureDemand.SeatFutureDemandGroupModelForTeam;
import capstone.is4103capstone.seat.service.SeatFutureDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation/future")
@CrossOrigin
public class SeatDemandForecastController {

    @Autowired
    private SeatFutureDemandService seatFutureDemandService;

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/team/6-month")
    public ResponseEntity getSeatDemandForecastForNext6MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext6MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }

    @GetMapping("/team/12-month")
    public ResponseEntity getSeatDemandForecastForNext12MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext12MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }
}
