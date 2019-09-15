package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SeatService {
    @Autowired
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public String createNewSeat(Seat seat) throws SeatCreationException {
        if (seat.getCoordinate() == null || seat.getSeatMap() == null) {
            throw new SeatCreationException("Creation of seat failed: insufficient seat info given");
        }

        // Check whether the seatmap of this seat already has a seat with a same coordinate
        if (seat.getSeatMap().getId() != null) {

        }

        Seat newSeat = seatRepository.save(seat);
        return newSeat.getId();
    }
}
