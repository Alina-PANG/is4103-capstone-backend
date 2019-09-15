package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.req.CreateSeatMapReq;
import capstone.is4103capstone.seat.model.req.CreateSeatReq;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.UUID;

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
    public Seat createNewSeatForNewSeatMap(CreateSeatReq createSeatReq) throws SeatCreationException {
        if (createSeatReq.getId() == null) {
            throw new SeatCreationException("Creation of seat failed: insufficient seat info given.");
        }

        if (createSeatReq.getX() < 0 || createSeatReq.getY() < 0) {
            throw new SeatCreationException("Creation of seat failed: invalid seat coordinate.");
        }

        Seat newSeat = new Seat();
        newSeat.setCoordinate(new Point(createSeatReq.getX(), createSeatReq.getY()));
        newSeat.setCode(createSeatReq.getId());
        return newSeat;
    }


    // Pre-conditions:
    // 1. There won't be overlapping of seat space because it has been already checked at the front-end.
    public String createNewSeatForExistingSeatMap(CreateSeatReq createSeatReq, String seatMapId) throws SeatCreationException {
        if (createSeatReq.getId() == null) {
            throw new SeatCreationException("Creation of seat failed: insufficient seat info given.");
        }

        if (createSeatReq.getX() < 0 || createSeatReq.getY() < 0) {
            throw new SeatCreationException("Creation of seat failed: invalid seat coordinate.");
        }

        Seat newSeat = new Seat();
        // Check whether the seatmap of this seat already has a seat with a same coordinate
        if (seatMapId != null) {
            Optional<SeatMap> seatMap = seatMapRepository.findById(seatMapId);
            if (!seatMap.isPresent()) {
                throw new SeatCreationException("Creation of seat failed: invalid seat map.");
            } else {

            }
        }

        return "";
    }
}
