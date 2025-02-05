package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.seatDataAnalytics.*;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.seat.repository.SeatUtilisationLogRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.InvalidInputException;
import capstone.is4103capstone.util.exception.OfficeNotFoundException;
import capstone.is4103capstone.util.exception.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SeatDataAnalyticsService {

    @Autowired
    private SeatAdminMatchService seatAdminMatchService;
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
    private SeatManagementGeneralService seatManagementGeneralService;

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
    // Office managers only have the right to see the office floors level info
    public SeatUtilisationDataModel retrieveBusinessLevelEntitySeatUtilisationAnalysis(String levelEntityId, String hierarchyType, String officeId,
                                                                                       String startDateString, String endDateString) {

        if (hierarchyType == null || hierarchyType.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: hierarchy type is required.");
        }
        if (levelEntityId == null || levelEntityId.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: business entity information is required.");
        }
        if (officeId == null || officeId.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: office information is required.");
        }
        if (startDateString == null) {
            throw new InvalidInputException("Retrieving analysis data failed: period start date is required.");
        }
        if (endDateString == null) {
            throw new InvalidInputException("Retrieving analysis data failed: period end date is required.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = simpleDateFormat.parse(startDateString);
        } catch (ParseException ex) {
            throw new InvalidInputException("Retrieving analysis data failed: invalid format of the start date.");
        }
        try {
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException ex) {
            throw new InvalidInputException("Retrieving analysis data failed: invalid format of the end date.");
        }

        if (startDate.after(endDate)) {
            throw new InvalidInputException("Retrieving analysis data failed: the start date must be before the end date.");
        }
        if (endDate.after(DateHelper.getDateWithoutTimeUsingCalendar(new Date()))) {
            throw new InvalidInputException("Retrieving analysis data failed: the end date must be a historic time.");
        }

        startDate = DateHelper.getDateWithoutTimeUsingCalendar(startDate);
        endDate = DateHelper.getDaysAfter(DateHelper.getDateWithoutTimeUsingCalendar(endDate), 1);
        SeatUtilisationDataModel seatUtilisationDataModel = new SeatUtilisationDataModel();

        // Check access right
        // Get the list of direct seat admin rights a user has access to;
        // If the business entity required is below the level, grant the default access
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();

        // Get the entity right
        DBEntityTemplate entity = seatManagementGeneralService.validateBusinessEntityWithHierarchyType(hierarchyType, levelEntityId);
        if (entity.getClass().getSimpleName().equals("CompanyFunction")) {
            CompanyFunction companyFunction = (CompanyFunction)entity;
            if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
                seatUtilisationDataModel = retrieveCompanyFunctionSeatUtilisationAnalysis(seatUtilisationDataModel, companyFunction, officeId, startDate, endDate);
            } else {
                throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
            }
        } else if (entity.getClass().getSimpleName().equals("BusinessUnit")) {
            BusinessUnit businessUnit = (BusinessUnit)entity;
            if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
                seatUtilisationDataModel = retrieveBusinessUnitSeatUtilisationAnalysis(seatUtilisationDataModel, businessUnit, officeId, startDate, endDate);
            } else {
                if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(businessUnit.getFunction().getId(),
                        "COMPANY_FUNCTION", currentEmployee.getId())) {
                    seatUtilisationDataModel = retrieveBusinessUnitSeatUtilisationAnalysis(seatUtilisationDataModel, businessUnit, officeId, startDate, endDate);
                } else {
                    throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
                }
            }
        } else if (entity.getClass().getSimpleName().equals("Team")) {
            Team team = (Team)entity;
            if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
                seatUtilisationDataModel = retrieveTeamSeatUtilisationAnalysis(seatUtilisationDataModel, team, startDate, endDate);
            } else {
                if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(team.getBusinessUnit().getId(),
                        "BUSINESS_UNIT", currentEmployee.getId())) {
                    seatUtilisationDataModel = retrieveTeamSeatUtilisationAnalysis(seatUtilisationDataModel, team, startDate, endDate);
                } else {
                    if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(team.getBusinessUnit().getFunction().getId(),
                            "COMPANY", currentEmployee.getId())) {
                        seatUtilisationDataModel = retrieveTeamSeatUtilisationAnalysis(seatUtilisationDataModel, team, startDate, endDate);
                    } else {
                        throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
                    }
                }
            }
        } else if (entity.getClass().getSimpleName().equals("Office")) {
            Office office = (Office)entity;
            if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
                seatUtilisationDataModel = retrieveOfficeSeatUtilisationAnalysis(seatUtilisationDataModel, office, officeId, startDate, endDate);
            } else {
                throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
            }
        } else if (entity.getClass().getSimpleName().equals("SeatMap")) {
            SeatMap seatMap = (SeatMap)entity;
            if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
                seatUtilisationDataModel = retrieveOfficeFloorUtilisationAnalysis(seatUtilisationDataModel, seatMap, startDate, endDate);
            } else {
                if (seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(seatMap.getOffice().getId(),
                        "OFFICE", currentEmployee.getId())) {
                    seatUtilisationDataModel = retrieveOfficeFloorUtilisationAnalysis(seatUtilisationDataModel, seatMap, startDate, endDate);
                } else {
                    throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
                }
            }
        }

        return seatUtilisationDataModel;
    }


    public SeatUtilisationDataModel retrieveTeamSeatUtilisationAnalysis(SeatUtilisationDataModel seatUtilisationDataModel, Team team,
                                                                                   Date startDate, Date endDate) {

        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                team.getId(), startDate, endDate, team.getOffice().getId());

        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());
        teamModel.setCode(team.getCode());

        List<SeatUtilisationLogModel> logs = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatUtilisationLogModel newLog = new SeatUtilisationLogModel();
            newLog.setDate(log.getCreatedTime());
            newLog.setYear(log.getYear());
            newLog.setMonth(log.getMonth());
            newLog.setDayOfMonth(log.getDayOfMonth());
            newLog.setInventoryCount(log.getInventoryCount());
            newLog.setOccupancyCount(log.getOccupancyCount());
            logs.add(newLog);
        }

        seatUtilisationDataModel.setEntity(teamModel);
        seatUtilisationDataModel.setLogs(logs);
        return seatUtilisationDataModel;
    }


    public SeatUtilisationDataModel retrieveBusinessUnitSeatUtilisationAnalysis(SeatUtilisationDataModel seatUtilisationDataModel, BusinessUnit businessUnit,
                                                                        String officeId, Date startDate, Date endDate) {

        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                businessUnit.getId(), startDate, endDate, officeId);

        GroupModel unitModel = new GroupModel();
        unitModel.setId(businessUnit.getId());
        unitModel.setName(businessUnit.getObjectName());
        unitModel.setCode(businessUnit.getCode());

        List<SeatUtilisationLogModel> logs = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatUtilisationLogModel newLog = new SeatUtilisationLogModel();
            newLog.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
            newLog.setYear(log.getYear());
            newLog.setMonth(log.getMonth());
            newLog.setDayOfMonth(log.getDayOfMonth());
            newLog.setInventoryCount(log.getInventoryCount());
            newLog.setOccupancyCount(log.getOccupancyCount());
            logs.add(newLog);
        }

        seatUtilisationDataModel.setEntity(unitModel);
        seatUtilisationDataModel.setLogs(logs);
        // Construct children: teams under the business unit
        List<Team> teams = teamRepository.findOnesUnderBusinessUnitAndOffice(businessUnit.getId(), officeId);
        for (Team team :
                teams) {
            SeatUtilisationDataModel teamModel = new SeatUtilisationDataModel();
            seatUtilisationDataModel.getChildren().add(retrieveTeamSeatUtilisationAnalysis(teamModel, team, startDate, endDate));
        }
        return seatUtilisationDataModel;
    }


    public SeatUtilisationDataModel retrieveCompanyFunctionSeatUtilisationAnalysis(SeatUtilisationDataModel seatUtilisationDataModel, CompanyFunction companyFunction,
                                                                                   String officeId, Date startDate, Date endDate) {

        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                companyFunction.getId(), startDate, endDate, officeId);

        GroupModel functionModel = new GroupModel();
        functionModel.setId(companyFunction.getId());
        functionModel.setName(companyFunction.getObjectName());
        functionModel.setCode(companyFunction.getCode());
        seatUtilisationDataModel.setEntity(functionModel);

        List<SeatUtilisationLogModel> logs = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatUtilisationLogModel newLog = new SeatUtilisationLogModel();
            newLog.setDate(log.getCreatedTime());
            newLog.setYear(log.getYear());
            newLog.setMonth(log.getMonth());
            newLog.setDayOfMonth(log.getDayOfMonth());
            newLog.setInventoryCount(log.getInventoryCount());
            newLog.setOccupancyCount(log.getOccupancyCount());
            logs.add(newLog);
        }
        seatUtilisationDataModel.setLogs(logs);

        // Construct children: business units under the function
        List<BusinessUnit> businessUnits = businessUnitRepository.findOnesUnderCompanyFunction(companyFunction.getId());
        for (BusinessUnit unit :
                businessUnits) {
            SeatUtilisationDataModel childModel = new SeatUtilisationDataModel();
            childModel = retrieveBusinessUnitSeatUtilisationAnalysis(childModel, unit, officeId, startDate, endDate);
            seatUtilisationDataModel.getChildren().add(childModel);
        }
        return seatUtilisationDataModel;
    }

    public SeatUtilisationDataModel retrieveOfficeFloorUtilisationAnalysis(SeatUtilisationDataModel seatUtilisationDataModel, SeatMap seatMap,
                                                                        Date startDate, Date endDate) {

        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                seatMap.getId(), startDate, endDate, seatMap.getOffice().getId());

        GroupModel seatMapModel = new GroupModel();
        seatMapModel.setId(seatMap.getId());
        seatMapModel.setName(seatMap.getObjectName());
        seatMapModel.setCode(seatMap.getCode());

        List<SeatUtilisationLogModel> logs = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatUtilisationLogModel newLog = new SeatUtilisationLogModel();
            newLog.setDate(log.getCreatedTime());
            newLog.setYear(log.getYear());
            newLog.setMonth(log.getMonth());
            newLog.setDayOfMonth(log.getDayOfMonth());
            newLog.setInventoryCount(log.getInventoryCount());
            newLog.setOccupancyCount(log.getOccupancyCount());
            logs.add(newLog);
        }

        seatUtilisationDataModel.setEntity(seatMapModel);
        seatUtilisationDataModel.setLogs(logs);
        return seatUtilisationDataModel;
    }

    public SeatUtilisationDataModel retrieveOfficeSeatUtilisationAnalysis(SeatUtilisationDataModel seatUtilisationDataModel, Office office,
                                                                                String officeId, Date startDate, Date endDate) {

        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                office.getId(), startDate, endDate, officeId);

        GroupModel officeModel = new GroupModel();
        officeModel.setId(office.getId());
        officeModel.setName(office.getObjectName());
        officeModel.setCode(office.getCode());
        seatUtilisationDataModel.setEntity(officeModel);

        List<SeatUtilisationLogModel> logs = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatUtilisationLogModel newLog = new SeatUtilisationLogModel();
            newLog.setDate(log.getCreatedTime());
            newLog.setYear(log.getYear());
            newLog.setMonth(log.getMonth());
            newLog.setDayOfMonth(log.getDayOfMonth());
            newLog.setInventoryCount(log.getInventoryCount());
            newLog.setOccupancyCount(log.getOccupancyCount());
            logs.add(newLog);
        }

        seatUtilisationDataModel.setLogs(logs);

        // Construct children: all office floors
        List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(officeId);
        for (SeatMap seatMap :
                seatMaps) {
            SeatUtilisationDataModel childModel = new SeatUtilisationDataModel();
            childModel = retrieveOfficeFloorUtilisationAnalysis(childModel, seatMap, startDate, endDate);
            seatUtilisationDataModel.getChildren().add(childModel);
        }
        return seatUtilisationDataModel;
    }






    // -------------------------------------- Blocked For Use (Per Office) --------------------------------------

    // Office managers: among all the seats the office has, how many seats each company function occupies -> office floor breakdown
    // Department heads: among all the seats assigned to the company function, how many seats each business unit occupies -> unit breakdown
    // Business unit heads: among all the seats assigned to the business unit, how many seats each team occupies -> team breakdown
    public SeatBlockedForUseDataModel retrieveBusinessLevelEntitySeatBlockedForUseInOneOfficeData(String levelEntityId, String hierarchyType, String officeId,
                                                                                                  String startDateString, String endDateString) {

        if (hierarchyType == null || hierarchyType.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: hierarchy type is required.");
        }
        if (levelEntityId == null || levelEntityId.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: business entity information is required.");
        }
        if (officeId == null || officeId.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: office information is required.");
        }
        if (startDateString == null) {
            throw new InvalidInputException("Retrieving analysis data failed: period start date is required.");
        }
        if (endDateString == null) {
            throw new InvalidInputException("Retrieving analysis data failed: period end date is required.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = simpleDateFormat.parse(startDateString);
        } catch (ParseException ex) {
            throw new InvalidInputException("Retrieving analysis data failed: invalid format of the start date.");
        }
        try {
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException ex) {
            throw new InvalidInputException("Retrieving analysis data failed: invalid format of the end date.");
        }

        if (startDate.after(endDate)) {
            throw new InvalidInputException("Retrieving analysis data failed: the start date must be before the end date.");
        }
        if (endDate.after(DateHelper.getDateWithoutTimeUsingCalendar(new Date()))) {
            throw new InvalidInputException("Retrieving analysis data failed: the end date must be a historic time.");
        }

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        if (!seatAdminMatchService.passCheckOfAdminRightByHierarchyIdLevelAndAdmin(levelEntityId, hierarchyType, currentEmployee.getId())) {
            throw new UnauthorizedActionException("Accessing seat utilisation data failed: you do not have the right to do this action!");
        }

        startDate = DateHelper.getDateWithoutTimeUsingCalendar(startDate);
        endDate = DateHelper.getDaysAfter(DateHelper.getDateWithoutTimeUsingCalendar(endDate), 1);

        SeatBlockedForUseDataModel seatBlockedForUseDataModel = new SeatBlockedForUseDataModel();
        // Recursive process of retrieving seat blocking information
        seatBlockedForUseDataModel = aggregateOfficeBusinessLevelEntitySeatBlockedForUseData(seatBlockedForUseDataModel, hierarchyType, levelEntityId, officeId,
                startDate, endDate, false);

        return seatBlockedForUseDataModel;
    }

    // Recursive retrieval to generate the hierarchy tree -> e.g., begin with the team and then add up for aggregation
    // The model returned is the parent model with newly child added in
    // For office and office floors, occupancy is aggregated, while for other entities it's the inventory amount.
    private SeatBlockedForUseDataModel aggregateOfficeBusinessLevelEntitySeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
                                                                                               String hierarchyType, String levelEntityId,
                                                                                               String officeId, Date startDate, Date endDate,
                                                                                               boolean constructNew) {
        // Check the entity level type
        if (hierarchyType.equals(HierarchyTypeEnum.OFFICE.toString())) {
            return aggregateOfficeSeatBlockedForUseData(seatBlockedForUseDataModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.OFFICE_FLOOR.toString())) {
            return aggregateOfficeFloorSeatBlockedForUseData(seatBlockedForUseDataModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.COMPANY_FUNCTION.toString())) {
            return aggregateCompanyFunctionSeatBlockedForUseData(seatBlockedForUseDataModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.BUSINESS_UNIT.toString())) {
            return aggregateBusinessUnitSeatBlockedForUseData(seatBlockedForUseDataModel, levelEntityId, officeId, startDate, endDate);
        } else if (hierarchyType.equals(HierarchyTypeEnum.TEAM.toString())) {
            return aggregateTeamSeatBlockedForUseData(seatBlockedForUseDataModel, levelEntityId, officeId, startDate, endDate);
        } else {
            throw new InvalidInputException("Retrieving Data failed: invalid hierarchy type.");
        }
    }


    private SeatBlockedForUseDataModel aggregateTeamSeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
                                                                          String levelEntityId, String officeId, Date startDate, Date endDate) {
        Team team = teamService.retrieveTeamById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());
        teamModel.setCode(team.getCode());

        SeatBlockedForUseSingleDayGroupModel seatBlockedForUseSingleDayGroupModel = new SeatBlockedForUseSingleDayGroupModel();
        List<SeatBlockedForUseSingleDayModel> seatBlockedForUseSingleDayModels = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatBlockedForUseSingleDayModel seatBlockedForUseSingleDayModel = new SeatBlockedForUseSingleDayModel();
            seatBlockedForUseSingleDayModel.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
            seatBlockedForUseSingleDayModel.setNumOfSeats(log.getInventoryCount());
            seatBlockedForUseSingleDayModels.add(seatBlockedForUseSingleDayModel);
        }
        seatBlockedForUseSingleDayGroupModel.setUsageData(seatBlockedForUseSingleDayModels);

        seatBlockedForUseDataModel.setEntity(teamModel);
        seatBlockedForUseDataModel.setSeatBlockedCountWithDate(seatBlockedForUseSingleDayGroupModel);
        return seatBlockedForUseDataModel;
    }

    private SeatBlockedForUseDataModel aggregateBusinessUnitSeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
                                                                                  String levelEntityId, String officeId, Date startDate, Date endDate) {
        BusinessUnit businessUnit = businessUnitService.retrieveBusinessUnitById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel unitModel = new GroupModel();
        unitModel.setId(businessUnit.getId());
        unitModel.setName(businessUnit.getObjectName());
        unitModel.setCode(businessUnit.getCode());

        SeatBlockedForUseSingleDayGroupModel seatBlockedForUseSingleDayGroupModel = new SeatBlockedForUseSingleDayGroupModel();
        List<SeatBlockedForUseSingleDayModel> seatBlockedForUseSingleDayModels = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatBlockedForUseSingleDayModel seatBlockedForUseSingleDayModel = new SeatBlockedForUseSingleDayModel();
            seatBlockedForUseSingleDayModel.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
            seatBlockedForUseSingleDayModel.setNumOfSeats(log.getInventoryCount());
            seatBlockedForUseSingleDayModels.add(seatBlockedForUseSingleDayModel);
        }
        seatBlockedForUseSingleDayGroupModel.setUsageData(seatBlockedForUseSingleDayModels);

        seatBlockedForUseDataModel.setEntity(unitModel);
        seatBlockedForUseDataModel.setSeatBlockedCountWithDate(seatBlockedForUseSingleDayGroupModel);
        // Construct children: teams under the business unit
        List<Team> teams = teamRepository.findOnesUnderBusinessUnit(levelEntityId);
        for (Team team :
                teams) {
            SeatBlockedForUseDataModel teamDataModel = new SeatBlockedForUseDataModel();
            seatBlockedForUseDataModel.getChildren().add(aggregateTeamSeatBlockedForUseData(teamDataModel, team.getId(),
                    officeId, startDate, endDate));
        }
        return seatBlockedForUseDataModel;
    }

    private SeatBlockedForUseDataModel aggregateCompanyFunctionSeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
                                                                                     String levelEntityId, String officeId, Date startDate, Date endDate) {
        CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel functionModel = new GroupModel();
        functionModel.setId(companyFunction.getId());
        functionModel.setName(companyFunction.getObjectName());
        functionModel.setCode(companyFunction.getCode());
        seatBlockedForUseDataModel.setEntity(functionModel);

        SeatBlockedForUseSingleDayGroupModel seatBlockedForUseSingleDayGroupModel = new SeatBlockedForUseSingleDayGroupModel();
        List<SeatBlockedForUseSingleDayModel> seatBlockedForUseSingleDayModels = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatBlockedForUseSingleDayModel seatBlockedForUseSingleDayModel = new SeatBlockedForUseSingleDayModel();
            seatBlockedForUseSingleDayModel.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
            seatBlockedForUseSingleDayModel.setNumOfSeats(log.getInventoryCount());
            seatBlockedForUseSingleDayModels.add(seatBlockedForUseSingleDayModel);
        }
        seatBlockedForUseSingleDayGroupModel.setUsageData(seatBlockedForUseSingleDayModels);
        seatBlockedForUseDataModel.setSeatBlockedCountWithDate(seatBlockedForUseSingleDayGroupModel);

        // Construct children: business units under the function
        List<BusinessUnit> businessUnits = businessUnitRepository.findOnesUnderCompanyFunction(levelEntityId);
        for (BusinessUnit unit :
                businessUnits) {
            SeatBlockedForUseDataModel childModel = new SeatBlockedForUseDataModel();
            childModel = aggregateBusinessUnitSeatBlockedForUseData(childModel, unit.getId(), officeId, startDate, endDate);
            seatBlockedForUseDataModel.getChildren().add(childModel);
        }
        return seatBlockedForUseDataModel;
    }

    private SeatBlockedForUseDataModel aggregateOfficeFloorSeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
                                                                                 String levelEntityId, String officeId,
                                                                                 Date startDate, Date endDate) {
        SeatMap seatMap = seatMapService.getSeatMapById(levelEntityId);
        List<SeatUtilisationLog> seatUtilisationLogs = seatUtilisationLogRepository.findOnesByBusinessEntityIdDuringPeriodAndOffice(
                levelEntityId, startDate, endDate, officeId);

        GroupModel seatMapModel = new GroupModel();
        seatMapModel.setId(seatMap.getId());
        seatMapModel.setName(seatMap.getObjectName());
        seatMapModel.setCode(seatMap.getCode());

        SeatBlockedForUseSingleDayGroupModel seatBlockedForUseSingleDayGroupModel = new SeatBlockedForUseSingleDayGroupModel();
        List<SeatBlockedForUseSingleDayModel> seatBlockedForUseSingleDayModels = new ArrayList<>();
        for (SeatUtilisationLog log :
                seatUtilisationLogs) {
            SeatBlockedForUseSingleDayModel seatBlockedForUseSingleDayModel = new SeatBlockedForUseSingleDayModel();
            seatBlockedForUseSingleDayModel.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
            seatBlockedForUseSingleDayModel.setNumOfSeats(log.getInventoryCount());
            seatBlockedForUseSingleDayModels.add(seatBlockedForUseSingleDayModel);
        }
        seatBlockedForUseSingleDayGroupModel.setUsageData(seatBlockedForUseSingleDayModels);

        seatBlockedForUseDataModel.setEntity(seatMapModel);
        seatBlockedForUseDataModel.setSeatBlockedCountWithDate(seatBlockedForUseSingleDayGroupModel);

        return seatBlockedForUseDataModel;
    }

    private SeatBlockedForUseDataModel aggregateOfficeSeatBlockedForUseData(SeatBlockedForUseDataModel seatBlockedForUseDataModel,
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
            seatBlockedForUseDataModel.setEntity(officeModel);

            SeatBlockedForUseSingleDayGroupModel seatBlockedForUseSingleDayGroupModel = new SeatBlockedForUseSingleDayGroupModel();
            List<SeatBlockedForUseSingleDayModel> seatBlockedForUseSingleDayModels = new ArrayList<>();
            for (SeatUtilisationLog log :
                    seatUtilisationLogs) {
                System.out.println("******************** log created time: " + log.getCreatedTime().toString() + " ********************");
                SeatBlockedForUseSingleDayModel seatBlockedForUseSingleDayModel = new SeatBlockedForUseSingleDayModel();
                seatBlockedForUseSingleDayModel.setDate(DateHelper.getDateWithoutTimeUsingCalendar(log.getCreatedTime()));
                seatBlockedForUseSingleDayModel.setNumOfSeats(log.getInventoryCount());
                seatBlockedForUseSingleDayModels.add(seatBlockedForUseSingleDayModel);
            }
            seatBlockedForUseSingleDayGroupModel.setUsageData(seatBlockedForUseSingleDayModels);
            seatBlockedForUseDataModel.setSeatBlockedCountWithDate(seatBlockedForUseSingleDayGroupModel);

            // Construct children: business units under the function
            List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(levelEntityId);
            for (SeatMap map :
                    seatMaps) {
                SeatBlockedForUseDataModel childModel = new SeatBlockedForUseDataModel();
                childModel = aggregateOfficeFloorSeatBlockedForUseData(childModel, map.getId(), officeId, startDate, endDate);
                seatBlockedForUseDataModel.getChildren().add(childModel);
            }
            return seatBlockedForUseDataModel;
        } catch (Exception ex) {
            throw new OfficeNotFoundException(ex.getMessage());
        }
    }






    // -------------------------------------- Blocked But Unused (Per Office) For > 2 Weeks --------------------------------------

    public SeatBlockedUnusedDataModel retrieveBusinessLevelEntitySeatBlockedButUnusedData(String hierarchyType, String levelEntityId, String officeId) {

        if (hierarchyType == null || hierarchyType.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: hierarchy type is required.");
        }
        if (levelEntityId == null || levelEntityId.trim().length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: business entity information is required.");
        }
        if (officeId == null || officeId.length() == 0) {
            throw new InvalidInputException("Retrieving analysis data failed: office information is required.");
        }

        // Check access right
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        SeatAdminMatch seatAdminMatch = seatAdminMatchService.retrieveMatchByHierarchyIdAndAdminId(levelEntityId, currentEmployee.getId());
        if (!seatAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
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



    // -------------------------------------- Office: Used & Empty --------------------------------------

    public OfficeFloorOfficeSeatUtilisationDataModel retrieveOfficeFloorOfficeSeatsUtilisationData(String seatMapId, boolean needAccessRightChecking) {

        if (needAccessRightChecking) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            SeatAdminMatch seatAdminMatch = seatAdminMatchService.retrieveMatchByHierarchyIdAndAdminId(seatMapId, currentEmployee.getId());
            if (!seatAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
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
        SeatAdminMatch seatAdminMatch = seatAdminMatchService.retrieveMatchByHierarchyIdAndAdminId(officeId, currentEmployee.getId());
        if (!seatAdminMatch.getSeatAdmin().getId().equals(currentEmployee.getId())) {
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






    // -------------------------------------- Helper method --------------------------------------


}
