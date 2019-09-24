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
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import capstone.is4103capstone.util.exception.EmployeeNotFoundException;
import capstone.is4103capstone.util.exception.SeatAllocationException;
import capstone.is4103capstone.util.exception.SeatNotFoundException;
import capstone.is4103capstone.util.exception.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    // 2. User's right to edit the allocation information has been done at the controller level.
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
    // 2. User's right to edit the allocation information has been done at the controller level.
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
    // 2. User's right to edit the allocation information has been done at the controller level.
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
    // 2. User's right to edit the allocation information has been done at the controller level.
    // 3. A seat does not have to be pre-assigned to a function/team to become a hot desk.
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
    // 1. A seat can only be assigned as a fixed seat to an employee when it does not have any currently active employee allocation.
    // 2. User's right to edit the allocation information has been done at the controller level.
    // 3. A seat must be pre-assigned to a function and a team in order to be assigned to an employee as a fixed seat.
    public void assignFixedSeatToEmployee(SeatModelForAllocation seatModel) throws SeatAllocationException {
        try {
            Seat seat = seatService.retrieveSeatById(seatModel.getId());
            if (seat.getActiveSeatAllocations().size() > 0) {
                throw new SeatAllocationException("Assigning fixed seat failed: the seat " + seat.getCode() + " currently has active employee allocation!");
            }

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

            // Create a new seat allocation between the seat and the employee
            Schedule allocationSchedule = new Schedule();
            Date startDateTime = seatModel.getSchedule().getStartDateTime();
            if (startDateTime.compareTo(new Date()) < 0) {
                throw new SeatAllocationException("Assigning fixed seat failed: the start date time of the occupancy cannot be a historical time!");
            }

            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setAllocationType(SeatAllocationTypeEnum.FIXED);
            newSeatAllocation.setSeat(seat);
            newSeatAllocation.setEmployee(employee);
            newSeatAllocation.setSchedule(allocationSchedule);

            scheduleRepository.save(allocationSchedule);
            seatAllocationRepository.save(newSeatAllocation);
        } catch (SeatNotFoundException | EmployeeNotFoundException | TeamNotFoundException ex) {
            throw new SeatAllocationException("Assigning fixed seat failed: " + ex.getMessage());
        }
    }


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
}
