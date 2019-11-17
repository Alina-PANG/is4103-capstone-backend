package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.seat.model.seatFutureDemand.MonthlySeatFutureDemandModelForTeam;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.util.exception.InvalidInputException;
import capstone.is4103capstone.util.exception.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;

@Service
public class SeatFutureDemandService {

    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatAdminMatchService seatAdminMatchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;

    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private SeatAdminMatchRepository seatAdminMatchRepository;


    public List<MonthlySeatFutureDemandModelForTeam> forecastSeatDemandForNext6MonthsByTeam(String teamId) {

        if(teamId == null || teamId.trim().length() ==0) {
            throw new InvalidInputException("Retrieving forecast failed because of invalid input: team ID is missing!");
        }
        Team team = teamService.retrieveTeamById(teamId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        Optional<SeatAdminMatch> optionalSeatAdminMatch = seatAdminMatchRepository.findUndeletedOneByEntityAndAdminId(teamId, currentEmployee.getId());
        if (!optionalSeatAdminMatch.isPresent()) {
            throw new UnauthorizedActionException("Retrieving forecast failed: you do not have the right to create such request!");
        }

        YearMonth thisYearMonth = DateHelper.getYearMonthFromDate(new Date());
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = new ArrayList<>();
        for(int i = 1; i <= 6; i++) {
            thisYearMonth = thisYearMonth.plusMonths(1);
            MonthlySeatFutureDemandModelForTeam monthlySeatFutureDemandModelForTeam = forecastMonthlySeatDemandByTeam(thisYearMonth, teamId);
            monthlySeatFutureDemandModelForTeam.setSeqNum(i);
            monthlySeatForecastModelsForTeam.add(monthlySeatFutureDemandModelForTeam);
        }
        return monthlySeatForecastModelsForTeam;
    }

    public List<MonthlySeatFutureDemandModelForTeam> forecastSeatDemandForNext12MonthsByTeam(String teamId) {

        if(teamId == null || teamId.trim().length() ==0) {
            throw new InvalidInputException("Retrieving forecast failed because of invalid input: team ID is missing!");
        }
        Team team = teamService.retrieveTeamById(teamId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        Optional<SeatAdminMatch> optionalSeatAdminMatch = seatAdminMatchRepository.findUndeletedOneByEntityAndAdminId(teamId, currentEmployee.getId());
        if (!optionalSeatAdminMatch.isPresent()) {
            throw new UnauthorizedActionException("Retrieving forecast failed: you do not have the right to create such request!");
        }

        YearMonth thisYearMonth = DateHelper.getYearMonthFromDate(new Date());
        List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            thisYearMonth = thisYearMonth.plusMonths(1);
            MonthlySeatFutureDemandModelForTeam monthlySeatFutureDemandModelForTeam = forecastMonthlySeatDemandByTeam(thisYearMonth, teamId);
            monthlySeatFutureDemandModelForTeam.setSeqNum(i);
            monthlySeatForecastModelsForTeam.add(monthlySeatFutureDemandModelForTeam);
        }
        return monthlySeatForecastModelsForTeam;
    }

    // Inventory should remain the same across all future year months because allocation of seats to teams takes an immediate effect
    private MonthlySeatFutureDemandModelForTeam forecastMonthlySeatDemandByTeam(YearMonth yearMonth, String teamId) {
        Integer inventoryCount = 0;
        Integer occupancyCount = 0;

        // Retrieve team
        Team team = teamService.retrieveTeamById(teamId);

        List<Seat> seats = seatService.retrieveSeatInventoryByTeamId(teamId);
        inventoryCount = seats.size();
        occupancyCount = countSeatsOccupiedByYearMonth(yearMonth, seats);
        List<Employee> employeesWhoNeedSeats = findEmployeesWhoNeedSeatDuringYearMonthByTeam(yearMonth, team);
        List<Employee> employeesWithUnnecessaryAllocations = findEmployeesWithUnnecessaryAllocationDuringYearMonthByTeam(yearMonth, seats, team.getOffice().getId());

        MonthlySeatFutureDemandModelForTeam monthlySeatFutureDemandModelForTeam = entityModelConversionService.createMonthlyForecastModelForTeam(team, yearMonth,
                inventoryCount, occupancyCount, employeesWhoNeedSeats, employeesWithUnnecessaryAllocations);
        return monthlySeatFutureDemandModelForTeam;
    }

    // Calculate the number of assigned seats:
    // 1. that have active allocations during the year month (no matter which employee it's assigned to [even if the employee is not under the team])
    private Integer countSeatsOccupiedByYearMonth(YearMonth yearMonth, List<Seat> seats) {
        Integer count = 0;
        for (Seat seat :
                seats) {
            if (seatService.isSeatOccupiedDuringYearMonth(seat, yearMonth)) {
                count++;
            }
        }
        return count;
    }

    // Calculate the number of employees under the team who doesn't have a seat allocation during the year month (positive demand count)
    private List<Employee> findEmployeesWhoNeedSeatDuringYearMonthByTeam(YearMonth yearMonth, Team team) {

        List<Employee> employees = team.getMembers();
        List<Employee> employeesWhoNeedSeats = new ArrayList<>();

        // Only keep the employees who will work at the office during the year month period
        ListIterator<Employee> listIterator = employees.listIterator();
        while (listIterator.hasNext()) {
            Employee thisEmployee = listIterator.next();
            if (!scheduleService.hasWorkingScheduleDuringYearMonthAtOfficeByEmployeeId(thisEmployee.getId(), team.getOffice().getId(), yearMonth)) {
                listIterator.remove();
            }
        }

        // Check for each employee whether they have active seat allocations during that year month, if not, increase the count by 1
        // - retrieve all the employee's active allocations and check the year month overlapping
        // - as long as the employee has an active seat allocation, that's fine no matter whether the seat belongs to that team or not
        for (Employee employee :
                employees) {
            List<SeatAllocation> activeSeatAllocations = seatAllocationRepository.findActiveOnesByEmployeeId(employee.getId());
            boolean hasSeatAllocationDuringPeriod = false;
            Collections.sort(activeSeatAllocations);
            for (SeatAllocation activeSeatAllocation :
                    activeSeatAllocations) {
                if (scheduleService.containYearMonth(activeSeatAllocation.getSchedule(), yearMonth)) {
                    if (activeSeatAllocation.getSeat().getSeatMap().getOffice().getId().equals(team.getOffice().getId())) {
                        hasSeatAllocationDuringPeriod = true;
                        break;
                    }
                }
            }
            if (!hasSeatAllocationDuringPeriod) {
                employeesWhoNeedSeats.add(employee);
            }
        }

        return employeesWhoNeedSeats;
    }

    // Calculate the number of employees assigned with seats under the team who will leave during the year month
    private List<Employee> findEmployeesWithUnnecessaryAllocationDuringYearMonthByTeam(YearMonth yearMonth, List<Seat> seats, String officeId) {
        List<Employee> employeesWithUnnecessaryAllocations = new ArrayList<>();

        // Get all employees who have active allocations under the seats during the year month
        for (Seat seat :
                seats) {
            Collections.sort(seat.getActiveSeatAllocations());
            for (SeatAllocation activeSeatAllocation :
                    seat.getActiveSeatAllocations()) {
                if (scheduleService.containYearMonth(activeSeatAllocation.getSchedule(), yearMonth)) {
                    if (!employeesWithUnnecessaryAllocations.contains(activeSeatAllocation.getEmployee())) {
                        employeesWithUnnecessaryAllocations.add(activeSeatAllocation.getEmployee());
                    }
                }
            }
        }

        ListIterator<Employee> listIterator = employeesWithUnnecessaryAllocations.listIterator();
        while (listIterator.hasNext()) {
            Employee thisEmployee = listIterator.next();
            if (scheduleService.hasWorkingScheduleDuringYearMonthAtOfficeByEmployeeId(thisEmployee.getId(), officeId, yearMonth)) {
                listIterator.remove();
            }
        }

        return employeesWithUnnecessaryAllocations;
    }
}
