package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.SeatRequestAdminMatchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatAdminMatchService {

    @Autowired
    private SeatAdminMatchRepository seatAdminMatchRepository;

    public SeatAdminMatch retrieveMatchByHierarchyId(String hierarchyId) throws SeatRequestAdminMatchNotFoundException {
        if (hierarchyId == null || hierarchyId.trim().length() == 0) {
            throw new SeatRequestAdminMatchNotFoundException("Invalid hierarchy ID given!");
        }
        Optional<SeatAdminMatch> optionalSeatRequestAdminMatch = seatAdminMatchRepository.findUndeletedByHierarchyId(hierarchyId);
        if (!optionalSeatRequestAdminMatch.isPresent()) {
            throw new SeatRequestAdminMatchNotFoundException("Seat Request Admin Match under hierarchy ID " + hierarchyId + " does not exist!");
        }
        return optionalSeatRequestAdminMatch.get();
    }

    public boolean passCheckOfAdminRightByHierarchyIdLevelAndAdmin(String hierarchyId, String hierarchyType, String adminId)
            throws SeatRequestAdminMatchNotFoundException {
        Optional<SeatAdminMatch> optionalSeatRequestAdminMatch = seatAdminMatchRepository.
                findUndeletedOnesByEntityAndAdminAndHierarchyType(hierarchyId,adminId, HierarchyTypeEnum.valueOf(hierarchyType).ordinal());
        if (!optionalSeatRequestAdminMatch.isPresent()) {
            return false;
        }
        return true;
    }
}
