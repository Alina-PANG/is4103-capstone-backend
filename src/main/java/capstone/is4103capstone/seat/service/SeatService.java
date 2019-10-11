package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.seat.model.seat.SeatModelForSeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import capstone.is4103capstone.util.exception.SeatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatMapRepository seatMapRepository;

    public SeatService() {

    }

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
}
