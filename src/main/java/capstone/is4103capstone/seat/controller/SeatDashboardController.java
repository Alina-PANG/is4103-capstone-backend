package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import capstone.is4103capstone.seat.model.seatDataAnalytics.*;
import capstone.is4103capstone.seat.model.seatFutureDemand.MonthlySeatFutureDemandModelForTeam;
import capstone.is4103capstone.seat.model.seatFutureDemand.SeatFutureDemandGroupModelForTeam;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.SeatDataAnalyticsService;
import capstone.is4103capstone.seat.service.SeatFutureDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/seat/dashboard")
@CrossOrigin
public class SeatDashboardController {

    @Autowired
    private SeatDataAnalyticsService seatDataAnalyticsService;
    @Autowired
    private SeatFutureDemandService seatFutureDemandService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;


    // -------------------------------------------------- Past Seat Utilisation --------------------------------------------------

    // -------------------------------------- Inventory vs Occupancy --------------------------------------

    @GetMapping("/utilisation/entity")
    public ResponseEntity retrieveBusinessLevelEntitySeatUtilisationAnalysis(@RequestParam(name = "entityId") String entityId,
                                                                             @RequestParam(name = "hierarchyType") String hierarchyType,
                                                                             @RequestParam(name = "officeId") String officeId,
                                                                             @RequestParam(name = "startDate") String startDate,
                                                                             @RequestParam(name = "endDate") String endDate) {
        SeatUtilisationDataModel seatUtilisationDataModel = new SeatUtilisationDataModel();
        seatUtilisationDataModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatUtilisationAnalysis(entityId, hierarchyType, officeId, startDate, endDate);
        Collections.sort(seatUtilisationDataModel.getLogs());
        return ResponseEntity.ok(seatUtilisationDataModel);
    }


    // -------------------------------------- Office: Used & Empty --------------------------------------

    @GetMapping("/utilisation/office/floor")
    public ResponseEntity retrieveOfficeFloorOfficeSeatsUtilisationData(@RequestParam(name = "seatMapId") String seatMapId) {

        OfficeFloorOfficeSeatUtilisationDataModel officeFloorOfficeSeatUtilisationDataModel = seatDataAnalyticsService.retrieveOfficeFloorOfficeSeatsUtilisationData(seatMapId, true);
        return ResponseEntity.ok(officeFloorOfficeSeatUtilisationDataModel);
    }

    @GetMapping("/utilisation/office/building")
    public ResponseEntity retrieveBuildingOfficeSeatsUtilisationData(@RequestParam(name = "officeId", required = true) String officeId) {

        BuildingOfficeSeatUtilisationDataModel buildingOfficeSeatUtilisationDataModel = seatDataAnalyticsService.retrieveBuildingOfficeSeatsUtilisationData(officeId);
        return ResponseEntity.ok(buildingOfficeSeatUtilisationDataModel);
    }


    // -------------------------------------- Blocked For Use (Per Office) --------------------------------------

    @GetMapping("/blocked/use/office")
    public ResponseEntity retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(@RequestParam(name = "entityId") String entityId,
                                                                                      @RequestParam(name = "hierarchyType") String hierarchyType,
                                                                                      @RequestParam(name = "officeId") String officeId,
                                                                                      @RequestParam(name = "startDate") String startDate,
                                                                                      @RequestParam(name = "endDate") String endDate) {

        SeatBlockedForUseDataModel seatBlockedForUseDataModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(entityId, hierarchyType, officeId, startDate, endDate);
        return ResponseEntity.ok(seatBlockedForUseDataModel);
    }


    // -------------------------------------- Blocked But Unused (Per Office) For > 2 Weeks --------------------------------------

    @GetMapping("/blocked/unused/office")
    public ResponseEntity retrieveBusinessLevelEntitySeatBlockedButUnusedData(@RequestParam(name = "hierarchyType") String hierarchyType,
                                                                                      @RequestParam(name = "entityId", required = false) String entityId,
                                                                                      @RequestParam(name = "officeId") String officeId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatBlockedButUnusedData(hierarchyType, entityId, officeId);
        return ResponseEntity.ok(seatBlockedUnusedDataModel);
    }


    // -------------------------------------------------- Future Demand --------------------------------------------------

    @GetMapping("/future/6-month/team")
    public ResponseEntity getSeatDemandForecastForNext6MonthsByTeam(@RequestParam(name="teamId") String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext6MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }

    @GetMapping("/future/12-month/team")
    public ResponseEntity getSeatDemandForecastForNext12MonthsByTeam(@RequestParam(name="teamId") String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext12MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }
}
