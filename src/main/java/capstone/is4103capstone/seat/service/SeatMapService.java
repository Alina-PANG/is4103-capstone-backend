package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.SeatModelForSeatMap;
import capstone.is4103capstone.seat.model.SeatMapModel;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.exception.SeatCreationException;
import capstone.is4103capstone.util.exception.SeatMapCreationException;
import capstone.is4103capstone.util.exception.SeatMapNotFoundException;
import capstone.is4103capstone.util.exception.SeatMapUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    // Post-condition:
    // 1. all the seats in the new seatmap are created
    public String createNewSeatMap(SeatMapModel createSeatMapReq) throws SeatMapCreationException {

        if (createSeatMapReq.getRegion() == null || createSeatMapReq.getCountry() == null || createSeatMapReq.getOffice() == null || createSeatMapReq.getFloor() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: insufficient info given.");
        }

        Optional<Office> optionalOffice = officeRepository.findByOfficeNameAndCountryName(createSeatMapReq.getOffice(), createSeatMapReq.getCountry());
        if (!optionalOffice.isPresent()) {
            throw new SeatMapCreationException("Creation of seat map failed: invalid office information.");
        }

        Office office = optionalOffice.get();
        SeatMap newSeatMap = new SeatMap();
        newSeatMap.setFloor(createSeatMapReq.getFloor());
        newSeatMap.setOffice(office);

        // Create seats one by one
        for (SeatModelForSeatMap seatReq: createSeatMapReq.getSeats()) {
            try {
                Seat newSeat = seatService.createNewSeat(seatReq);
                newSeatMap.getSeats().add(newSeat);
                newSeat.setSeatMap(newSeatMap);
            } catch (SeatCreationException ex) {
                throw new SeatMapCreationException(ex.getMessage());
            }
        }

        newSeatMap.setNumOfSeats(newSeatMap.getSeats().size());

        // Initialise codes for seatmap and its seats
        // seatmap code example: SG-ORQ-26
        // seat code example: SG-ORQ-26-01
        String countryCode = office.getCountry().getCode();
        String officeCode = office.getCode();
        newSeatMap.setCode(countryCode + "-" + officeCode + "-" + newSeatMap.getFloor());
        for (Seat seat: newSeatMap.getSeats()) {
            seat.setCode(newSeatMap.getCode() + seat.getSerialNumber());
        }

        for (Seat seat: newSeatMap.getSeats()) {
            seatRepository.save(seat);
        }
        newSeatMap = seatMapRepository.save(newSeatMap);

        return newSeatMap.getId();
    }


    public SeatMap getSeatMapById(String id) throws SeatMapNotFoundException {

        Optional<SeatMap> optionalSeatMap = seatMapRepository.findById(id);
        if (!optionalSeatMap.isPresent()) {
            throw new SeatMapNotFoundException("Seat map required does not exist.");
        } else {
            SeatMap seatMap = optionalSeatMap.get();
            seatMap.getOffice().getCountry().getRegion();
            seatMap.getSeats().size();
            return seatMap;
        }
    }


    public void updateSeatMap(SeatMapModel seatMapModel) throws SeatMapUpdateException, SeatMapNotFoundException {

        if (seatMapModel.getId() == null || seatMapModel.getId().trim().length() == 0) {
            throw new SeatMapUpdateException("Seat map update failed: insufficient seat map info given.");
        }

        SeatMap seatMap = getSeatMapById(seatMapModel.getId().trim());
        List<String> originalSeatsIds = new ArrayList<>();
        for (Seat originalSeat:
             seatMap.getSeats()) {
            originalSeatsIds.add(originalSeat.getId());
        }

        // Sort the seats based on the serial number.
        Collections.sort(seatMapModel.getSeats());
        Collections.sort(seatMap.getSeats());

        // Find the seat in the original seatmap.
        for (SeatModelForSeatMap seatModel:
             seatMapModel.getSeats()) {
            if (seatModel.getId() == null || seatModel.getId().trim().length() == 0) {
                // This is a new seat. Need to create this seat and add to the existing seatmap.
                Seat newSeat = seatService.createNewSeat(seatModel);
                seatMap.getSeats().add(newSeat);
                newSeat.setSeatMap(seatMap);
            } else {
                Optional<Seat> optionalSeatToUpdate = seatRepository.findById(seatModel.getId().trim());
                if (!optionalSeatToUpdate.isPresent()) {
                    throw new SeatMapUpdateException("Seat map update failed: invalid seat info given. There is no seat" +
                            "with id " + seatModel.getId());
                } else {
                    Seat seatToUpdate = optionalSeatToUpdate.get();
                    // The seat exists, but need to ensure it exists only in this seatmap
                    if (!seatToUpdate.getCode().contains(seatMap.getCode())) {
                        throw new SeatMapUpdateException("Seat map update failed: invalid seat info given. Seat with id " +
                                seatModel.getId() + " does not exist in the seatmap");
                    } else {
                        // Update seat information directly, even if the information is actually the same
                        seatToUpdate.setSerialNumber(seatModel.getSerialNumber());
                        seatToUpdate.getCoordinate().x = seatModel.getX();
                        seatToUpdate.getCoordinate().y = seatModel.getY();
                        originalSeatsIds.remove(seatToUpdate.getId());
                    }
                }
            }
        }

        // Delete the seats that are in the original seatmap but not in the updated seatmap.
        ListIterator<Seat> seatListIterator = seatMap.getSeats().listIterator();
        while (seatListIterator.hasNext()) {
            Seat thisSeat = seatListIterator.next();
            if (originalSeatsIds.contains(thisSeat.getId())) {
                seatListIterator.remove();
            }
        }

        // Check for duplicates of serial number in seatmap of type SeatMap.
        if (hasDuplicateSerialNumber(seatMap)) {
            throw new SeatMapUpdateException("Seat map update failed: there are seats with duplicate seat number.");
        }

        // Refresh the seat code.
        refreshSeatCode(seatMap);
        seatMap.setNumOfSeats(seatMap.getSeats().size());
    }

    // Pre-condition:
    // 1. a user will be warned at the front-end if any seat in the seatmap has active allocations
    // 2. a user can enforce to delete a seatmap even if being warned
    // Post-condition:
    // 1. all the seats together with their active seat allocations in the  seatmap will soft-deleted
    public void deleteSeatMapById(String id) throws SeatMapNotFoundException {

        SeatMap seatMap = getSeatMapById(id);

        for (Seat seat:
             seatMap.getSeats()) {
            for (SeatAllocation seatAllocation:
                 seat.getActiveSeatAllocations()) {
                seatAllocation.setDeleted(true);
            }
            for (SeatAllocation seatAllocation:
                    seat.getInactiveSeatAllocations()) {
                seatAllocation.setDeleted(true);
            }
            seat.setDeleted(true);
        }
    }

    private boolean hasDuplicateSerialNumber(SeatMap seatMap) {

        Collections.sort(seatMap.getSeats());
        for (int count = 0; count < seatMap.getSeats().size() - 1; count++ ) {
            if (seatMap.getSeats().get(count).getSerialNumber().equals(seatMap.getSeats().get(count + 1).getSerialNumber())) {
                return true;
            }
        }
        return false;
    }

    private void refreshSeatCode(SeatMap seatMap) {
        for (Seat seat:
             seatMap.getSeats()) {
            seat.setCode(seatMap.getCode() + seat.getSerialNumber());
        }
    }
}
