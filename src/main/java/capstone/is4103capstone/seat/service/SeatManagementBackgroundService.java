package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.*;
import capstone.is4103capstone.seat.repository.*;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import capstone.is4103capstone.util.exception.ScheduleClashException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Scope("singleton")
@Component
// @Service
public class SeatManagementBackgroundService {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private SeatAllocationInactivationLogRepository seatAllocationInactivationLogRepository;
    @Autowired
    private SeatUtilisationLogRepository seatUtilisationLogRepository;
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


    public SeatManagementBackgroundService() {

    }


    // ------------------------ Programmatic timers for system release one presentation only ------------------------

    public Runnable runInactivateAllocations() {

        return new Runnable() {

            public void run() {
                System.out.println("******************** Seat Management Background Service: inactivate allocations at " + new Date().toString() + " ********************");
                // List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getLogsByCurrentTime(new Date());
                List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getUndoneLogs();
                for (SeatAllocationInactivationLog allocationToInactivate :
                        allocationsToInactivate) {
                    System.out.println("********** allocation ID: " + allocationToInactivate.getAllocation_id() + " **********");
                    System.out.println("********** inactivate time: " + allocationToInactivate.getInactivate_time().toString() + " **********");
                    System.out.println("********** now: " + new Date().toString() + " **********");
                    if (allocationToInactivate.getInactivate_time().before(new Date())) {
                        Optional<Seat> optionalSeat = seatRepository.findByActiveSeatAllocationId(allocationToInactivate.getAllocation_id());
                        if (optionalSeat.isPresent()) {
                            Seat seat = optionalSeat.get();
                            ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
                            while(iterator.hasNext()) {
                                SeatAllocation activeAllocation = iterator.next();
                                if (activeAllocation.getId().equals(allocationToInactivate.getAllocation_id())) {
                                    System.out.println("********** inactivate seat allocation: " + activeAllocation.getId() + " **********");
                                    activeAllocation.setActive(false);
                                    allocationToInactivate.setDone(true);
                                    allocationToInactivate.setCompletion_time(new Date());
                                    seat.getInactiveSeatAllocations().add(activeAllocation);
                                    iterator.remove();
                                    seatAllocationRepository.save(activeAllocation);
                                    seatAllocationInactivationLogRepository.save(allocationToInactivate);
                                }
                            }
                            seat.setType(SeatTypeEnum.HOTDESK);
                            seat.setCurrentOccupancy(null);
                            seatRepository.save(seat);
                        }
                    }
                }
            }
        };
    }

    public Runnable runUpdateCurrentOccupancy() {

        return new Runnable() {

            public void run() {
                System.out.println("******************** Seat Management Background Service: update current occupancy ********************");
                List<Seat> seats = seatRepository.findAllUndeleted();
                for (Seat seat :
                        seats) {
                    System.out.println("********** checking seat: " + seat.getId() + " **********");
                    List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
                    System.out.println("********** number of active allocations: " + activeAllocations.size() + " **********");
                    Collections.sort(activeAllocations);
                    for (SeatAllocation activeAllocation :
                            activeAllocations) {
                        System.out.println("***** checking allocation: " + activeAllocation.getId() + " *****");
                        if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                            if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                                    (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                                seat.setCurrentOccupancy(activeAllocation);
                                seat.setType(SeatTypeEnum.FIXED);
                                seatRepository.save(seat);
                                break;
                            }
                        } else { // Shared seat allocation
                            if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0) {
                                if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                                    if (activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                        seat.setCurrentOccupancy(activeAllocation);
                                        seat.setType(SeatTypeEnum.SHARED);
                                        seatRepository.save(seat);
                                        break;
                                    }
                                } else if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                                    if (activeAllocation.getSchedule().getRecurringWeekdays().contains(LocalDateTime.now().getDayOfWeek())) {
                                        if (activeAllocation.getSchedule().getRecurringStartTime() == null ||
                                                activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                            seat.setCurrentOccupancy(activeAllocation);
                                            seat.setType(SeatTypeEnum.SHARED);
                                            seatRepository.save(seat);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }


    // ------------------------ Automatic timers (@scheduled annotation commented out for system release one presentation only) ------------------------

    // The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday.
    // Month and weekday names can be given as the first three letters of the English names.
    // Example patterns:
    // "0 0 * * * *" = the top of every hour of every day.
    // "*/10 * * * * *" = every ten seconds.
    // "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    // "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
    // "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
    // "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    // "0 0 0 25 12 ?" = every Christmas Day at midnight
    // Generation tool at: https://www.freeformatter.com/cron-expression-generator-quartz.html
    // @Scheduled(cron = "*/5 * 9-18 * * MON-FRI")
    private void inactivateAllocations() {

        System.out.println("******************** Seat Management Background Service: inactivate allocations at " + new Date().toString() + " ********************");
        List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getUndoneLogs();
        for (SeatAllocationInactivationLog allocationToInactivate :
                allocationsToInactivate) {
            System.out.println("********** allocation ID: " + allocationToInactivate.getAllocation_id() + " **********");
            System.out.println("********** inactivate time: " + allocationToInactivate.getInactivate_time().toString() + " **********");
            System.out.println("********** now: " + new Date().toString() + " **********");
            if (allocationToInactivate.getInactivate_time().before(new Date())) {
                Optional<Seat> optionalSeat = seatRepository.findByActiveSeatAllocationId(allocationToInactivate.getAllocation_id());
                if (optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();
                    ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
                    while(iterator.hasNext()) {
                        SeatAllocation activeAllocation = iterator.next();
                        if (activeAllocation.getId().equals(allocationToInactivate.getAllocation_id())) {
                            System.out.println("********** inactivate seat allocation: " + activeAllocation.getId() + " **********");
                            activeAllocation.setActive(false);
                            allocationToInactivate.setDone(true);
                            allocationToInactivate.setCompletion_time(new Date());
                            seat.getInactiveSeatAllocations().add(activeAllocation);
                            if (seat.getCurrentOccupancy() != null && seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) {
                                seat.setCurrentOccupancy(null);
                                seat.setLastOccupancy(activeAllocation);
                            }
                            iterator.remove();
                            seatAllocationRepository.save(activeAllocation);
                            seatAllocationInactivationLogRepository.save(allocationToInactivate);
                        }
                    }
                    if (!seat.getType().equals(SeatTypeEnum.HOTDESK)) {
                        seat.setType(SeatTypeEnum.FIXED);
                    }
                    seatRepository.save(seat);
                }
            }
        }
    }

    // @Scheduled(cron = "*/5 * 9-18 * * MON-FRI")
    private void updateCurrentOccupancy() {

        System.out.println("******************** Seat Management Background Service: update current occupancy ********************");
        List<Seat> seats = seatRepository.findAllUndeleted();
        for (Seat seat :
                seats) {
            System.out.println("********** checking seat: " + seat.getId() + " **********");
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            System.out.println("********** number of active allocations: " + activeAllocations.size() + " **********");
            Collections.sort(activeAllocations);
            for (SeatAllocation activeAllocation :
                    activeAllocations) {
                System.out.println("***** checking allocation: " + activeAllocation.getId() + " *****");
                if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                            (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                        if (seat.getCurrentOccupancy() != null) {
                            seat.setLastOccupancy(seat.getCurrentOccupancy());
                        }
                        seat.setCurrentOccupancy(activeAllocation);
                        seat.setType(SeatTypeEnum.FIXED);
                        seatRepository.save(seat);
                        break;
                    }
                } else if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.HOTDESK) {
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                            (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                        if (seat.getCurrentOccupancy() != null) {
                            seat.setLastOccupancy(seat.getCurrentOccupancy());
                        }
                        seat.setCurrentOccupancy(activeAllocation);
                        seat.setType(SeatTypeEnum.HOTDESK);
                        seatRepository.save(seat);
                        break;
                    }
                } else if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.SHARED) { // Shared seat allocation
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0) {
                        if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                            if (activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                if (seat.getCurrentOccupancy() != null) {
                                    seat.setLastOccupancy(seat.getCurrentOccupancy());
                                }
                                seat.setCurrentOccupancy(activeAllocation);
                                seat.setType(SeatTypeEnum.SHARED);
                                seatRepository.save(seat);
                                break;
                            }
                        } else if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                            if (activeAllocation.getSchedule().getRecurringWeekdays().contains(LocalDateTime.now().getDayOfWeek())) {
                                if (activeAllocation.getSchedule().getRecurringStartTime() == null ||
                                        activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                    if (seat.getCurrentOccupancy() != null) {
                                        seat.setLastOccupancy(seat.getCurrentOccupancy());
                                    }
                                    seat.setCurrentOccupancy(activeAllocation);
                                    seat.setType(SeatTypeEnum.SHARED);
                                    seatRepository.save(seat);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // - Past utilisation
    // --- By Office
    // --- By Floor
    // --- By Function
    // --- By Team
    // --- Blocked for use
    // --- Blocked but unused for more than 2 weeks
    // @Scheduled(cron = "0 0 23 * * MON-FRI")
    private void generateDailySeatUtilisationLog() {

        // Team
        List<Team> teams = teamRepository.findUndeletedAll();
        for (Team team :
                teams) {
            generateDailyTeamSeatUtilisationLog(team);
        }

        // Business Unit
        // Directly aggregate its teams' utilisation data to create a new one
        List<BusinessUnit> businessUnits = businessUnitRepository.findAllUndeleted();
        for (BusinessUnit businessUnit :
                businessUnits) {
            generateDailyBusinessUnitSeatUtilisationLog(businessUnit);
        }

        // Company Function
        List<CompanyFunction> companyFunctions = functionRepository.findAllUndeleted();
        for (CompanyFunction function :
                companyFunctions) {
            generateDailyCompanyFunctionSeatUtilisationLog(function);
        }

        // Office and Office floors (seat maps)
        List<Office> offices = officeRepository.findAllUndeleted();
        for (Office office :
                offices) {
            List<SeatMap> seatMaps = seatMapRepository.findOnesAtOffice(office.getId());
            List<SeatUtilisationLog> officeFloorLogs = new ArrayList<>();
            for (SeatMap seatMap :
                    seatMaps) {
                officeFloorLogs.add(generateDailyOfficeFloorSeatUtilisationLog(seatMap));
            }
            generateDailyOfficeSeatUtilisationLog(office, officeFloorLogs);
        }
    }


    private void generateDailyTeamSeatUtilisationLog(Team team) {

        SeatUtilisationLog seatUtilisationLog = new SeatUtilisationLog();
        seatUtilisationLog.setLevelEntityId(team.getId());
        seatUtilisationLog.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatUtilisationLog.setOfficeId(team.getOffice().getId());
        seatUtilisationLog.setHierarchyPath(team.getHierachyPath());

        List<Seat> seatsUnderTeam = seatRepository.findOnesByTeamId(team.getId());

        Date now = new Date();
        Schedule todayPeriod = new Schedule();
        todayPeriod.setStartDateTime(DateHelper.getDateWithoutTimeUsingCalendar(now));
        todayPeriod.setEndDateTime(now);
        Integer inventoryCount = seatsUnderTeam.size();
        Integer occupancyCount = 0;

        // for each seat, check whether it has been used today by checking the current occupancy and the last occupancy
        for (Seat seat :
                seatsUnderTeam) {
            if (seat.getCurrentOccupancy() != null) {
                try {
                    scheduleService.checkClashBetweenTwoGeneralSchedules(todayPeriod, seat.getCurrentOccupancy().getSchedule());
                } catch (ScheduleClashException ex) {
                    occupancyCount++;
                }
            } else if (seat.getLastOccupancy() != null) {
                try {
                    scheduleService.checkClashBetweenTwoGeneralSchedules(todayPeriod, seat.getLastOccupancy().getSchedule());
                } catch (ScheduleClashException ex) {
                    occupancyCount++;
                }
            }
        }

        seatUtilisationLog.setInventoryCount(inventoryCount);
        seatUtilisationLog.setOccupancyCount(occupancyCount);
        seatUtilisationLog.setYear(DateHelper.getYearFromDate(now));
        seatUtilisationLog.setMonth(DateHelper.getMonthFromDate(now));
        seatUtilisationLog.setDayOfMonth(DateHelper.getDayOfMonthFromDate(now));
        seatUtilisationLog.setCreatedTime(now);

        seatUtilisationLogRepository.save(seatUtilisationLog);
    }

    private void generateDailyBusinessUnitSeatUtilisationLog(BusinessUnit businessUnit) {

        HashMap<String, SeatUtilisationLog> map = new HashMap<>();

        Date now = new Date();
        Integer year = DateHelper.getYearFromDate(now);
        Integer month = DateHelper.getMonthFromDate(now);
        Integer dayOfMonth = DateHelper.getDayOfMonthFromDate(now);

        List<Team> teams = teamRepository.findOnesUnderBusinessUnit(businessUnit.getId());
        for (Team team :
             teams) {
            Optional<SeatUtilisationLog> optionalSeatUtilisationLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(team.getId(), year, month, dayOfMonth);
            if (optionalSeatUtilisationLog.isPresent()) {
                SeatUtilisationLog teamSeatUtilisationLog = optionalSeatUtilisationLog.get();
                if (map.containsKey(team.getOffice().getId())) {
                    SeatUtilisationLog businessUnitLog = map.get(team.getOffice().getId());
                    businessUnitLog.setInventoryCount(businessUnitLog.getInventoryCount() + teamSeatUtilisationLog.getInventoryCount());
                    businessUnitLog.setOccupancyCount(businessUnitLog.getOccupancyCount() + teamSeatUtilisationLog.getOccupancyCount());
                } else {
                    SeatUtilisationLog businessUnitLog = new SeatUtilisationLog();
                    businessUnitLog.setLevelEntityId(businessUnit.getId());
                    businessUnitLog.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
                    businessUnitLog.setHierarchyPath(businessUnit.getHierachyPath());
                    businessUnitLog.setOfficeId(team.getOffice().getId());

                    businessUnitLog.setInventoryCount(businessUnitLog.getInventoryCount() + teamSeatUtilisationLog.getInventoryCount());
                    businessUnitLog.setOccupancyCount(businessUnitLog.getOccupancyCount() + teamSeatUtilisationLog.getOccupancyCount());
                    businessUnitLog.setYear(DateHelper.getYearFromDate(now));
                    businessUnitLog.setMonth(DateHelper.getMonthFromDate(now));
                    businessUnitLog.setDayOfMonth(DateHelper.getDayOfMonthFromDate(now));

                    map.put(businessUnitLog.getOfficeId(), businessUnitLog);
                }
            }
        }

        for (SeatUtilisationLog log :
                map.values()) {
            log.setCreatedTime(new Date());
            seatUtilisationLogRepository.save(log);
        }
    }

    private void generateDailyCompanyFunctionSeatUtilisationLog(CompanyFunction companyFunction) {

        HashMap<String, SeatUtilisationLog> map = new HashMap<>();

        Date now = new Date();
        Integer year = DateHelper.getYearFromDate(now);
        Integer month = DateHelper.getMonthFromDate(now);
        Integer dayOfMonth = DateHelper.getDayOfMonthFromDate(now);

        List<BusinessUnit> businessUnits = businessUnitRepository.findOnesUnderCompanyFunction(companyFunction.getId());
        for (BusinessUnit businessUnit :
                businessUnits) {
            Optional<SeatUtilisationLog> optionalSeatUtilisationLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(businessUnit.getId(), year, month, dayOfMonth);
            if (optionalSeatUtilisationLog.isPresent()) {
                SeatUtilisationLog businessUnitLog = optionalSeatUtilisationLog.get();
                if (map.containsKey(businessUnitLog.getOfficeId())) {
                    SeatUtilisationLog functionLog = map.get(businessUnitLog.getOfficeId());
                    functionLog.setInventoryCount(functionLog.getInventoryCount() + businessUnitLog.getInventoryCount());
                    functionLog.setOccupancyCount(functionLog.getOccupancyCount() + businessUnitLog.getOccupancyCount());
                } else {
                    SeatUtilisationLog functionLog = new SeatUtilisationLog();
                    functionLog.setLevelEntityId(companyFunction.getId());
                    functionLog.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
                    functionLog.setHierarchyPath(companyFunction.getHierachyPath());
                    functionLog.setOfficeId(businessUnitLog.getOfficeId());

                    functionLog.setInventoryCount(functionLog.getInventoryCount() + businessUnitLog.getInventoryCount());
                    functionLog.setOccupancyCount(functionLog.getOccupancyCount() + businessUnitLog.getOccupancyCount());
                    functionLog.setYear(DateHelper.getYearFromDate(now));
                    functionLog.setMonth(DateHelper.getMonthFromDate(now));
                    functionLog.setDayOfMonth(DateHelper.getDayOfMonthFromDate(now));

                    map.put(functionLog.getOfficeId(), functionLog);
                }
            }
        }

        for (SeatUtilisationLog log :
                map.values()) {
            log.setCreatedTime(new Date());
            seatUtilisationLogRepository.save(log);
        }
    }

    private SeatUtilisationLog generateDailyOfficeFloorSeatUtilisationLog(SeatMap seatMap) {

        SeatUtilisationLog seatUtilisationLog = new SeatUtilisationLog();
        seatUtilisationLog.setLevelEntityId(seatMap.getId());
        seatUtilisationLog.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
        seatUtilisationLog.setHierarchyPath(seatMap.getHierachyPath());
        seatUtilisationLog.setOfficeId(seatMap.getOffice().getId());

        List<Seat> seatsOnFloor = seatMap.getSeats();

        Date now = new Date();
        Schedule todayPeriod = new Schedule();
        todayPeriod.setStartDateTime(DateHelper.getDateWithoutTimeUsingCalendar(now));
        todayPeriod.setEndDateTime(now);
        Integer inventoryCount = seatsOnFloor.size();
        Integer occupancyCount = 0;

        // for each seat, check whether it has been used today by checking the current occupancy and the last occupancy
        for (Seat seat :
                seatsOnFloor) {
            if (seat.getCurrentOccupancy() != null) {
                try {
                    scheduleService.checkClashBetweenTwoGeneralSchedules(todayPeriod, seat.getCurrentOccupancy().getSchedule());
                } catch (ScheduleClashException ex) {
                    occupancyCount++;
                }
            } else if (seat.getLastOccupancy() != null) {
                try {
                    scheduleService.checkClashBetweenTwoGeneralSchedules(todayPeriod, seat.getLastOccupancy().getSchedule());
                } catch (ScheduleClashException ex) {
                    occupancyCount++;
                }
            }
        }

        seatUtilisationLog.setInventoryCount(inventoryCount);
        seatUtilisationLog.setOccupancyCount(occupancyCount);
        seatUtilisationLog.setYear(DateHelper.getYearFromDate(now));
        seatUtilisationLog.setMonth(DateHelper.getMonthFromDate(now));
        seatUtilisationLog.setDayOfMonth(DateHelper.getDayOfMonthFromDate(now));
        seatUtilisationLog.setCreatedTime(now);

        seatUtilisationLog = seatUtilisationLogRepository.save(seatUtilisationLog);
        return seatUtilisationLog;
    }

    // Aggregate office floor logs
    private void generateDailyOfficeSeatUtilisationLog(Office office, List<SeatUtilisationLog> officeFloorLogs) {

        SeatUtilisationLog seatUtilisationLog = new SeatUtilisationLog();
        seatUtilisationLog.setLevelEntityId(office.getId());
        seatUtilisationLog.setHierarchyType(HierarchyTypeEnum.OFFICE);
        seatUtilisationLog.setHierarchyPath(office.getHierachyPath());
        seatUtilisationLog.setOfficeId(office.getId());

        Date now = new Date();
        Integer year = DateHelper.getYearFromDate(now);
        Integer month = DateHelper.getMonthFromDate(now);
        Integer dayOfMonth = DateHelper.getDayOfMonthFromDate(now);

        Integer inventoryCount = 0;
        Integer occupancyCount = 0;

        for (SeatUtilisationLog officeFloorLog :
                officeFloorLogs) {
            inventoryCount += officeFloorLog.getInventoryCount();
            occupancyCount += officeFloorLog.getOccupancyCount();
        }

        seatUtilisationLog.setInventoryCount(inventoryCount);
        seatUtilisationLog.setOccupancyCount(occupancyCount);
        seatUtilisationLog.setYear(DateHelper.getYearFromDate(now));
        seatUtilisationLog.setMonth(DateHelper.getMonthFromDate(now));
        seatUtilisationLog.setDayOfMonth(DateHelper.getDayOfMonthFromDate(now));
        seatUtilisationLog.setCreatedTime(now);

        seatUtilisationLogRepository.save(seatUtilisationLog);
    }
}
