package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.seatDataAnalytics.BuildingOfficeSeatUtilisationDataModel;
import capstone.is4103capstone.seat.model.seatDataAnalytics.OfficeFloorOfficeSeatUtilisationDataModel;
import capstone.is4103capstone.seat.model.seatDataAnalytics.SeatBlockedForUseDateModel;
import capstone.is4103capstone.seat.model.seatDataAnalytics.SeatBlockedUnusedDataModel;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.seat.repository.SeatUtilisationLogRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.InvalidInputException;
import capstone.is4103capstone.util.exception.OfficeNotFoundException;
import capstone.is4103capstone.util.exception.UnauthorizedActionException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatDataAnalyticsService {

    @Autowired
    private SeatRequestAdminMatchService seatRequestAdminMatchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private BusinessUnitService businessUnitService;
    @Autowired
    private CompanyFunctionService companyFunctionService;
    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private OfficeService officeService;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatUtilisationLogRepository seatUtilisationLogRepository;


    // Seats
    // - Future seat allocations
    // --- By Hiring
    // --- By Leavers
    // --- Net Demand
    // Offices
    // - Used
    // - Empty
    // Employees without seats

    // Analysis model returned:
    // 1. Inventory
    // 2. Occupancy: the seat has more than one allocation during the period
    // Note: End date is inclusive

    // -------------------------------------------------- Past Seat Utilisation --------------------------------------------------

    // -------------------------------------- Inventory vs Occupancy --------------------------------------

    // By office, office floor, company function, business unit and team
    public List<SeatUtilisationLog> retrieveBusinessLevelEntitySeatUtilisationAnalysis(String levelEntityId, Date startDate, Date endDate) {

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(levelEntityId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
            throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
        }

        startDate = DateHelper.getDateWithoutTimeUsingCalendar(startDate);
        endDate = DateHelper.getDaysAfter(DateHelper.getDateWithoutTimeUsingCalendar(endDate), 1);

        // retrieve the logs created after the start date and before the end date
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriod(levelEntityId, startDate, endDate);
        return seatUtilisationLogs;
    }


    // -------------------------------------- Blocked For Use (Per Office) --------------------------------------

    // Office managers can see a pie chart: among all the seats the office has, how many seats each company function occupies -> company function breakdown
    // Department heads can see a pie chart: among all the seats assigned to the company function, how many seats each business unit occupies -> unit breakdown
    // Business unit heads can see a pie chart: among all the seats assigned to the business unit, how many seats each team occupies -> team breakdown
    public SeatBlockedForUseDateModel retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(String hierarchyType, String levelEntityId,
                                                                                                  String officeId, Date startDate, Date endDate) {

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(levelEntityId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
            throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
        }

        startDate = DateHelper.getDateWithoutTimeUsingCalendar(startDate);
        endDate = DateHelper.getDaysAfter(DateHelper.getDateWithoutTimeUsingCalendar(endDate), 1);

        SeatBlockedForUseDateModel seatBlockedForUseDateModel = new SeatBlockedForUseDateModel();
        // Recursive process of retrieving seat blocking information
        seatBlockedForUseDateModel = aggregateOfficeBusinessLevelEntitySeatBlockedForUseData(seatBlockedForUseDateModel, hierarchyType, levelEntityId, officeId,
                startDate, endDate, false);

        return seatBlockedForUseDateModel;
    }

    // Recursive retrieval to generate the hierarchy tree -> begin with the team and then add up for aggregation
    // The model returned is the parent model with newly child added in
    private SeatBlockedForUseDateModel aggregateOfficeBusinessLevelEntitySeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                                         String hierarchyType, String levelEntityId,
                                                                                         String officeId, Date startDate, Date endDate,
                                                                                               boolean constructNew) {
        // Check the entity level type
        if (hierarchyType.equals(HierarchyTypeEnum.OFFICE.toString())) {
            return aggregateOfficeSeatBlockedForUseData(seatBlockedForUseDateModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.OFFICE_FLOOR.toString())) {
            return aggregateOfficeFloorSeatBlockedForUseData(seatBlockedForUseDateModel, levelEntityId, officeId, startDate, endDate, false);
        } else if (hierarchyType.equals(HierarchyTypeEnum.COMPANY_FUNCTION.toString())) {
            return aggregateCompanyFunctionSeatBlockedForUseData(seatBlockedForUseDateModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.BUSINESS_UNIT.toString())) {
            return aggregateBusinessUnitSeatBlockedForUseData(seatBlockedForUseDateModel, levelEntityId, officeId, startDate, endDate, false);
        } else if (hierarchyType.equals(HierarchyTypeEnum.TEAM.toString())) {
            return aggregateTeamSeatBlockedForUseData(seatBlockedForUseDateModel, levelEntityId, officeId, startDate, endDate, false);
        } else {
            throw new InvalidInputException("Retrieving Data failed: invalid hierarchy type.");
        }
    }


    private SeatBlockedForUseDateModel aggregateTeamSeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                                               String levelEntityId, String officeId, Date startDate, Date endDate,
                                                                                               boolean constructNew) {
        Team team = teamService.retrieveTeamById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());
        teamModel.setCode(team.getCode());

        JSONObject jsonObject = new JSONObject();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            jsonObject.put(log.getCreatedTime().toString(), log.getInventoryCount());
        }

        if (constructNew) {
            SeatBlockedForUseDateModel newModel = new SeatBlockedForUseDateModel();
            newModel.setEntity(teamModel);
            newModel.setSeatBlockedCountWithDate(jsonObject);
            seatBlockedForUseDateModel.getChildren().add(newModel);
            return seatBlockedForUseDateModel;
        } else {
            seatBlockedForUseDateModel.setEntity(teamModel);
            seatBlockedForUseDateModel.setSeatBlockedCountWithDate(jsonObject);
            return seatBlockedForUseDateModel;
        }
    }

    private SeatBlockedForUseDateModel aggregateBusinessUnitSeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                          String levelEntityId, String officeId, Date startDate, Date endDate,
                                                                          boolean constructNew) {
        BusinessUnit businessUnit = businessUnitService.retrieveBusinessUnitById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel unitModel = new GroupModel();
        unitModel.setId(businessUnit.getId());
        unitModel.setName(businessUnit.getObjectName());
        unitModel.setCode(businessUnit.getCode());


        JSONObject jsonObject = new JSONObject();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            jsonObject.put(log.getCreatedTime().toString(), log.getInventoryCount());
        }

        if (constructNew) {
            SeatBlockedForUseDateModel newModel = new SeatBlockedForUseDateModel();
            newModel.setEntity(unitModel);
            newModel.setSeatBlockedCountWithDate(jsonObject);
            seatBlockedForUseDateModel.getChildren().add(newModel);
            // Construct children: teams under the business unit
            List<Team> teams = teamRepository.findOnesUnderBusinessUnit(levelEntityId);
            for (Team team :
                    teams) {
                newModel = aggregateTeamSeatBlockedForUseData(newModel, team.getId(), officeId, startDate, endDate, true);
            }
            return seatBlockedForUseDateModel;
        } else {
            seatBlockedForUseDateModel.setEntity(unitModel);
            seatBlockedForUseDateModel.setSeatBlockedCountWithDate(jsonObject);
            // Construct children: teams under the business unit
            List<Team> teams = teamRepository.findOnesUnderBusinessUnit(levelEntityId);
            for (Team team :
                    teams) {
                seatBlockedForUseDateModel.getChildren().add(aggregateTeamSeatBlockedForUseData(seatBlockedForUseDateModel, team.getId(),
                        officeId, startDate, endDate, true));
            }
            return seatBlockedForUseDateModel;
        }
    }

    private SeatBlockedForUseDateModel aggregateCompanyFunctionSeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                                  String levelEntityId, String officeId, Date startDate, Date endDate) {
        CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel functionModel = new GroupModel();
        functionModel.setId(companyFunction.getId());
        functionModel.setName(companyFunction.getObjectName());
        functionModel.setCode(companyFunction.getCode());
        seatBlockedForUseDateModel.setEntity(functionModel);

        JSONObject jsonObject = new JSONObject();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            jsonObject.put(log.getCreatedTime().toString(), log.getInventoryCount());
        }
        seatBlockedForUseDateModel.setSeatBlockedCountWithDate(jsonObject);

        // Construct children: business units under the function
        List<BusinessUnit> businessUnits = businessUnitRepository.findOnesUnderCompanyFunction(levelEntityId);
        for (BusinessUnit unit :
                businessUnits) {
            SeatBlockedForUseDateModel childModel = new SeatBlockedForUseDateModel();
            childModel = aggregateBusinessUnitSeatBlockedForUseData(childModel, unit.getId(), officeId, startDate, endDate, true);
            seatBlockedForUseDateModel.getChildren().add(childModel);
        }
        return seatBlockedForUseDateModel;
    }

    private SeatBlockedForUseDateModel aggregateOfficeFloorSeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                            String levelEntityId, String officeId,
                                                                            Date startDate, Date endDate,
                                                                            boolean constructNew) {
        SeatMap seatMap = seatMapService.getSeatMapById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel seatMapModel = new GroupModel();
        seatMapModel.setId(seatMap.getId());
        seatMapModel.setName(seatMap.getObjectName());
        seatMapModel.setCode(seatMap.getCode());

        JSONObject jsonObject = new JSONObject();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            jsonObject.put(log.getCreatedTime().toString(), log.getInventoryCount());
        }

        if (constructNew) {
            SeatBlockedForUseDateModel newModel = new SeatBlockedForUseDateModel();
            newModel.setEntity(seatMapModel);
            newModel.setSeatBlockedCountWithDate(jsonObject);
            seatBlockedForUseDateModel.getChildren().add(newModel);
        } else {
            seatBlockedForUseDateModel.setEntity(seatMapModel);
            seatBlockedForUseDateModel.setSeatBlockedCountWithDate(jsonObject);
        }

        return seatBlockedForUseDateModel;
    }

    private SeatBlockedForUseDateModel aggregateOfficeSeatBlockedForUseData(SeatBlockedForUseDateModel seatBlockedForUseDateModel,
                                                                            String levelEntityId, String officeId,
                                                                            Date startDate, Date endDate) {
        try {
            Office office = officeService.getOfficeEntityByUuid(levelEntityId);
            List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                    levelEntityId, startDate, endDate, officeId);

            GroupModel officeModel = new GroupModel();
            officeModel.setId(office.getId());
            officeModel.setName(office.getObjectName());
            officeModel.setCode(office.getCode());
            seatBlockedForUseDateModel.setEntity(officeModel);

            JSONObject jsonObject = new JSONObject();
            for (SeatUtilisationLog log :
                    seatUtilisationLogs) {
                jsonObject.put(log.getCreatedTime().toString(), log.getInventoryCount());
            }
            seatBlockedForUseDateModel.setSeatBlockedCountWithDate(jsonObject);

            // Construct children: business units under the function
            List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(levelEntityId);
            for (SeatMap map :
                    seatMaps) {
                SeatBlockedForUseDateModel childModel = new SeatBlockedForUseDateModel();
                childModel = aggregateOfficeFloorSeatBlockedForUseData(childModel, map.getId(), officeId, startDate, endDate, true);
                seatBlockedForUseDateModel.getChildren().add(childModel);
            }
            return seatBlockedForUseDateModel;
        } catch (Exception ex) {
            throw new OfficeNotFoundException(ex.getMessage());
        }
    }


    // -------------------------------------- Blocked But Unused (Per Office) For > 2 Weeks --------------------------------------

    public SeatBlockedUnusedDataModel retrieveBusinessLevelEntitySeatBlockedButUnusedData(String hierarchyType, String levelEntityId, String officeId) {

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(levelEntityId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
            throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
        }

        // Check the entity level type
        if (hierarchyType.equals(HierarchyTypeEnum.OFFICE.toString())) {
            return aggregateOfficeBlockedUnusedData(levelEntityId);
        } else if (hierarchyType.equals(HierarchyTypeEnum.OFFICE_FLOOR.toString())) {
            return aggregateOfficeFloorBlockedUnusedData(levelEntityId);
        } else if (hierarchyType.equals(HierarchyTypeEnum.COMPANY_FUNCTION.toString())) {
            return aggregateCompanyFunctionSeatBlockedUnusedData(levelEntityId, officeId);
        } else if (hierarchyType.equals(HierarchyTypeEnum.BUSINESS_UNIT.toString())) {
            return aggregateBusinessUnitSeatBlockedUnusedData(levelEntityId, officeId);
        } else if (hierarchyType.equals(HierarchyTypeEnum.TEAM.toString())) {
            return aggregateTeamSeatBlockedUnusedData(levelEntityId);
        } else {
            throw new InvalidInputException("Retrieving Data failed: invalid hierarchy type.");
        }
    }

    private SeatBlockedUnusedDataModel aggregateTeamSeatBlockedUnusedData(String teamId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = new SeatBlockedUnusedDataModel();

        Team team = teamService.retrieveTeamById(teamId);
        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());
        teamModel.setCode(team.getCode());
        seatBlockedUnusedDataModel.setEntity(teamModel);

        Integer unusedCount = 0;

        List<Seat> seatsUnderTeam = seatRepository.findOnesByTeamId(teamId);
        seatBlockedUnusedDataModel.setCurrentInventoryCount(seatsUnderTeam.size());
        for (Seat seat :
             seatsUnderTeam) {
            if (seat.getCurrentOccupancy() == null) {
                if (seat.getLastOccupancy() == null ||
                        seat.getLastOccupancy().getSchedule().getEndDateTime().before(DateHelper.getDaysBefore(new Date(), 14))) {
                    unusedCount++;
                }
            }
        }
        seatBlockedUnusedDataModel.setUnusedCount(unusedCount);
        return seatBlockedUnusedDataModel;
    }

    private SeatBlockedUnusedDataModel aggregateBusinessUnitSeatBlockedUnusedData(String businessUnitId, String officeId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = new SeatBlockedUnusedDataModel();

        BusinessUnit businessUnit = businessUnitService.retrieveBusinessUnitById(businessUnitId);

        GroupModel unitModel = new GroupModel();
        unitModel.setId(businessUnit.getId());
        unitModel.setName(businessUnit.getObjectName());
        unitModel.setCode(businessUnit.getCode());
        seatBlockedUnusedDataModel.setEntity(unitModel);

        Integer unusedCount = 0;
        Integer inventoryCount = 0;

        List<Team> teams = teamRepository.findOnesUnderBusinessUnitAndOffice(businessUnitId, officeId);
        for (Team team :
                teams) {
            SeatBlockedUnusedDataModel teamUnusedModel = aggregateTeamSeatBlockedUnusedData(team.getId());
            inventoryCount += teamUnusedModel.getCurrentInventoryCount();
            unusedCount += teamUnusedModel.getUnusedCount();
            seatBlockedUnusedDataModel.getChildren().add(teamUnusedModel);
        }

        seatBlockedUnusedDataModel.setUnusedCount(unusedCount);
        seatBlockedUnusedDataModel.setCurrentInventoryCount(inventoryCount);
        return seatBlockedUnusedDataModel;
    }

    private SeatBlockedUnusedDataModel aggregateCompanyFunctionSeatBlockedUnusedData(String functionId, String officeId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = new SeatBlockedUnusedDataModel();

        CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(functionId);

        GroupModel functionModel = new GroupModel();
        functionModel.setId(companyFunction.getId());
        functionModel.setName(companyFunction.getObjectName());
        functionModel.setCode(companyFunction.getCode());
        seatBlockedUnusedDataModel.setEntity(functionModel);

        Integer unusedCount = 0;
        Integer inventoryCount = 0;

        List<BusinessUnit> businessUnits = businessUnitRepository.findOnesUnderCompanyFunctionAndOffice(functionId, officeId);
        for (BusinessUnit businessUnit :
                businessUnits) {
            SeatBlockedUnusedDataModel businessUnitUnusedModel = aggregateBusinessUnitSeatBlockedUnusedData(businessUnit.getId(), officeId);
            inventoryCount += businessUnitUnusedModel.getCurrentInventoryCount();
            unusedCount += businessUnitUnusedModel.getUnusedCount();
            seatBlockedUnusedDataModel.getChildren().add(businessUnitUnusedModel);
        }

        seatBlockedUnusedDataModel.setUnusedCount(unusedCount);
        seatBlockedUnusedDataModel.setCurrentInventoryCount(inventoryCount);
        return seatBlockedUnusedDataModel;
    }

    private SeatBlockedUnusedDataModel aggregateOfficeFloorBlockedUnusedData(String seatMapId) {

        SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = new SeatBlockedUnusedDataModel();

        SeatMap seatMap = seatMapService.getSeatMapById(seatMapId);
        GroupModel seatMapModel = new GroupModel();
        seatMapModel.setId(seatMap.getId());
        seatMapModel.setName(seatMap.getObjectName());
        seatMapModel.setCode(seatMap.getCode());
        seatBlockedUnusedDataModel.setEntity(seatMapModel);

        Integer unusedCount = 0;

        List<Seat> seats = seatMap.getSeats();
        seatBlockedUnusedDataModel.setCurrentInventoryCount(seats.size());
        for (Seat seat :
                seats) {
            if (seat.getCurrentOccupancy() == null) {
                if (seat.getLastOccupancy() == null ||
                        seat.getLastOccupancy().getSchedule().getEndDateTime().before(DateHelper.getDaysBefore(new Date(), 14))) {
                    unusedCount++;
                }
            }
        }
        seatBlockedUnusedDataModel.setUnusedCount(unusedCount);
        return seatBlockedUnusedDataModel;
    }

    private SeatBlockedUnusedDataModel aggregateOfficeBlockedUnusedData(String officeId) {

        try {
            SeatBlockedUnusedDataModel seatBlockedUnusedDataModel = new SeatBlockedUnusedDataModel();

            Office office = officeService.getOfficeEntityByUuid(officeId);
            GroupModel officeModel = new GroupModel();
            officeModel.setId(office.getId());
            officeModel.setName(office.getObjectName());
            officeModel.setCode(office.getCode());
            seatBlockedUnusedDataModel.setEntity(officeModel);

            Integer inventoryCount = 0;
            Integer unusedCount = 0;

            List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(officeId);

            for (SeatMap seatMap :
                    seatMaps) {
                SeatBlockedUnusedDataModel officeFloorUnusedModel = aggregateOfficeFloorBlockedUnusedData(seatMap.getId());
                inventoryCount += officeFloorUnusedModel.getCurrentInventoryCount();
                unusedCount += officeFloorUnusedModel.getUnusedCount();
                seatBlockedUnusedDataModel.getChildren().add(officeFloorUnusedModel);
            }
            seatBlockedUnusedDataModel.setCurrentInventoryCount(inventoryCount);
            seatBlockedUnusedDataModel.setUnusedCount(unusedCount);
            return seatBlockedUnusedDataModel;
        } catch (Exception ex) {
            throw new OfficeNotFoundException(ex.getMessage());
        }
    }


    // -------------------------------------- Future Supply & Demand --------------------------------------


    // -------------------------------------- Office: Used & Empty --------------------------------------

    public OfficeFloorOfficeSeatUtilisationDataModel retrieveOfficeFloorOfficeSeatsUtilisationData(String seatMapId, boolean needAccessRightChecking) {

        if (needAccessRightChecking) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(seatMapId);
            if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
                throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
            }
        }

        OfficeFloorOfficeSeatUtilisationDataModel officeFloorOfficeSeatUtilisationDataModel = new OfficeFloorOfficeSeatUtilisationDataModel();
        SeatMap seatMap = seatMapService.getSeatMapById(seatMapId);

        GroupModel seatMapModel = new GroupModel();
        seatMapModel.setId(seatMap.getId());
        seatMapModel.setName(seatMap.getObjectName());
        seatMapModel.setCode(seatMap.getCode());
        officeFloorOfficeSeatUtilisationDataModel.setOfficeFloor(seatMapModel);

        Integer occupancyCount = 0;

        List<Seat> seats = seatRepository.findOfficeOnesBySeatMapId(seatMapId);
        for (Seat seat :
                seats) {
            if (seat.getActiveSeatAllocations().size() > 0) {
                occupancyCount++;
            }
        }

        officeFloorOfficeSeatUtilisationDataModel.setInventoryCount(seats.size());
        officeFloorOfficeSeatUtilisationDataModel.setOccupancyCount(occupancyCount);
        return officeFloorOfficeSeatUtilisationDataModel;
    }

    public BuildingOfficeSeatUtilisationDataModel retrieveBuildingOfficeSeatsUtilisationData(String officeId) {

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(officeId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
            throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
        }

        try {
            BuildingOfficeSeatUtilisationDataModel buildingOfficeSeatUtilisationDataModel = new BuildingOfficeSeatUtilisationDataModel();
            Office office = officeService.getOfficeEntityByUuid(officeId);

            GroupModel officeModel = new GroupModel();
            officeModel.setId(office.getId());
            officeModel.setName(office.getObjectName());
            officeModel.setCode(office.getCode());
            buildingOfficeSeatUtilisationDataModel.setOffice(officeModel);

            Integer inventoryCount = 0;
            Integer unusedCount = 0;

            List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(officeId);

            for (SeatMap seatMap :
                    seatMaps) {
                OfficeFloorOfficeSeatUtilisationDataModel officeFloorOfficeSeatUtilisationDataModel = retrieveOfficeFloorOfficeSeatsUtilisationData(seatMap.getId(), false);
                buildingOfficeSeatUtilisationDataModel.getOfficeFloorOfficeSeatUtilisationDataModels().add(officeFloorOfficeSeatUtilisationDataModel);
            }
            return buildingOfficeSeatUtilisationDataModel;
        } catch (Exception ex) {
            throw new OfficeNotFoundException(ex.getMessage());
        }


    }
}
