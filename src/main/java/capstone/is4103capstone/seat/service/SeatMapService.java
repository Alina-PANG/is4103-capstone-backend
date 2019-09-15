package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.req.CreateSeatMapReq;
import capstone.is4103capstone.seat.model.req.CreateSeatReq;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import capstone.is4103capstone.util.exception.SeatMapCreationException;
import capstone.is4103capstone.util.exception.SeatMapNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatMapService {

    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private SeatService seatService;

    public SeatMapService() {

    }


    // Pre-condition:
    // 1. the seats in the seatmap do not have any crossing coordinates (their squares won't overlap)
    // 2. the seats are already sorted
    // 3. the req passed in has already been checked in terms of user access rights to different offices (before the seat map was drawn in front-end canvas)
    public String createNewSeatMap(CreateSeatMapReq seatMapReq) throws SeatMapCreationException {
        if (seatMapReq.getRegion() == null || seatMapReq.getCountry() == null || seatMapReq.getOffice() == null || seatMapReq.getFloor() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: insufficient info given.");
        }

        Optional<Office> office = officeRepository.findByOfficeNameAndCountryName(seatMapReq.getOffice(), seatMapReq.getCountry());
        if (!office.isPresent()) {
            throw new SeatMapCreationException("Creation of seat map failed: invalid office information.");
        }

        SeatMap newSeatMap = new SeatMap();
        newSeatMap.setFloor(seatMapReq.getFloor());
        newSeatMap.setOffice(office.get());

        // Create seats one by one
        Integer seatCount = 0;
        for (CreateSeatReq seatReq: seatMapReq.getSeats()) {
            try {
                Seat newSeat = seatService.createNewSeatForNewSeatMap(seatReq);
                seatCount++;
                newSeat.setSerialNumber(seatCount);
                newSeatMap.getSeats().add(newSeat);
            } catch (SeatCreationException ex) {
                throw new SeatMapCreationException(ex.getMessage());
            }
        }

        newSeatMap.setNumOfSeats(seatCount);

        for (Seat seat: newSeatMap.getSeats()) {
            seat.setSeatMap(newSeatMap);
            seatRepository.save(seat);
        }
        newSeatMap = seatMapRepository.save(newSeatMap);

        return newSeatMap.getId();
    }


    public SeatMap getSeatMapById(String id) throws SeatMapNotFoundException {
        Optional<SeatMap> optionalSeatMap = seatMapRepository.findById(id);
        if (!optionalSeatMap.isPresent()) {
            throw new SeatMapNotFoundException("Seat map does not exist.");
        } else {
            SeatMap seatMap = optionalSeatMap.get();
            seatMap.getOffice().getCountry().getRegion();
            seatMap.getSeats().size();
            return seatMap;
        }
    }
}
