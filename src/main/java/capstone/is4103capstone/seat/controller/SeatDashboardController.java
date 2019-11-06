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

    // TODO: entity ID is not needed, the system checks the seat admin status of the current user and returns back the relevant data accordingly
    //      But a user may have more than one seat admin rights, in this case the request parameter for the entityId should be passed in, otherwise the
    //      the system cannot decide what to return.

    // -------------------------------------------------- Past Seat Utilisation --------------------------------------------------

    // -------------------------------------- Inventory vs Occupancy --------------------------------------


    @GetMapping("/utilisation/entity")
    public ResponseEntity retrieveBusinessLevelEntitySeatUtilisationAnalysis(@RequestParam(name = "entityId", required = false) String entityId,
                                                                             @RequestParam(name = "startDate") Date startDate,
                                                                             @RequestParam(name = "endDate") Date endDate) {

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
    public ResponseEntity retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(@RequestParam(name = "hierarchyType") String hierarchyType,
                                                                           @RequestParam(name = "entityId") String entityId,
                                                                           @RequestParam(name = "officeId") String officeId,
                                                                             @RequestParam(name = "startDate") Date startDate,
                                                                             @RequestParam(name = "endDate") Date endDate) {

        SeatBlockedForUseDateModel seatBlockedForUseDateModel = seatDataAnalyticsService.retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(hierarchyType, entityId, officeId, startDate, endDate);
        return ResponseEntity.ok(seatBlockedForUseDateModel);
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
