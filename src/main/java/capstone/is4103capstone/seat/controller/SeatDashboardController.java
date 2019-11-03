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

import java.util.ArrayList;
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

    @GetMapping("/utilisation/entity/allOffices")
    public ResponseEntity retrieveBusinessLevelEntitySeatUtilisationAnalysis(@RequestParam(name = "entityId", required = true) String entityId,
                                                                             @RequestParam(name = "startDate", required = true) Date startDate,
                                                                             @RequestParam(name = "endDate", required = true) Date endDate) {

        SeatUtilisationGroupModel seatUtilisationGroupModel = new SeatUtilisationGroupModel();
        List<SeatUtilisationLog> seatUtilisationLogs = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatUtilisationAnalysis(entityId, startDate, endDate);
        List<SeatUtilisationModel> seatUtilisationModels = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            seatUtilisationModels.add(entityModelConversionService.convertSeatUtilisationLogIntoModel(log));
        }
        seatUtilisationGroupModel.setSeatUtilisationModels(seatUtilisationModels);

        return ResponseEntity.ok(seatUtilisationGroupModel);
    }


    // -------------------------------------- Office: Used & Empty --------------------------------------

    @GetMapping("/utilisation/officeFloor")
    public ResponseEntity retrieveOfficeFloorOfficeSeatsUtilisationData(@RequestParam(name = "seatMapId", required = true) String seatMapId) {

        OfficeFloorOfficeSeatUtilisationDataModel officeFloorOfficeSeatUtilisationDataModel = seatDataAnalyticsService.retrieveOfficeFloorOfficeSeatsUtilisationData(seatMapId, true);
        return ResponseEntity.ok(officeFloorOfficeSeatUtilisationDataModel);
    }

    @GetMapping("/utilisation/office")
    public ResponseEntity retrieveBuildingOfficeSeatsUtilisationData(@RequestParam(name = "officeId", required = true) String officeId) {

        BuildingOfficeSeatUtilisationDataModel buildingOfficeSeatUtilisationDataModel = seatDataAnalyticsService.retrieveBuildingOfficeSeatsUtilisationData(officeId);
        return ResponseEntity.ok(buildingOfficeSeatUtilisationDataModel);
    }


    // -------------------------------------- Blocked For Use (Per Office) --------------------------------------

    @GetMapping("/blocked/use/office")
    public ResponseEntity retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(@RequestParam(name = "hierarchyType", required = true) String hierarchyType,
                                                                           @RequestParam(name = "entityId", required = true) String entityId,
                                                                           @RequestParam(name = "officeId", required = true) String officeId,
                                                                             @RequestParam(name = "startDate", required = true) Date startDate,
                                                                             @RequestParam(name = "endDate", required = true) Date endDate) {

        SeatBlockedForUseDateModel seatBlockedForUseDateModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(hierarchyType, entityId, officeId, startDate, endDate);
        return ResponseEntity.ok(seatBlockedForUseDateModel);
    }


    // -------------------------------------- Blocked But Unused (Per Office) For > 2 Weeks --------------------------------------

    @GetMapping("/blocked/unused/office")
    public ResponseEntity retrieveBusinessLevelEntitySeatBlockedButUnusedData(@RequestParam(name = "hierarchyType", required = true) String hierarchyType,
                                                                                      @RequestParam(name = "entityId", required = true) String entityId,
                                                                                      @RequestParam(name = "officeId", required = true) String officeId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatBlockedButUnusedData(hierarchyType, entityId, officeId);
        return ResponseEntity.ok(seatBlockedUnusedDataModel);
    }


    @GetMapping("/future/6-month/team")
    public ResponseEntity getSeatDemandForecastForNext6MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext6MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }

    @GetMapping("/future/12-month/team")
    public ResponseEntity getSeatDemandForecastForNext12MonthsByTeam(@RequestParam(name="teamId", required=true) String teamId) {
        SeatFutureDemandGroupModelForTeam seatFutureDemandGroupModelForTeam = new SeatFutureDemandGroupModelForTeam();
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = seatFutureDemandService.forecastSeatDemandForNext12MonthsByTeam(teamId);
        seatFutureDemandGroupModelForTeam.setMonthlySeatForecastModelsForTeam(monthlySeatForecastModelsForTeam);
        return ResponseEntity.ok(seatFutureDemandGroupModelForTeam);
    }
}
