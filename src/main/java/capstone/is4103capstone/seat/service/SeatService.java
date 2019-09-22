package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.SeatModelForSeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
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

        Seat newSeat = new Seat();
        newSeat.setxCoordinate(newSeatModel.getX());
        newSeat.setyCoordinate(newSeatModel.getY());
        newSeat.setSerialNumber(newSeatModel.getSerialNumber());
        return newSeat;
    }

}
