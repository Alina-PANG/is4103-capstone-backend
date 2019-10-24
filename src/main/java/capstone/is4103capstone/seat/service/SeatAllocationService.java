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
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import capstone.is4103capstone.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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
    @Autowired
    private ScheduleService scheduleService;

    // -----------------------------------------------Assumptions-----------------------------------------------
    // 1. the booking period is 1 year, meaning that the start date of the seat allocation cannot be later than 1 year from the current date.
    // 2. User's right to edit the allocation information has been done at the controller level.



    // ---------------------------------- Unit Level -----------------------------------
    public void allocateSeatsToFunction(SeatAllocationModelForFunction seatAllocationModelForFunction) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForAllocation(seatAllocationModelForFunction.getSeatIds(), "FUNCTION");
        CompanyFunction function = validateFunctionInformation(seatAllocationModelForFunction.getFunctionId(), seats);

        for (Seat seat :
                seats) {
            if (seat.getActiveSeatAllocations().size() > 0) {
                throw new SeatAllocationException("Allocating seats to function failed: some seat has active allocations!");
            }
        }

        for (Seat seat :
                seats) {
            // Need to change the seat type because the seat may have been a hot desk previously
            seat.setType(SeatTypeEnum.FIXED);
            seat.setFunctionAssigned(function);
            seatRepository.save(seat);
        }

    }


    // Pre-conditions:
    // 1. A seat can only be deallocated from a function when it is not assigned to a team/business unit / does not have any active employee allocation.
    public void deallocateSeatsFromFunction(BulkSeatIdsModel bulkSeatIdsModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkSeatIdsModel.getSeatIds(), "FUNCTION");

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
    public void deallocateSeatsFromBusinessUnit(BulkSeatIdsModel bulkSeatIdsModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkSeatIdsModel.getSeatIds(), "BUSINESS_UNIT");

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
    public void deallocateSeatsFromTeam(BulkSeatIdsModel bulkSeatIdsModel) throws SeatAllocationException {

        List<Seat> seats = validateSeatsInformationForDeallocation(bulkSeatIdsModel.getSeatIds(), "TEAM");

        for (Seat seat :
                seats) {
            seat.setTeamAssigned(null);
            seatRepository.save(seat);
        }
    }

    // Pre-conditions:
    // 1. A seat can only be marked as a hot desk if it does not have any active employee allocation.
    // 2. A hot desk is assigned at the floor level, which means a seat does not have to be pre-assigned to a function/team to become a hot desk.
    public void markSeatsAsHotDesk(BulkSeatIdsModel bulkSeatIdsModel) {
        List<Seat> seats = validateSeatsInformationForAllocation(bulkSeatIdsModel.getSeatIds(), "HOT_DESK");
        // Check whether any seat has active seat allocation
        for (Seat seat :
                seats) {
            if (seat.getActiveSeatAllocations().size() > 0) {
                throw new SeatAllocationException("Marking seat as hot desk failed: seat with ID " + seat.getId() + " has active allocations!");
            }
            if (seat.isUnderOffice()) {
                throw new SeatAllocationException("Marking seat as hot desk failed: seat under office cannot be allocated as hot desk!");
            }
        }

        // Disassociate each seat with the team, business unit and company function assigned
        for (Seat seat :
                seats) {
            seat.setTeamAssigned(null);
            seat.setBusinessUnitAssigned(null);
            seat.setFunctionAssigned(null);
            seat.setType(SeatTypeEnum.HOTDESK);
            seatRepository.save(seat);
        }
    }


    // ---------------------------------- Employee Level -----------------------------------

    // Assumptions:
    // 1. A hot desk is like a fixed seat to a temporary employee, and it's directly allocated to an individual employee.
    // 2. The allocation schedule must have an end date.
    // 3. Seats under office cannot be allocated as hot desks.
    public void assignHotDeskToAnEmployee(SeatAllocationModelForEmployee seatAllocationModelForEmployee) throws SeatAllocationException {
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
            if (seatAllocationModelForEmployee.getType() == null ||
                    seatAllocationModelForEmployee.getType().trim().length() == 0 ||
                    !seatAllocationModelForEmployee.getType().equals("HOTDESK")) {
                throw new SeatAllocationException("Assigning hot desk failed: invalid or mismatching allocation type!");
            }
            if (!seat.getType().equals(SeatTypeEnum.HOTDESK)) {
                throw new SeatAllocationException("Assigning hot desk failed: the seat is not a hot desk!");
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

            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                try {
                    scheduleService.checkClashForHotDeskTypeAllocation(startDateTime, endDateTime, seatAllocation);
                } catch (ScheduleClashException ex) {
                    throw new SeatAllocationException("Assigning hot desk failed: " + ex.getMessage());
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

            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            System.out.println("********** the number of active allocations: " + activeAllocations.size() + " **********");
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                try {
                    scheduleService.checkClashForPermanentFixedTypeAllocation(startDateTime, seatAllocation);
                } catch (ScheduleClashException ex) {
                    throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
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
            if (startDateTime == null) {
                throw new SeatAllocationException("Assigning fixed seat failed: start date is required!");
            }
            Date endDateTime = seatAllocationModel.getSchedule().getEndDateTime();
            if (endDateTime == null || startDateTime.after(endDateTime)) {
                throw new SeatAllocationException("Assigning fixed seat failed: invalid end date of the seat allocation!");
            }
            validateOccupancyDateTimeUsingToday(startDateTime, true);
            validateOccupancyDateTimeUsingToday(endDateTime, false);
            System.out.println("********** Start Date Time: " + startDateTime.toString() + " **********");
            System.out.println("********** End Date Time: " + endDateTime.toString() + " **********");

            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                try {
                    scheduleService.checkClashForTemporaryFixedTypeAllocation(startDateTime, endDateTime, seatAllocation);
                } catch (ScheduleClashException ex) {
                    throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
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
            if (startDateTime == null) {
                throw new SeatAllocationException("Assigning shared seat failed: start date is required!");
            }
            validateOccupancyDateTimeUsingToday(startDateTime, true);
            Date endDateTime = seatAllocationModel.getSchedule().getEndDateTime();
            if (endDateTime != null) {
                if (startDateTime.after(endDateTime)) {
                    throw new SeatAllocationException("Assigning shared seat failed: invalid end date of the seat allocation!");
                }
                validateOccupancyDateTimeUsingToday(endDateTime, false);
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
            scheduleService.validateScheduleRecurringBasis(recurringBasisEnum, seatAllocationModel.getSchedule());
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

            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            for (SeatAllocation seatAllocation :
                    activeAllocations) {
                try {
                    scheduleService.checkClashForSharedTypeAllocation(startDateTime, endDateTime, newAllocationSchedule, seatAllocation);
                } catch (ScheduleClashException ex) {
                    throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
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


    // Pre-conditions:
    // 1. A seat can only be assigned as a shared seat to an employee when it does not have any schedule clash with other active allocations.
    // 2. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    // Schedules example in a single allocation:
    // - 9am to 6pm, recurringWeekdays: [1,2]
    // - 1pm to 6pm, recurringWeekdays: [3]
    // - 9am to 6pm, recurringWeekdays: [4]
    public void assignSharedSeatToEmployeeWithMultipleSchedules(SharedSeatAllocationModelForEmployee sharedSeatAllocationModelForEmployee) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(sharedSeatAllocationModelForEmployee.getSeatId());

            if (seat.getTeamAssigned() == null || seat.getFunctionAssigned() == null) {
                throw new SeatAllocationException("Assigning shared seat failed: the seat must be assigned to a function and a team first!");
            }

            // Check whether the employee who will occupy this seat belongs to the team this seat has been assigned to
            Employee employee = employeeService.retrieveEmployeeById(sharedSeatAllocationModelForEmployee.getEmployee().getId());
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

            List<SeatAllocation> seatAllocationsToSave = new ArrayList<>();
            // Check each schedule inside the request
            for (ScheduleModel scheduleModel :
                    sharedSeatAllocationModelForEmployee.getSchedules()) {

                Date startDateTime = scheduleModel.getStartDateTime();
                if (startDateTime == null) {
                    throw new SeatAllocationException("Assigning fixed seat failed: start date is required!");
                }
                validateOccupancyDateTimeUsingToday(startDateTime, true);
                Date endDateTime = scheduleModel.getEndDateTime();
                if (endDateTime != null) {
                    if (startDateTime.after(endDateTime)) {
                        throw new SeatAllocationException("Assigning fixed seat failed: invalid end date of the seat allocation!");
                    }
                    validateOccupancyDateTimeUsingToday(endDateTime, false);
                }

                // Create a new seat allocation between the seat and the employee
                Schedule newAllocationSchedule = new Schedule();
                newAllocationSchedule.setStartDateTime(startDateTime);
                newAllocationSchedule.setEndDateTime(endDateTime);
                newAllocationSchedule.setRecurring(true);
                String recurringBasisString = scheduleModel.getRecurringBasis();
                if (!recurringBasisString.equals("EVERYDAY") && !recurringBasisString.equals("EVERYWEEK")) {
                    throw new SeatAllocationException("Assigning shared seat failed: invalid recurring basis!");
                }

                ScheduleRecurringBasisEnum recurringBasisEnum = ScheduleRecurringBasisEnum.valueOf(scheduleModel.getRecurringBasis());
                scheduleService.validateScheduleRecurringBasis(recurringBasisEnum, scheduleModel);
                newAllocationSchedule.setRecurringBasis(recurringBasisEnum);
                if (scheduleModel.getRecurringDates() != null && scheduleModel.getRecurringDates().size() > 0) {
                    for (Date date :
                            scheduleModel.getRecurringDates()) {
                        date = DateHelper.getDateWithoutTimeUsingCalendar(date);
                    }
                }
                newAllocationSchedule.setRecurringDates(scheduleModel.getRecurringDates());
                if (scheduleModel.getRecurringWeekdays() != null && scheduleModel.getRecurringWeekdays().size() > 0) {
                    for (Integer weekDay :
                            scheduleModel.getRecurringWeekdays()) {
                        if (weekDay < 1 || weekDay > 7) {
                            throw new SeatAllocationException("Assigning shared seat failed: invalid recurring weekday of " + weekDay + "!");
                        }
                        newAllocationSchedule.getRecurringWeekdays().add(DayOfWeek.of(weekDay));
                    }
                }
                newAllocationSchedule.setRecurringStartTime(scheduleModel.getRecurringStartTime());
                newAllocationSchedule.setRecurringEndTime(scheduleModel.getRecurringEndTime());

                List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
                for (SeatAllocation seatAllocation :
                        activeAllocations) {
                    try {
                        scheduleService.checkClashForSharedTypeAllocation(startDateTime, endDateTime, newAllocationSchedule, seatAllocation);
                    } catch (ScheduleClashException ex) {
                        throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
                    }
                }

                // Create a new seat allocation between the seat and the employee
                SeatAllocation newSeatAllocation = new SeatAllocation();
                newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.SHARED);
                newSeatAllocation.setSeat(seat);
                newSeatAllocation.setEmployee(employee);
                newSeatAllocation.setSchedule(newAllocationSchedule);

                // Check clashes between new schedules
                for (SeatAllocation otherNewSeatAllocation :
                        seatAllocationsToSave) {
                    try {
                        scheduleService.checkClashForSharedTypeAllocation(startDateTime, endDateTime, newAllocationSchedule, otherNewSeatAllocation);
                    } catch (ScheduleClashException ex) {
                        throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage() + " (among the requested schedules)");
                    }
                }

                seatAllocationsToSave.add(newSeatAllocation);
            }

            for (SeatAllocation newSeatAllocation :
                    seatAllocationsToSave) {
                Schedule newSchedule = newSeatAllocation.getSchedule();
                newSchedule = scheduleRepository.save(newSchedule);
                newSeatAllocation = seatAllocationRepository.saveAndFlush(newSeatAllocation);
                seat.getActiveSeatAllocations().add(newSeatAllocation);

                SeatAllocationInactivationLog log = new SeatAllocationInactivationLog();
                log.setAllocation_id(newSeatAllocation.getId());
                log.setInactivate_time(newSchedule.getEndDateTime());
                seatAllocationInactivationLogRepository.save(log);
            }
            seatRepository.save(seat);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning shared seat failed: " + ex.getMessage());
        }
    }

    // ---------------------------------- Other Services -----------------------------------

    // ---------------------- Retrieval ----------------------

    public Seat retrieveSeatWithAllocationsBySeatId(String seatId) throws SeatNotFoundException, SeatAllocationException {
        Optional<Seat> optionalSeat = seatRepository.findUndeletedById(seatId);
        if (!optionalSeat.isPresent()) {
            throw new SeatNotFoundException("Seat does not exist!");
        }

        return optionalSeat.get();
    }



    // ---------------------- Deallocation ----------------------

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
        seatAllocation.getSchedule().setDeleted(true);
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
            seatAllocation.getSchedule().setDeleted(true);
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
        List<String> allocationIdsToRemove = new ArrayList<>();

        for (SeatAllocation seatAllocation :
                seatAllocations) {
            seatAllocation.setDeleted(true);
            seatAllocation.getSchedule().setDeleted(true);
            Optional<SeatAllocationInactivationLog> optionalSeatAllocationInactivationLog = seatAllocationInactivationLogRepository.getUncancelledById(seatAllocation.getId());
            if (optionalSeatAllocationInactivationLog.isPresent()) {
                SeatAllocationInactivationLog seatAllocationInactivationLog = optionalSeatAllocationInactivationLog.get();
                seatAllocationInactivationLog.setCancelled(true);
                seatAllocationInactivationLogRepository.save(seatAllocationInactivationLog);
            }

            if (seatAllocation.isActive()) {
                allocationIdsToRemove.add(seatAllocation.getId());

            }

            seatAllocationRepository.save(seatAllocation);
        }

        ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
        while (iterator.hasNext()) {
            SeatAllocation thisOne = iterator.next();
            if (allocationIdsToRemove.contains(thisOne.getId())) {
                iterator.remove();
            }
        }
        seatRepository.save(seat);
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

}
