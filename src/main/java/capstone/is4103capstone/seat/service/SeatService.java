package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelForSeatMap;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.ScheduleClashException;
import capstone.is4103capstone.util.exception.SeatCreationException;
import capstone.is4103capstone.util.exception.SeatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;

    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private ScheduleService scheduleService;

    public SeatService() {

    }

    // ---------------------------------- Creation -----------------------------------

    // Pre-conditions:
    // 1. There won't be overlapping of seat space because it has been already checked at the front-end.
    Seat createNewSeat(SeatModelForSeatMap newSeatModel) throws SeatCreationException {
        if (newSeatModel.getSerialNumber() == null) {
            throw new SeatCreationException("Creation of seat failed: serial number is required but not given.");
        }

        if (newSeatModel.getX() < 0 || newSeatModel.getY() < 0) {
            throw new SeatCreationException("Creation of seat failed: invalid seat coordinate.");
        }

        System.out.println("******************** Create New Seat ********************");
        System.out.println("********** serial number: " + newSeatModel.getSerialNumber() + " **********");
        System.out.println("********** is office? " + newSeatModel.isUnderOffice() + " **********");
        Seat newSeat = new Seat();
        newSeat.setxCoordinate(newSeatModel.getX());
        newSeat.setyCoordinate(newSeatModel.getY());
        newSeat.setSerialNumber(newSeatModel.getSerialNumber());
        if (newSeatModel.isUnderOffice()) {
            newSeat.setUnderOffice(true);
            if (newSeatModel.getAdjacentSeatSeqNum() != null) {
                newSeat.setAdjacentSeatSeqNum(newSeatModel.getAdjacentSeatSeqNum());
            }
        }

        return newSeat;
    }


    // ---------------------------------- Retrieval -----------------------------------

    Seat retrieveSeatById(String seatId) throws SeatNotFoundException {

        if (seatId == null || seatId.trim().length() == 0) {
            throw new SeatNotFoundException("No seat ID given!");
        }

        seatId = seatId.trim();
        Optional<Seat> optionalSeat = seatRepository.findUndeletedById(seatId);
        if(!optionalSeat.isPresent()) {
            throw new SeatNotFoundException("Seat with ID " + seatId + " does not exist!");
        } else {
            return optionalSeat.get();
        }
    }

    public List<Seat> retrieveSeatInventoryByTeamId(String teamId) {
        return seatRepository.findOnesByTeamId(teamId);
    }


    // ---------------------------------- Deletion -----------------------------------

    public void deleteSeats(List<Seat> seats) {
        for (Seat seat :
                seats) {
            System.out.println("-------------------------------------------");
            System.out.println("***** removing seat: " + seat.getId() + " *****");
            for (SeatAllocation seatAllocation:
                    seat.getActiveSeatAllocations()) {
                seatAllocation.setDeleted(true);
                seatAllocationRepository.save(seatAllocation);
            }
            for (SeatAllocation seatAllocation:
                    seat.getInactiveSeatAllocations()) {
                seatAllocation.setDeleted(true);
                seatAllocationRepository.save(seatAllocation);
            }
            seat.setDeleted(true);
            seat.setSeatMap(null);
            seat.setCode(null);
            seatRepository.save(seat);
        }
    }


    // ---------------------------------- Logic Checking -----------------------------------

    public boolean hasScheduleClash(Seat seat, Schedule schedule, SeatAllocationTypeEnum seatAllocationTypeEnum) {

        try {
            // Decide which type of allocation does this schedule represent
            if (seatAllocationTypeEnum.equals(SeatAllocationTypeEnum.FIXED)) {
                if (schedule.getEndDateTime() == null) {
                    // FIXED for permanent employee
                    for (SeatAllocation activeAllocation :
                            seat.getActiveSeatAllocations()) {
                        scheduleService.checkClashForPermanentFixedTypeAllocation(schedule.getStartDateTime(), activeAllocation);
                    }
                } else {
                    for (SeatAllocation activeAllocation :
                            seat.getActiveSeatAllocations()) {
                        scheduleService.checkClashForTemporaryFixedTypeAllocation(schedule.getStartDateTime(), schedule.getEndDateTime(), activeAllocation);
                    }
                }
            } else if (seatAllocationTypeEnum.equals(SeatAllocationTypeEnum.SHARED)) {
                for (SeatAllocation activeAllocation :
                        seat.getActiveSeatAllocations()) {
                    scheduleService.checkClashForSharedTypeAllocation(schedule.getStartDateTime(), schedule.getEndDateTime(), schedule, activeAllocation);
                }
            } else {
                for (SeatAllocation activeAllocation :
                        seat.getActiveSeatAllocations()) {
                    scheduleService.checkClashForHotDeskTypeAllocation(schedule.getStartDateTime(), schedule.getEndDateTime(), activeAllocation);
                }
            }
        } catch (ScheduleClashException ex) {
            return true;
        }

        return false;
    }

    public boolean isSeatOccupiedDuringYearMonth(Seat seat, YearMonth yearMonth) {
        if (seat.getActiveSeatAllocations().size() == 0) {
            return false;
        }
        Collections.sort(seat.getActiveSeatAllocations());
        for (SeatAllocation activeSeatAllocation :
                seat.getActiveSeatAllocations()) {
            if (scheduleService.containYearMonth(activeSeatAllocation.getSchedule(), yearMonth)) {
                return true;
            }
        }
        return false;
    }
}
