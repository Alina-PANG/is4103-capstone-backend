package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatAllocationInactivationLog;
import capstone.is4103capstone.seat.model.*;
import capstone.is4103capstone.seat.model.seatAllocation.*;
import capstone.is4103capstone.seat.repository.*;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
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
    private SeatAllocationInactivationLogRepository seatAllocationInactivationLogRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
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



    // ---------------------------------- Unit Level -----------------------------------
    public void allocateSeatsToFunction(SeatAllocationModelForFunction seatAllocationModelForFunction) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForFunction.getSeatIds(), "FUNCTION");
        CompanyFunction function = validateFunctionInformation(seatAllocationModelForFunction.getFunctionId(), seats);

        for (Seat seat :
                seats) {
            seat.setFunctionAssigned(function);
            seatRepository.save(seat);
        }

    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a function when it is not assigned to a team/business unit / does not have any active employee allocation.
    public void deallocateSeatsFromFunction(BulkDeallocationModel bulkDeallocationModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkDeallocationModel.getSeatIds(), "FUNCTION");

        for (Seat seat :
                seats) {
            seat.setFunctionAssigned(null);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. Only when a seat has been allocated to a particular function can it be allocated to a business unit under that function
    public void allocateSeatsToBusinessUnit(SeatAllocationModelForBusinessUnit seatAllocationModelForBusinessUnit) {
        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForBusinessUnit.getSeatIds(), "BUSINESS_UNIT");
        BusinessUnit businessUnit = validateBusinessUnitInformation(seatAllocationModelForBusinessUnit.getBusinessUnitId(), seats);

        for (Seat seat :
                seats) {
            seat.setBusinessUnitAssigned(businessUnit);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a business unit when it is not assigned to a team / does not have any active employee allocation.
    public void deallocateSeatsFromBusinessUnit(BulkDeallocationModel bulkDeallocationModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkDeallocationModel.getSeatIds(), "BUSINESS_UNIT");

        for (Seat seat :
                seats) {
            seat.setBusinessUnitAssigned(null);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. Only when a seat has been allocated to a particular business unit can it be allocated to a team under that business unit
    public void allocateSeatsToTeam(SeatAllocationModelForTeam seatAllocationModelForTeam) {
        // Check whether the seats have already been allocated to the business unit of the team required
        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForTeam.getSeatIds(),"TEAM");
        Team team = validateTeamInformation(seatAllocationModelForTeam.getTeamId(), seats);

        for (Seat seat :
                seats) {
            seat.setTeamAssigned(team);
            seatRepository.save(seat);
        }
    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a team when it does not have any active employee allocation.
    public void deallocateSeatsFromTeam(BulkDeallocationModel bulkDeallocationModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkDeallocationModel.getSeatIds(), "TEAM");

        for (Seat seat :
                seats) {
            seat.setTeamAssigned(null);
            seatRepository.save(seat);
        }
    }



    // ---------------------------------- Employee Level -----------------------------------

    // Assumptions:
    // 1. A hot desk is like a fixed seat to a temporary employee, and it's directly allocated to an individual employee.
    // 2. A seat can only be assigned as a fixed seat to a temporary employee when it does not have any allocation schedule that clashes with the schedule set
    //    in the required allocation.
    // 3. A hot desk is assigned at the floor level, which means a seat does not have to be pre-assigned to a function/team to become a hot desk.
    // 4. The allocation schedule must have an end date.
    // 5. Seats under office cannot be allocated as hot desks.
    public void assignHotDesk(SeatAllocationModelForEmployee seatAllocationModelForEmployee) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatAllocationModelForEmployee.getSeatId());

            if (seat.isUnderOffice()) {
                throw new SeatAllocationException("Assigning hot desk failed: seat under office cannot be allocated as hot desk!");
            }

            if (seatAllocationModelForEmployee.getSchedule().getStartDateTime() == null) {
                throw new SeatAllocationException("Assigning hot desk failed: start date of the seat allocation is required!");
            }

            if (seatAllocationModelForEmployee.getSchedule().getEndDateTime() == null) {
                throw new SeatAllocationException("Assigning hot desk failed: end date of the seat allocation is required!");
            }

            Date startDateTime = seatAllocationModelForEmployee.getSchedule().getStartDateTime();
            System.out.println("********** Start Date Time: " + startDateTime.toString() + " **********");
            validateOccupancyDateTimeUsingToday(startDateTime, true);
            Date endDateTime = seatAllocationModelForEmployee.getSchedule().getEndDateTime();
            validateOccupancyDateTimeUsingToday(endDateTime, false);
            System.out.println("********** End Date Time: " + endDateTime.toString() + " **********");

            if (startDateTime.after(endDateTime)) {
                throw new SeatAllocationException("Assigning hot desk failed: invalid end date of the seat allocation!");
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
                        throw new SeatAllocationException("Assigning hot desk failed: allocation schedule clash with another seat allocation of " +
                                seatAllocation.getAllocationType().toString() + " type!");
                    }
                } else {
                    if (startDateTime.before(allocationSchedule.getStartDateTime())) {
                        if (!endDateTime.before(allocationSchedule.getStartDateTime())) {
                            throw new SeatAllocationException("Assigning hot desk failed: allocation schedule clash with another seat allocation of " +
                                    seatAllocation.getAllocationType().toString() + " type!");
                        }
                    }
                    if (startDateTime.equals(allocationSchedule.getStartDateTime())) {
                        throw new SeatAllocationException("Assigning hot desk failed: allocation schedule clash with another seat allocation of " +
                                seatAllocation.getAllocationType().toString() + " type!");
                    }
                    if (startDateTime.after(allocationSchedule.getStartDateTime()) && startDateTime.before(allocationSchedule.getEndDateTime())) {
                        throw new SeatAllocationException("Assigning hot desk failed: allocation schedule clash with another seat allocation of " +
                                seatAllocation.getAllocationType().toString() + " type!");
                    }
                }
            }


            // Create a new seat allocation between the seat and the employee
            Employee employee = employeeService.retrieveEmployeeById(seatAllocationModelForEmployee.getEmployee().getId());
            Schedule allocationSchedule = new Schedule();

            allocationSchedule.setStartDateTime(startDateTime);
            allocationSchedule.setEndDateTime(endDateTime);
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.HOTDESK);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(allocationSchedule);
            seat.getActiveSeatAllocations().add(newSeatAllocation);

            allocationSchedule = scheduleRepository.save(allocationSchedule);
            newSeatAllocation = seatAllocationRepository.save(newSeatAllocation);
            seatRepository.save(seat);

            SeatAllocationInactivationLog log = new SeatAllocationInactivationLog();
            log.setAllocation_id(newSeatAllocation.getId());
            log.setInactivate_time(allocationSchedule.getEndDateTime());
            seatAllocationInactivationLogRepository.save(log);
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
    public void assignFixedSeatToPermanentEmployee(SeatAllocationModelForEmployee seatAllocationModel) throws SeatAllocationException {
        System.out.println("******************** Assign Fixed Seat To Permanent Employee ********************");
        try {
            Seat seat = seatService.retrieveSeatById(seatAllocationModel.getSeatId());
            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning fixed seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatAllocationModel.getEmployee().getId());
            Team team = seat.getTeamAssigned();
            boolean isTeamMember = false;
            for (Employee member :
                    team.getMembers()) {
                if (member.getId().equals(employee.getId())) {
                    isTeamMember = true;
                }
            }
            if (!isTeamMember) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatAllocationModel.getSchedule().getStartDateTime();
            validateOccupancyDateTimeUsingToday(startDateTime, true);

            // Check whether there is any clash of allocation schedule
            // For both fixed & shared seats allocation:
            // - allocation with no occupancy end date: directly reject
            // - allocation with an occupancy end date: compare the end date of this one and the start date of the new one
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            System.out.println("********** the number of active allocations: " + activeAllocations.size() + " **********");
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                System.out.println("***** seat allocation: " + seatAllocation.getId() + " *****");
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
    public void assignFixedSeatToTemporaryEmployee(SeatAllocationModelForEmployee seatAllocationModel) throws SeatAllocationException {

        System.out.println("******************** Assign Fixed Seat To Temporary Employee, " + new Date().toString() + " ********************");
        try {
            Seat seat = seatService.retrieveSeatById(seatAllocationModel.getSeatId());

            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning fixed seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatAllocationModel.getEmployee().getId());
            Team team = seat.getTeamAssigned();
            boolean isTeamMember = false;
            for (Employee member :
                    team.getMembers()) {
                if (member.getId().equals(employee.getId())) {
                    isTeamMember = true;
                }
            }
            if (!isTeamMember) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatAllocationModel.getSchedule().getStartDateTime();
            System.out.println("********** Start Date Time: " + startDateTime.toString() + " **********");
            validateOccupancyDateTimeUsingToday(startDateTime, true);
            Date endDateTime = seatAllocationModel.getSchedule().getEndDateTime();
            validateOccupancyDateTimeUsingToday(endDateTime, false);
            System.out.println("********** End Date Time: " + endDateTime.toString() + " **********");

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
            allocationSchedule.setStartDateTime(startDateTime);
            allocationSchedule.setEndDateTime(endDateTime);
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.FIXED);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(allocationSchedule);
            seat.getActiveSeatAllocations().add(newSeatAllocation);

            scheduleRepository.save(allocationSchedule);
            seatAllocationRepository.save(newSeatAllocation);
            seatRepository.save(seat);

            SeatAllocationInactivationLog log = new SeatAllocationInactivationLog();
            log.setAllocation_id(newSeatAllocation.getId());
            log.setInactivate_time(allocationSchedule.getEndDateTime());
            seatAllocationInactivationLogRepository.save(log);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
        }
    }


    // Pre-conditions:
    // 1. A seat can only be assigned as a shared seat to an employee when it does not have any schedule clash with other active allocations.
    // 2. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    public void assignSharedSeatToEmployee(SeatAllocationModelForEmployee seatAllocationModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatAllocationModel.getSeatId());

            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(seatAllocationModel.getEmployee().getId());
            Team team = seat.getTeamAssigned();
            boolean isTeamMember = false;
            for (Employee member :
                    team.getMembers()) {
                if (member.getId().equals(employee.getId())) {
                    isTeamMember = true;
                }
            }
            if (!isTeamMember) {
                throw new SeatAllocationException("Employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " is not in team " + team.getCode() + "!");
            }

            Date startDateTime = seatAllocationModel.getSchedule().getStartDateTime();
            validateOccupancyDateTimeUsingToday(startDateTime, true);
            Date endDateTime = seatAllocationModel.getSchedule().getEndDateTime();
            validateOccupancyDateTimeUsingToday(endDateTime, false);
            if (endDateTime != null && startDateTime.after(endDateTime)) {
                throw new SeatAllocationException("Assigning shared seat failed: invalid end date of the seat allocation!");
            }

            // Create a new seat allocation between the seat and the employee
            Schedule newAllocationSchedule = new Schedule();
            newAllocationSchedule.setStartDateTime(startDateTime);
            newAllocationSchedule.setEndDateTime(endDateTime);
            newAllocationSchedule.setRecurring(true);
            String recurringBasisString = seatAllocationModel.getSchedule().getRecurringBasis();
            if (!recurringBasisString.equals("EVERYDAY") && !recurringBasisString.equals("EVERYWEEK")) {
                throw new SeatAllocationException("Assigning shared seat failed: invalid recurring basis!");
            }

            ScheduleRecurringBasisEnum recurringBasisEnum = ScheduleRecurringBasisEnum.valueOf(seatAllocationModel.getSchedule().getRecurringBasis());
            validateScheduleRecurringBasis(recurringBasisEnum, seatAllocationModel.getSchedule());
            newAllocationSchedule.setRecurringBasis(recurringBasisEnum);
            if (seatAllocationModel.getSchedule().getRecurringDates() != null && seatAllocationModel.getSchedule().getRecurringDates().size() > 0) {
                for (Date date :
                        seatAllocationModel.getSchedule().getRecurringDates()) {
                    date = DateHelper.getDateWithoutTimeUsingCalendar(date);
                }
            }
            newAllocationSchedule.setRecurringDates(seatAllocationModel.getSchedule().getRecurringDates());
            if (seatAllocationModel.getSchedule().getRecurringWeekdays() != null && seatAllocationModel.getSchedule().getRecurringWeekdays().size() > 0) {
                for (Integer weekDay :
                        seatAllocationModel.getSchedule().getRecurringWeekdays()) {
                    if (weekDay < 1 || weekDay > 7) {
                        throw new SeatAllocationException("Assigning shared seat failed: invalid recurring weekday of " + weekDay + "!");
                    }
                    newAllocationSchedule.getRecurringWeekdays().add(DayOfWeek.of(weekDay));
                }
            }
            newAllocationSchedule.setRecurringStartTime(seatAllocationModel.getSchedule().getRecurringStartTime());
            newAllocationSchedule.setRecurringEndTime(seatAllocationModel.getSchedule().getRecurringEndTime());

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

            SeatAllocationInactivationLog log = new SeatAllocationInactivationLog();
            log.setAllocation_id(newSeatAllocation.getId());
            log.setInactivate_time(newAllocationSchedule.getEndDateTime());
            seatAllocationInactivationLogRepository.save(log);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
        }
    }



    // ---------------------------------- Other Services -----------------------------------

    public Seat retrieveSeatWithAllocationsBySeatId(String seatId) throws SeatNotFoundException, SeatAllocationException {
        Optional<Seat> optionalSeat = seatRepository.findUndeletedById(seatId);
        if (!optionalSeat.isPresent()) {
            throw new SeatNotFoundException("Seat does not exist!");
        }

        return optionalSeat.get();
    }


    public void deleteAllocationByAllocationId(String allocationId) throws SeatAllocationException {

        System.out.println("******************** Delete Allocation ********************");
        Optional<SeatAllocation> optionalSeatAllocation = seatAllocationRepository.findUndeletedById(allocationId);
        if (!optionalSeatAllocation.isPresent()) {
            throw new SeatAllocationException("Seat allocation with ID " + allocationId + " does not exist!");
        }

        SeatAllocation seatAllocation = optionalSeatAllocation.get();
        System.out.println("********** seat allocation (to be deleted) ID: " + seatAllocation.getId() + " **********");
        Seat seat = seatAllocation.getSeat();
        seatAllocation.setDeleted(true);
        Optional<SeatAllocationInactivationLog> optionalSeatAllocationInactivationLog = seatAllocationInactivationLogRepository.getUncancelledById(allocationId);
        if (optionalSeatAllocationInactivationLog.isPresent()) {
            SeatAllocationInactivationLog seatAllocationInactivationLog = optionalSeatAllocationInactivationLog.get();
            seatAllocationInactivationLog.setCancelled(true);
            seatAllocationInactivationLogRepository.save(seatAllocationInactivationLog);
        }

        System.out.println("-------------------------------------------");
        if (seatAllocation.isActive()) {
            ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
            System.out.println("********** list iterator **********");
            System.out.println("********** original active allocation count: " + seat.getActiveSeatAllocations().size() + " **********");
            while (iterator.hasNext()) {
                SeatAllocation thisOne = iterator.next();
                System.out.println("***** seat allocation (list iterator) ID : " +  thisOne.getId() + " *****");
                if (thisOne.getId().equals(seatAllocation.getId())) {
                    iterator.remove();
                }
            }
            System.out.println("********** final active allocation count: " + seat.getActiveSeatAllocations().size() + " **********");
        }
        seatRepository.save(seat);
        seatAllocationRepository.save(seatAllocation);
    }


    // Soft-delete all active allocations belonging to the employee
    public void deleteAllocationsByEmployeeId(String employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeService.retrieveEmployeeById(employeeId);
        List<SeatAllocation> seatAllocations = seatAllocationRepository.findActiveOnesByEmployeeId(employee.getId());

        for (SeatAllocation seatAllocation :
                seatAllocations) {
            Seat seat = seatAllocation.getSeat();
            seatAllocation.setDeleted(true);
            Optional<SeatAllocationInactivationLog> optionalSeatAllocationInactivationLog = seatAllocationInactivationLogRepository.getUncancelledById(seatAllocation.getId());
            if (optionalSeatAllocationInactivationLog.isPresent()) {
                SeatAllocationInactivationLog seatAllocationInactivationLog = optionalSeatAllocationInactivationLog.get();
                seatAllocationInactivationLog.setCancelled(true);
                seatAllocationInactivationLogRepository.save(seatAllocationInactivationLog);
            }

            if (seatAllocation.isActive()) {
                ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
                while (iterator.hasNext()) {
                    SeatAllocation thisOne = iterator.next();
                    if (thisOne.getId().equals(seatAllocation.getId())) {
                        iterator.remove();
                    }
                }
            }
            seatRepository.save(seat);
            seatAllocationRepository.save(seatAllocation);
        }
    }


    // Soft-delete all active allocations belonging to the seat
    public void deleteAllocationsBySeatId(String seatId) throws SeatNotFoundException {
        Seat seat = seatService.retrieveSeatById(seatId);
        List<SeatAllocation> seatAllocations = seat.getActiveSeatAllocations();

        for (SeatAllocation seatAllocation :
                seatAllocations) {
            seatAllocation.setDeleted(true);
            Optional<SeatAllocationInactivationLog> optionalSeatAllocationInactivationLog = seatAllocationInactivationLogRepository.getUncancelledById(seatAllocation.getId());
            if (optionalSeatAllocationInactivationLog.isPresent()) {
                SeatAllocationInactivationLog seatAllocationInactivationLog = optionalSeatAllocationInactivationLog.get();
                seatAllocationInactivationLog.setCancelled(true);
                seatAllocationInactivationLogRepository.save(seatAllocationInactivationLog);
            }

            if (seatAllocation.isActive()) {
                ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
                while (iterator.hasNext()) {
                    SeatAllocation thisOne = iterator.next();
                    if (thisOne.getId().equals(seatAllocation.getId())) {
                        iterator.remove();
                    }
                }
            }
            seatRepository.save(seat);
            seatAllocationRepository.save(seatAllocation);
        }
    }


    // -----------------------------------------------Helper methods-----------------------------------------------

    // Check whether the seats to be allocated:
    // 1. exist
    // 2. belong to the same seat map
    // 3. have already been allocated to any function/business unit/team
    private List<Seat> validateSeatsInformationForAllocation(List<String> seatIds, String targetUnit) throws SeatAllocationException {

        System.out.println("******************** Validate Seats Information For Allocation ********************");
        List<Seat> seats = new ArrayList<>();
        for (String seatId:
                seatIds) {
            try {
                System.out.println("********** retrieving seats: " + seatId + " **********");
                Seat seat = seatService.retrieveSeatById(seatId);
                seats.add(seat);
                if (seats.size() > 0 && !seats.get(0).getSeatMap().getId().equals(seat.getSeatMap().getId())) {
                    throw new SeatAllocationException("Allocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetUnit.equals("FUNCTION") && seat.getFunctionAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: seat " + seatId + " has already been allocated to a function!");
                } else if (targetUnit.equals("BUSINESS_UNIT") && seat.getBusinessUnitAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: seat " + seatId + " has already been allocated to a business unit!");
                } if (targetUnit.equals("TEAM") && seat.getTeamAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: seat " + seatId + " has already been allocated to a team!");
                }
            } catch (SeatNotFoundException ex) {
                throw new SeatAllocationException("Allocation failed: " + ex.getMessage());
            }
        }

        return seats;
    }


    // Check whether the seats to be allocated:
    // 1. exist
    // 2. belong to the same seat map
    // 3. have already been allocated to any function/business unit
    private List<Seat> validateSeatsInformationForDeallocation(List<String> seatIds, String targetUnit) throws SeatAllocationException {

        List<Seat> seats = new ArrayList<>();
        for (String seatId:
                seatIds) {
            try {
                Seat seat = seatService.retrieveSeatById(seatId);
                seats.add(seat);
                if (seats.size() > 0 && !seats.get(0).getSeatMap().getId().equals(seat.getSeatMap().getId())) {
                    throw new SeatAllocationException("Deallocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetUnit.equals("FUNCTION")) {
                    if (seat.getTeamAssigned() != null) {
                        throw new SeatAllocationException("Deallocation of seats failed: some seat has already been allocated to a team!");
                    }
                    if (seat.getBusinessUnitAssigned() != null) {
                        throw new SeatAllocationException("Deallocation of seats failed: some seat has already been allocated to a business unit!");
                    }
                } else if (targetUnit.equals("BUSINESS_UNIT")) {
                    if (seat.getTeamAssigned() != null) {
                        throw new SeatAllocationException("Deallocation of seats failed: some seat has already been allocated to a team!");
                    }
                }
                if (seat.getActiveSeatAllocations().size() > 0) {
                    throw new SeatAllocationException("Deallocation of seats failed: seat" + seat.getCode() + " has been allocated to an employee!");
                }
            } catch (SeatNotFoundException ex) {
                throw new SeatAllocationException("Deallocation failed: " + ex.getMessage());
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


    // Check whether the office where the seats belong has the business unit required
    private BusinessUnit validateBusinessUnitInformation(String businessUnitId, List<Seat> seats) throws SeatAllocationException {

        Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findByIdNonDeleted(businessUnitId);
        if(!optionalBusinessUnit.isPresent()) {
            throw new SeatAllocationException("Allocation of seats failed: business unit does not exist!");
        }
        BusinessUnit businessUnit = optionalBusinessUnit.get();
        Office office = seats.get(0).getSeatMap().getOffice();
        if(!office.getBusinessUnitsCodeInOffice().contains(businessUnit.getCode())) {
            throw new SeatAllocationException("Office " + office.getObjectName() + " does not have " + businessUnit.getObjectName() + " business unit!");
        }

        // It has been confirmed that the office has the business unit, need to check whether the seats have already been allocated to the function
        //   and if the seats have already been allocated to another business unit
        for (Seat seat :
                seats) {
            if (seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Allocation of seats failed: seat must be allocated to the function before being allocated to a business unit!");
            } else { // the seat has already been assigned to a function
                if (!seat.getFunctionAssigned().getCode().equals(businessUnit.getFunction().getCode())) {
                    throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to another function!");
                }
            }
        }

        return businessUnit;
    }


    // Check whether the office where the seats belong has the function required and the team belongs to that function
    // Function -> Business Unit -> Team
    private Team validateTeamInformation(String teamId, List<Seat> seats) throws SeatAllocationException {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(!optionalTeam.isPresent()) {
            throw new SeatAllocationException("Allocation of seats failed: team does not exist!");
        }

        Team team = optionalTeam.get();
        BusinessUnit businessUnit = validateBusinessUnitInformation(team.getBusinessUnit().getId(), seats);
        for (Seat seat :
                seats) {
            if (seat.getBusinessUnitAssigned() == null) {
                throw new SeatAllocationException("Allocation of seats failed: seat must be allocated to the business unit before being allocated to a team!");
            } else if (!seat.getBusinessUnitAssigned().getCode().equals(businessUnit.getCode())) {
                throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to another business unit!");
            }
        }

        return team;
    }


    // Validate the start date of the allocation.
    private void validateOccupancyDateTimeUsingToday(Date dateTime, boolean isStartDateTime) throws SeatAllocationException {
        if (isStartDateTime) {
            if (dateTime.compareTo(DateHelper.getDateWithoutTimeUsingCalendar(new Date())) < 0) {
                throw new SeatAllocationException("Assigning seat failed: the start date time of the occupancy cannot be a historical time!");
            }
            GregorianCalendar oneYearLater = new GregorianCalendar();
            oneYearLater.roll(Calendar.YEAR,1);
            Date oneYearLaterDate = oneYearLater.getTime();
            if (dateTime.after(oneYearLaterDate)) {
                throw new SeatAllocationException("Assigning seat failed: the start date time of the occupancy cannot be later than 1 year " +
                        "after the current date!");
            }
        } else {
            if (dateTime.compareTo(new Date()) < 0) {
                throw new SeatAllocationException("Assigning seat failed: the end date time of the occupancy cannot be a historical time!");
            }
        }
    }


    private void validateScheduleRecurringBasis(ScheduleRecurringBasisEnum recurringBasisEnum, ScheduleModel scheduleModel) throws SeatAllocationException {
        if (recurringBasisEnum == ScheduleRecurringBasisEnum.EVERYDAY) {
            if (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: recurring start time or end time must be provided for EveryDay recurring basis!");
            }
        } else if (recurringBasisEnum == ScheduleRecurringBasisEnum.EVERYWEEK) {
            if (scheduleModel.getRecurringWeekdays() == null || scheduleModel.getRecurringWeekdays().size() == 0) {
                throw new SeatAllocationException("Assigning shared seat failed: there must be at least one recurring weekday provided for EveryWeek recurring basis!");
            } else if  (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: recurring start time or end time must be provided for EveryWeek recurring basis!");
            }
        } else {
            if (scheduleModel.getRecurringDates() == null || scheduleModel.getRecurringDates().size() == 0) {
                throw new SeatAllocationException("Assigning shared seat failed: there must be at least one recurring date provided for EveryYear recurring basis!");
            } else if  (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: recurring start time or end time must be provided for EveryYear recurring basis!");
            }
        }
    }


    // Pre-condition:
    // - The two schedules were checked before hand to have overlapping time frame (based on date only)
    private void checkSharedSeatsScheduleClash(Schedule schedule1, Schedule schedule2) throws SeatAllocationException {

        // shared seats -> compare by recurring basis, one by one
        // "EveryDay" (recurringStartTime, recurringEndTime),
        // "EveryWeek" (recurringWeekdays, recurringStartTime, recurringEndTime),

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
                            if (dayOfWeek.equals(dayOfWeek2)) {
                                checkHourlyClashes(schedule1, schedule2);
                            }
                        }
                    }
                } else {
                    for (DayOfWeek dayOfWeek :
                            schedule1.getRecurringWeekdays()) {
                        for (Date date :
                                schedule2.getRecurringDates()) {
                            if (dayOfWeek == DateHelper.getDayOfWeekFromDate(date)) {
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

        System.out.println("******************** Check Hourly Clashes ********************");
        LocalTime startTime1 = schedule1.getRecurringStartTime();
        LocalTime endTime1 = schedule1.getRecurringEndTime();
        LocalTime startTime2 = schedule2.getRecurringStartTime();
        LocalTime endTime2 = schedule2.getRecurringEndTime();

        if (startTime1.compareTo(startTime2) < 0) {
            System.out.println("********** " + startTime1.toString() + " is before " + startTime2.toString() + " **********");
            if (!(endTime1.compareTo(startTime2) < 0)) {
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


}
