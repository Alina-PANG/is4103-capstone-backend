package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.seat.model.SeatAllocationModelForFunction;
import capstone.is4103capstone.seat.model.SeatAllocationModelForTeam;
import capstone.is4103capstone.seat.model.SeatModelForAllocation;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatAllocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private OfficeRepository officeRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private TeamRepository teamRepository;

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


    // Check whether the seats to be allocated:
    // 1. exist
    // 2. belong to the same seat map
    // 3. have already been allocated to any function/team
    private List<Seat> validateSeatsInformationForAllocation(List<SeatModelForAllocation> seatModels, boolean targetFunction) throws SeatAllocationException {

        List<Seat> seats = new ArrayList<>();
        for (SeatModelForAllocation seatModel:
                seatModels) {
            Optional<Seat> optionalSeat = seatRepository.findById(seatModel.getId());
            if (!optionalSeat.isPresent()) {
                throw new SeatAllocationException("Allocation of seats failed: seat with ID " + seatModel.getId() + " does not exist!");
            } else {
                Seat seat = optionalSeat.get();
                seats.add(seat);
                if (seats.size() > 0 && seats.get(0).getSeatMap().getId() != seat.getSeatMap().getId()) {
                    throw new SeatAllocationException("Allocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetFunction && seat.getFunctionAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to a function!");
                } else if (!targetFunction && seat.getTeamAssigned() != null) {
                    throw new SeatAllocationException("Allocation of seats failed: some seat has already been allocated to a team!");
                }
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
            Optional<Seat> optionalSeat = seatRepository.findById(seatModel.getId());
            if (!optionalSeat.isPresent()) {
                throw new SeatAllocationException("Allocation of seats failed: seat with ID " + seatModel.getId() + " does not exist!");
            } else {
                Seat seat = optionalSeat.get();
                seats.add(seat);
                if (seats.size() > 0 && seats.get(0).getSeatMap().getId() != seat.getSeatMap().getId()) {
                    throw new SeatAllocationException("Allocation of seats failed: seats don't belong to the same seat map!");
                }
                if (targetFunction && seat.getTeamAssigned() != null) {
                    throw new SeatAllocationException("Deallocation of seats failed: some seat has already been allocated to a team!");
                }
                if (seat.getActiveSeatAllocations().size() > 0) {
                    throw new SeatAllocationException("Deallocation of seats failed: seat" + seat.getCode() + " has been allocated to an employee!");
                }
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
