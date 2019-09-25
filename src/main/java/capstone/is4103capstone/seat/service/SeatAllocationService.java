package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.SeatAllocationModelForFunction;
import capstone.is4103capstone.seat.model.SeatAllocationModelForTeam;
import capstone.is4103capstone.seat.model.SeatModelForAllocation;
import capstone.is4103capstone.seat.repository.ScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import capstone.is4103capstone.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
public class SeatAllocationService {

    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SeatService seatService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;

    // -----------------------------------------------Assumptions-----------------------------------------------
    // 1. the booking period is 1 year, meaning that the start date of the seat allocation cannot be later than 1 year from the current date.
    // 2. User's right to edit the allocation information has been done at the controller level.

    public void allocateSeatsToFunction(SeatAllocationModelForFunction seatAllocationModelForFunction) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForFunction.getSeats(), true);
        CompanyFunction function = validateFunctionInformation(seatAllocationModelForFunction.getFunction().getId(), seats);

        for (Seat seat :
                seats) {
            seat.setFunctionAssigned(function);
            seatRepository.save(seat);
        }

    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a function when it is not assigned to a team / does not have any active employee allocation.
    public void deallocateSeatsFromFunction(List<SeatModelForAllocation> seatModels) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(seatModels, true);

        for (Seat seat :
                seats) {
            seat.setFunctionAssigned(null);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. Only when a seat has been allocated to a particular function can it be allocated to a team under that function
    public void allocateSeatsToTeam(SeatAllocationModelForTeam seatAllocationModelForTeam) {
        // Check whether the seats have already been allocated to the function of the team required
        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForTeam.getSeats(),false);
        Team team = validateTeamInformation(seatAllocationModelForTeam.getTeam().getId(), seats);

        for (Seat seat :
                seats) {
            seat.setTeamAssigned(team);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a team when it does not have any active employee allocation.
    public void deallocateSeatsFromTeam(List<SeatModelForAllocation> seatModels) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(seatModels, false);

        for (Seat seat :
                seats) {
            seat.setTeamAssigned(null);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. A seat can only be assigned as a hot desk when it does not have any active employee allocation.
    // 2. A seat does not have to be pre-assigned to a function/team to become a hot desk.
    public void assignHotDesk(SeatModelForAllocation seatModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatModel.getId());
            if (seat.getActiveSeatAllocations().size() > 0) {
                throw new SeatAllocationException("Assigning hot desk failed: the seat " + seat.getCode() + " currently has active employee allocation!");
            }
            seat.setType(SeatTypeEnum.HOTDESK);
            seatRepository.save(seat);
        } catch (SeatNotFoundException ex) {
            throw new SeatAllocationException("Assigning hot desk failed: " + ex.getMessage());
        }
    }


    // Pre-conditions:
    // 1. A seat can only be assigned as a fixed seat to a permanent employee when it does not have any currently active employee allocation
    //      whose allocation schedule clashes with the start date of the required allocation.
    // 2. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    // Post-condition:
    // 1. The resulted allocation schedule won't have an end date.
    public void assignFixedSeatToPermanentEmployee(SeatModelForAllocation seatModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatModel.getId());
            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning fixed seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatModel.getEmployee().getId());
            Team team = teamService.retrieveTeamById(seatModel.getTeamAssigned().getId());
            if (!team.getMembers().contains(employee)) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatModel.getSchedule().getStartDateTime();
            validateOccupancyStartDateTime(startDateTime);

            // Check whether there is any clash of allocation schedule
            // For both fixed & shared seats allocation:
            // - allocation with no occupancy end date: directly reject
            // - allocation with an occupancy end date: compare the end date of this one and the start date of the new one
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                Schedule allocationSchedule = seatAllocation.getSchedule();
                if (seatAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                    if (allocationSchedule.getEndDateTime() == null) {
                        throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another fixed seat allocation " +
                                "(for a permanent employee)!");
                    } else {
                        if (allocationSchedule.getEndDateTime().after(startDateTime)) {
                            throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another fixed seat allocation " +
                                    "(for a temporary employee)!");
                        }
                    }
                } else {
                    if (allocationSchedule.getEndDateTime() == null) {
                        throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another shared seat allocation!");
                    } else {
                        if (allocationSchedule.getEndDateTime().after(startDateTime)) {
                            throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another shared seat allocation " +
                                    "(for a working-from-home employee)!");
                        }
                    }
                }
            }

            Schedule allocationSchedule = new Schedule();
            allocationSchedule.setStartDateTime(startDateTime);
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.FIXED);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(allocationSchedule);
            seat.getActiveSeatAllocations().add(newSeatAllocation);

            scheduleRepository.save(allocationSchedule);
            seatAllocationRepository.save(newSeatAllocation);
            seatRepository.save(seat);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
        }
    }



    // Pre-conditions:
    // 1. A seat can only be assigned as a fixed seat to a temporary employee when it does not have any allocation schedule that clashes with the schedule set
    //      in the required allocation. This means that the checking of schedule clashes is not done linked with the employee's working schedule, but with
    //      what's finally passed in the ScheduleModel of SeatModelForAllocation. In reality, the info in ScheduleModel is actually decided based on the
    //      employee's working schedule which was passed to the front-end when the user sets the allocation schedule for this new allocation.
    // 2. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    // 3. The allocation schedule must have an end date.
    public void assignFixedSeatToTemporaryEmployee(SeatModelForAllocation seatModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatModel.getId());

            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning fixed seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatModel.getEmployee().getId());
            Team team = teamService.retrieveTeamById(seatModel.getTeamAssigned().getId());
            if (!team.getMembers().contains(employee)) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatModel.getSchedule().getStartDateTime();
            validateOccupancyStartDateTime(startDateTime);
            Date endDateTime = seatModel.getSchedule().getEndDateTime();
            if (endDateTime == null || startDateTime.after(endDateTime)) {
                throw new SeatAllocationException("Assigning fixed seat failed: invalid end date of the seat allocation!");
            }

            // Check whether there is any clash of allocation schedule
            // - allocation with no occupancy end date: compare the start date of this one and the end date of the new one
            // - allocation with an occupancy end date: compare the whole time frame
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                Schedule allocationSchedule = seatAllocation.getSchedule();
                if (allocationSchedule.getEndDateTime() == null) {
                    if (allocationSchedule.getStartDateTime().before(endDateTime)) {
                        throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another seat allocation of " +
                                 seatAllocation.getAllocationType().toString() + " type!");
                    }
                } else {
                    if (startDateTime.before(allocationSchedule.getStartDateTime())) {
                        if (!endDateTime.before(allocationSchedule.getStartDateTime())) {
                            throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another seat allocation of " +
                                    seatAllocation.getAllocationType().toString() + " type!");
                        }
                    }
                    if (startDateTime.equals(allocationSchedule.getStartDateTime())) {
                        throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another seat allocation of " +
                                seatAllocation.getAllocationType().toString() + " type!");
                    }
                    if (startDateTime.after(allocationSchedule.getStartDateTime()) && startDateTime.before(allocationSchedule.getEndDateTime())) {
                        throw new SeatAllocationException("Assigning fixed seat failed: allocation schedule clash with another seat allocation of " +
                                seatAllocation.getAllocationType().toString() + " type!");
                    }
                }
            }

            // Create a new seat allocation between the seat and the employee
            Schedule allocationSchedule = new Schedule();
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.FIXED);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(allocationSchedule);
            seat.getActiveSeatAllocations().add(newSeatAllocation);

            scheduleRepository.save(allocationSchedule);
            seatAllocationRepository.save(newSeatAllocation);
            seatRepository.save(seat);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
        }
    }


    // Pre-conditions:
    // 1. A seat can only be assigned as a shared seat to an employee when it does not have any schedule clash with other active allocations.
    // 2. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    public void assignSharedSeatToEmployee(SeatModelForAllocation seatModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatModel.getId());

            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatModel.getEmployee().getId());
            Team team = teamService.retrieveTeamById(seatModel.getTeamAssigned().getId());
            if (!team.getMembers().contains(employee)) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatModel.getSchedule().getStartDateTime();
            validateOccupancyStartDateTime(startDateTime);
            Date endDateTime = seatModel.getSchedule().getEndDateTime();
            if (endDateTime != null && startDateTime.after(endDateTime)) {
                throw new SeatAllocationException("Assigning shared seat failed: invalid end date of the seat allocation!");
            }

            // Create a new seat allocation between the seat and the employee
            Schedule newAllocationSchedule = new Schedule();
            newAllocationSchedule.setStartDateTime(startDateTime);
            newAllocationSchedule.setEndDateTime(endDateTime);
            newAllocationSchedule.setRecurring(true);
            String recurringBasisString = seatModel.getSchedule().getRecurringBasis();
            if (recurringBasisString != "EveryDay" || recurringBasisString != "EveryWeek" || recurringBasisString != "EveryYear") {
                throw new SeatAllocationException("Assigning shared seat failed: invalid recurring basis!");
            }

            ScheduleRecurringBasisEnum recurringBasisEnum = ScheduleRecurringBasisEnum.valueOf(seatModel.getSchedule().getRecurringBasis());
            newAllocationSchedule.setRecurringBasis(recurringBasisEnum);
            newAllocationSchedule.setRecurringDates(seatModel.getSchedule().getRecurringDates());
            for (Integer weekDay :
                    seatModel.getSchedule().getRecurringWeekdays()) {
                if (weekDay < 1 || weekDay > 7) {
                    throw new SeatAllocationException("Assigning shared seat failed: invalid recurring weekday of " + weekDay + "!");
                }
                newAllocationSchedule.getRecurringWeekdays().add(DayOfWeek.of(weekDay));
            }
            newAllocationSchedule.setRecurringStartTime(seatModel.getSchedule().getRecurringStartTime());
            newAllocationSchedule.setRecurringEndTime(seatModel.getSchedule().getRecurringEndTime());

            // Check whether there is any clash of allocation schedule
            // For fixed seat allocation:
            // - for permanent employees (no occupancy end date): compare the start date of this one and the end date of the new one (if have any)
            // - for temporary employees (with occupancy end date): compare the whole time period
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                Schedule allocationSchedule = seatAllocation.getSchedule();
                if (seatAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {

                    // Two cases: fixed seat for permanent employees and temporary employees
                    if (allocationSchedule.getEndDateTime() == null) { // permanent employees
                        if (endDateTime == null) {
                            // Both have no occupancy end date, reject
                            throw new SeatAllocationException("Assigning shared seat failed due to a schedule clash with another fixed seat allocation " +
                                    "for a permanent employee!");
                        } else {
                            // Compare the end date of the new one with the start date of the existing one
                            if (!endDateTime.before(allocationSchedule.getStartDateTime())) {
                                throw new SeatAllocationException("Assigning shared seat failed due to a schedule clash with another fixed seat allocation " +
                                        "for a permanent employee!");
                            }
                        }
                    } else { // temporary employees
                        if (endDateTime == null) {
                            // Compare the start date of the new one with the end date of the existing one
                            if (!startDateTime.after(allocationSchedule.getEndDateTime())) {
                                throw new SeatAllocationException("Assigning shared seat failed due to a schedule clash with another fixed seat allocation " +
                                        "for a temporary employee!");
                            }
                        } else {
                            // Both have a start date and an end date -> Compare the whole time frame
                            if (startDateTime.before(allocationSchedule.getStartDateTime())) {
                                if (!endDateTime.before(allocationSchedule.getStartDateTime())) {
                                    throw new SeatAllocationException("Assigning shared seat failed: allocation schedule clash with another seat allocation of " +
                                            seatAllocation.getAllocationType().toString() + " type!");
                                }
                            }
                            if (startDateTime.equals(allocationSchedule.getStartDateTime())) {
                                throw new SeatAllocationException("Assigning shared seat failed: allocation schedule clash with another seat allocation of " +
                                        seatAllocation.getAllocationType().toString() + " type!");
                            }
                            if (startDateTime.after(allocationSchedule.getStartDateTime()) && startDateTime.before(allocationSchedule.getEndDateTime())) {
                                throw new SeatAllocationException("Assigning shared seat failed: allocation schedule clash with another seat allocation of " +
                                        seatAllocation.getAllocationType().toString() + " type!");
                            }
                        }
                    }

                } else { // shared seats

                    // The checking can be bypassed if the time frames don't overlap
                    if (allocationSchedule.getEndDateTime() != null) { // the existing allocation has an end date
                        if (endDateTime == null) { // the new allocation does not have an end date, sliding on the time scale
                            if (startDateTime.before(allocationSchedule.getEndDateTime())) {
                                checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                            }
                        } else { // both have an end date
                            if (startDateTime.after(allocationSchedule.getStartDateTime()) && startDateTime.before(allocationSchedule.getEndDateTime())
                             || endDateTime.after(allocationSchedule.getStartDateTime()) && endDateTime.before(allocationSchedule.getEndDateTime())) {
                                checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                            }
                        }
                    } else { // the existing allocation does not have an end date
                        if (endDateTime == null) {
                            checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                        } else {
                            if (endDateTime.after(allocationSchedule.getStartDateTime())) {
                                checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                            }
                        }
                    }
                }
            }

            // Create a new seat allocation between the seat and the employee
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.SHARED);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(newAllocationSchedule);
            seat.getActiveSeatAllocations().add(newSeatAllocation);

            scheduleRepository.save(newAllocationSchedule);
            seatAllocationRepository.save(newSeatAllocation);
            seatRepository.save(seat);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
        }
    }

    // -----------------------------------------------Helper methods-----------------------------------------------

    // Check whether the seats to be allocated:
    // 1. exist
    // 2. belong to the same seat map
    // 3. have already been allocated to any function/team
    private List<Seat> validateSeatsInformationForAllocation(List<SeatModelForAllocation> seatModels, boolean targetFunction) throws SeatAllocationException {

        List<Seat> seats = new ArrayList<>();
        for (SeatModelForAllocation seatModel:
                seatModels) {
            try {
                Seat seat = seatService.retrieveSeatById(seatModel.getId());
                seats.add(seat);
                if (seats.size() > 0 && !seats.get(0).getSeatMap().getId().equals(seat.getSeatMap().getId())) {
                    throw new SeatAllocationException("Allocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetFunction && seat.getFunctionAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to a function!");
                } else if (!targetFunction && seat.getTeamAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to a team!");
                }
            } catch (SeatNotFoundException ex) {
                throw new SeatAllocationException("Assigning hotdesk failed: " + ex.getMessage());
            }
        }

        return seats;
    }


    // Check whether the seats to be allocated:
    // 1. exist
    // 2. belong to the same seat map
    // 3. have already been allocated to any function/team
    private List<Seat> validateSeatsInformationForDeallocation(List<SeatModelForAllocation> seatModels, boolean targetFunction) throws SeatAllocationException {

        List<Seat> seats = new ArrayList<>();
        for (SeatModelForAllocation seatModel:
                seatModels) {
            try {
                Seat seat = seatService.retrieveSeatById(seatModel.getId());
                seats.add(seat);
                if (seats.size() > 0 && !seats.get(0).getSeatMap().getId().equals(seat.getSeatMap().getId())) {
                    throw new SeatAllocationException("Allocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetFunction && seat.getTeamAssigned() != null) {
                    throw new SeatAllocationException("Deallocation of seats failed: some seat has already been allocated to a team!");
                }
                if (seat.getActiveSeatAllocations().size() > 0) {
                    throw new SeatAllocationException("Deallocation of seats failed: seat" + seat.getCode() + " has been allocated to an employee!");
                }
            } catch (SeatNotFoundException ex) {
                throw new SeatAllocationException("Assigning hotdesk failed: " + ex.getMessage());
            }
        }

        return seats;
    }


    // Check whether the office where the seats belong has the function required
    private CompanyFunction validateFunctionInformation(String functionId, List<Seat> seats) throws SeatAllocationException {

        Optional<CompanyFunction> optionalCompanyFunction = functionRepository.findById(functionId);
        if(!optionalCompanyFunction.isPresent()) {
            throw new SeatAllocationException("Allocation of seats failed: function does not exist!");
        }
        CompanyFunction function = optionalCompanyFunction.get();
        Office office = seats.get(0).getSeatMap().getOffice();
        if(!office.getFunctionsCodeInOffice().contains(function.getCode())) {
            throw new SeatAllocationException("Office " + office.getObjectName() + " does not have " + function.getObjectName() + " function!");
        }

        return function;
    }


    // Check whether the office where the seats belong has the function required and the team belongs to that function
    private Team validateTeamInformation(String teamId, List<Seat> seats) throws SeatAllocationException {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(!optionalTeam.isPresent()) {
            throw new SeatAllocationException("Allocation of seats failed: team does not exist!");
        }

        Team team = optionalTeam.get();
        CompanyFunction function = validateFunctionInformation(team.getFunction().getId(), seats);
        // It has been confirmed that the office has the function, need to check whether the seats have already been allocated to the function
        for (Seat seat :
                seats) {
            if (seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Allocation of seats failed: seat must be allocated to the function before being allocated to a team!");
            } else if (!seat.getFunctionAssigned().getCode().equals(function.getCode())) {
                throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to another function!");
            }
        }

        return team;
    }


    // Validate the start date of the allocation.
    private void validateOccupancyStartDateTime(Date startDateTime) throws SeatAllocationException {
        if (startDateTime.compareTo(new Date()) < 0) {
            throw new SeatAllocationException("Assigning seat failed: the start date time of the occupancy cannot be a historical time!");
        }
        GregorianCalendar oneYearLater = new GregorianCalendar();
        oneYearLater.roll(Calendar.YEAR,1);
        Date oneYearLaterDate = oneYearLater.getTime();
        if (startDateTime.after(oneYearLaterDate)) {
            throw new SeatAllocationException("Assigning seat failed: the start date time of the occupancy cannot be later than 1 year " +
                    "after the current date!");
        }
    }


    // Pre-condition:
    // - The two schedules were checked before hand to have overlapping time frame (based on date only)
    private void checkSharedSeatsScheduleClash(Schedule schedule1, Schedule schedule2) throws SeatAllocationException {

        // shared seats -> compare by recurring basis, one by one
        // "EveryDay" (recurringStartTime, recurringEndTime),
        // "EveryWeek" (recurringWeekdays, recurringStartTime, recurringEndTime),
        // "EveryYear" (recurringDate, recurringStartTime, recurringEndTime)

        try {
            if (schedule1.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                checkHourlyClashes(schedule1, schedule2);
            } else if (schedule1.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                if (schedule2.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                    checkHourlyClashes(schedule1, schedule2);
                } else if (schedule2.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                    for (DayOfWeek dayOfWeek :
                            schedule1.getRecurringWeekdays()) {
                        for (DayOfWeek dayOfWeek2 :
                                schedule2.getRecurringWeekdays()) {
                            if (dayOfWeek == dayOfWeek2) {
                                checkHourlyClashes(schedule1, schedule2);
                            }
                        }
                    }
                } else {
                    for (DayOfWeek dayOfWeek :
                            schedule1.getRecurringWeekdays()) {
                        for (Date date :
                                schedule2.getRecurringDates()) {
                            if (dayOfWeek == getDayOfWeekFromDate(date)) {
                                checkHourlyClashes(schedule1, schedule2);
                            }
                        }
                    }
                }
            }
        } catch (ScheduleClashException ex) {
            throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
        }
    }


    private void checkHourlyClashes(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {
        LocalTime startTime1 = schedule1.getRecurringStartTime();
        LocalTime endTime1 = schedule1.getRecurringEndTime();
        LocalTime startTime2 = schedule2.getRecurringStartTime();
        LocalTime endTime2 = schedule2.getRecurringEndTime();

        if (startTime1.compareTo(startTime2) < 0) {
            if (!(endTime1.compareTo(endTime2) < 0)) {
                throw new ScheduleClashException("There is hourly schedule clash!");
            }
        }
        if (startTime1.equals(startTime2)) {
            throw new ScheduleClashException("There is hourly schedule clash!");
        }
        if (startTime1.compareTo(startTime2) > 0 && startTime1.compareTo(endTime2) < 0) {
            throw new ScheduleClashException("There is hourly schedule clash!");
        }
    }


    private DayOfWeek getDayOfWeekFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.of(calendar.DAY_OF_WEEK);
    }
}
