package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.util.exception.SeatMapCreationException;
import capstone.is4103capstone.util.exception.SeatMapNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatMapService {

    @Autowired
    private final SeatMapRepository seatMapRepository;
    @Autowired
    private final OfficeRepository officeRepository;

    public SeatMapService(SeatMapRepository seatMapRepository, OfficeRepository officeRepository) {
        this.seatMapRepository = seatMapRepository;
        this.officeRepository = officeRepository;
    }

    // Pre-condition:
    // 1. the seats in the seatmap do not have any crossing coordinates (their squares won't overlap)
    // 2. the seats are already sorted
    public String createNewSeatMap(SeatMap seatMap) throws SeatMapCreationException {
        if (seatMap.getNumOfSeats() <= 0) {
            throw new SeatMapCreationException("Creation of seat map failed: a seat map must have at least one seat.");
        }

        if (seatMap.getFloor() == null || seatMap.getOffice() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: insufficient info given.");
        }

        Optional<Office> office = officeRepository.findById(seatMap.getOffice().getId());
        if (office == null) {
            throw new SeatMapCreationException("Creation of seat map failed: invalid office information.");
        }

        for (Seat seat: seatMap.getSeats()) {

        }

        return "";
    }

    public Optional<SeatMap> getSeatMapById(String id) throws SeatMapNotFoundException {
        Optional<SeatMap> seatMap = seatMapRepository.findById(id);
        return seatMap;
    }
}
