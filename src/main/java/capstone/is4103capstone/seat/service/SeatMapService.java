package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.seat.SeatModelForSeatMap;
import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForAllocationWithHighlight;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForSetup;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.*;
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
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private SeatService seatService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private CompanyFunctionService companyFunctionService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;

    public SeatMapService() {

    }


    // ---------------------------------- Creation -----------------------------------

    // Pre-condition:
    // 1. the seats in the seatmap do not have any crossing coordinates (their squares won't overlap)
    // 2. the seats are already sorted
    // 3. the req passed in has already been checked in terms of user access rights to different offices (before the seat map was drawn in front-end canvas)
    // Post-condition:
    // 1. all the seats in the new seatmap are created
    public String createNewSeatMap(SeatMapModelForSetup createSeatMapReq) throws SeatMapCreationException {

        if (createSeatMapReq.getRegion() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: region not given.");
        }

        if (createSeatMapReq.getCountry() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: country not given.");
        }

        if (createSeatMapReq.getOffice() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: office not given.");
        }

        if (createSeatMapReq.getFloor() == null) {
            throw new SeatMapCreationException("Creation of seat map failed: floor not given.");
        }

        Optional<Office> optionalOffice = officeRepository.findByOfficeNameAndCountryName(createSeatMapReq.getOffice(), createSeatMapReq.getCountry());
        if (!optionalOffice.isPresent()) {
            throw new SeatMapCreationException("Creation of seat map failed: invalid office information.");
        }
        Office office = optionalOffice.get();

        // Check whether there is an existing seat map that has the same floor and the same office as the new one
        if (seatMapRepository.findByOfficeIdAndFloor(office.getId(), createSeatMapReq.getFloor()).isPresent()) {
            throw new SeatMapCreationException("Creation of seat map failed: a seat map already exists on the same floor in the office.");
        }


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

        if (hasDuplicateOrInconsecutiveSerialNumber(newSeatMap)) {
            throw new SeatMapCreationException("Seat map creation failed: there is duplicate or inconsecutive serial number of seats.");
        }

        if (hasInvalidOfficeSeat(newSeatMap)) {
            throw new SeatMapCreationException("Seat map creation failed: there is invalid seat under office. Please check" +
                    " whether the seats are adjacent if there are more than one seat under the same office.");
        }

        // Update numOfFloors in office if needed
        if (!office.getFloors().contains(newSeatMap.getFloor())) {
            office.getFloors().add(newSeatMap.getFloor());
            office.setNumOfFloors(office.getFloors().size());
            officeRepository.save(office);
        }

        // Initialise codes for seatmap and its seats
        // seatmap code example: SG-ORQ-26
        // seat code example: SG-ORQ-26-01
        String countryCode = office.getCountry().getCode();
        String officeCode = office.getCode();
        newSeatMap.setCode(countryCode + "-" + officeCode + "-" + newSeatMap.getFloor());
        newSeatMap.setObjectName(newSeatMap.getCode());
        newSeatMap.setHierachyPath(newSeatMap.getCode());
        newSeatMap = seatMapRepository.save(newSeatMap);

        refreshSeatCode(newSeatMap);

        for (Seat seat: newSeatMap.getSeats()) {
            seatRepository.save(seat);
        }

        return newSeatMap.getId();
    }


    // ---------------------------------- Retrieval -----------------------------------

    public SeatMap getSeatMapById(String id) throws SeatMapNotFoundException {

        Optional<SeatMap> optionalSeatMap = seatMapRepository.findUndeletedById(id);
        if (!optionalSeatMap.isPresent()) {
            throw new SeatMapNotFoundException("Seat map required does not exist.");
        } else {
            SeatMap seatMap = optionalSeatMap.get();
            seatMap.getOffice().getCountry().getRegion();
            seatMap.getSeats().size();
            return seatMap;
        }
    }


    public List<SeatMap> getAllSeatMaps() {
        List<SeatMap> seatMaps = seatMapRepository.findAllUndeleted();
        return seatMaps;
    }


    public List<SeatMap> retrieveSeatMapsWithActiveEmployeeAllocations(String employeeId) throws EmployeeNotFoundException {
        List<SeatMap> seatMaps;
        Employee employee = employeeService.retrieveEmployeeById(employeeId);
        seatMaps = seatMapRepository.findByActiveEmployeeSeatAllocation(employee.getId());
        return seatMaps;
    }

    // Retrieve seat maps that have seats available for a required allocation under a specific function/business unit/team
    // This step is done when an approver tries to see whether the request can be fulfilled; when a request is escalated to a higher hierarchy,
    //    there is no checking done
    public List<SeatMapModelForAllocationWithHighlight> retrieveAvailableSeatMapsForAllocationByHierarchy(String hierarchy, String hierarchyId, SeatAllocation seatAllocation)
            throws SeatMapNotFoundException, TeamNotFoundException, CompanyFunctionNotFoundException {

        List<SeatMapModelForAllocationWithHighlight> seatMapModelsOfAvailableSeatMaps = new ArrayList<>();

        if (hierarchy == null || hierarchy.trim().length() == 0) {
            throw new SeatMapNotFoundException("Fail to retrieve seat maps: hierarchy type is required!");
        }

        if (hierarchyId == null || hierarchyId.trim().length() == 0) {
            throw new SeatMapNotFoundException("Fail to retrieve seat maps: hierarchy detail is required!");
        }

        if (seatAllocation.getSchedule() == null) {
            throw new SeatMapNotFoundException("Fail to retrieve seat maps: schedule info is required!");
        }

        Office office = seatAllocation.getEmployee().getTeam().getOffice();

        // Retrieve the corresponding hierarchy level
        if (hierarchy.equals("TEAM")) {
            Team team = teamService.retrieveTeamById(hierarchyId);
            seatMapModelsOfAvailableSeatMaps.addAll(retrieveAvailableSeatMapsForAllocationByTeam(team, seatAllocation.getSchedule(), seatAllocation.getAllocationType()));
        } else if (hierarchy.equals("BUSINESS_UNIT")) {
            Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findById(hierarchyId);
            if (!optionalBusinessUnit.isPresent()) {
                throw new SeatMapNotFoundException("Fail to retrieve seat maps: business unit with id " + hierarchyId + " does not exist!");
            }
            BusinessUnit businessUnit = optionalBusinessUnit.get();
            seatMapModelsOfAvailableSeatMaps.addAll(retrieveAvailableSeatMapsForAllocationByBusinessUnit(businessUnit,
                    seatAllocation.getSchedule(), seatAllocation.getAllocationType(), office));
        } else if (hierarchy.equals("COMPANY_FUNCTION")) {
            CompanyFunction function = companyFunctionService.retrieveCompanyFunctionById(hierarchyId);
            seatMapModelsOfAvailableSeatMaps.addAll(retrieveAvailableSeatMapsForAllocationByFunction(function,
                    seatAllocation.getSchedule(), seatAllocation.getAllocationType(), office));
        } else {
            throw new SeatMapNotFoundException("Fail to retrieve seat maps: hierarchy type is invalid!");
        }

        return seatMapModelsOfAvailableSeatMaps;
    }


    private List<SeatMapModelForAllocationWithHighlight> retrieveAvailableSeatMapsForAllocationByTeam(Team team, Schedule schedule, SeatAllocationTypeEnum seatAllocationTypeEnum) {

        List<SeatMapModelForAllocationWithHighlight> seatMapModels = new ArrayList<>();
        List<SeatMap> availableSeatMaps = seatMapRepository.findOnesWithSeatsAllocatedToTeam(team.getId());
        // Check each seat inside the seat map
        // - whether it's allocated to the team
        // - whether it has schedule clashes
        for (SeatMap seatMap :
                availableSeatMaps) {

            SeatMapModelForAllocationWithHighlight seatMapModelForAllocationWithHighlight = new SeatMapModelForAllocationWithHighlight();
            seatMapModelForAllocationWithHighlight.setId(seatMap.getId());
            seatMapModelForAllocationWithHighlight.setCode(seatMap.getCode());
            List<SeatModelWithHighlighting> seatModels = new ArrayList<>();

            boolean shouldBeIncluded = false;

            for (Seat seat :
                    seatMap.getSeats()) {
                if (seat.getTeamAssigned() != null && seat.getTeamAssigned().getId().equals(team.getId())) {
                    // Check schedule clashes
                    if (!seatService.hasScheduleClash(seat, schedule, seatAllocationTypeEnum)) {
                        // its seat map should be included; need to convert the seat as well
                        shouldBeIncluded = true;
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, true));
                    } else {
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, false));
                    }
                }
            }

            if (shouldBeIncluded) {
                seatMapModelForAllocationWithHighlight.setSeatModelsForAllocationViaSeatMap(seatModels);
                seatMapModels.add(seatMapModelForAllocationWithHighlight);
            }
        }
        return seatMapModels;
    }


    // TODO: must ensure that the user does the checking has access to the office where the teams are located
    private List<SeatMapModelForAllocationWithHighlight> retrieveAvailableSeatMapsForAllocationByBusinessUnit(BusinessUnit businessUnit,
                                                                                                              Schedule schedule,
                                                                                                              SeatAllocationTypeEnum seatAllocationTypeEnum,
                                                                                                              Office office) {

        List<SeatMapModelForAllocationWithHighlight> seatMapModels = new ArrayList<>();
        List<SeatMap> availableSeatMaps = seatMapRepository.findOnesWithSeatsAllocatedToBusinessUnit(businessUnit.getId());

        for (SeatMap seatMap :
                availableSeatMaps) {

            SeatMapModelForAllocationWithHighlight seatMapModelForAllocationWithHighlight = new SeatMapModelForAllocationWithHighlight();
            seatMapModelForAllocationWithHighlight.setId(seatMap.getId());
            seatMapModelForAllocationWithHighlight.setCode(seatMap.getCode());
            List<SeatModelWithHighlighting> seatModels = new ArrayList<>();

            if (!seatMap.getOffice().getId().equals(office.getId())) {
                continue;
            }
            boolean shouldBeIncluded = false;

            for (Seat seat :
                    seatMap.getSeats()) {
                if (seat.getBusinessUnitAssigned() != null && seat.getBusinessUnitAssigned().getId().equals(businessUnit.getId())) {
                    // Check schedule clashes
                    if (!seatService.hasScheduleClash(seat, schedule, seatAllocationTypeEnum)) {
                        // its seat map should be included; need to convert the seat as well
                        shouldBeIncluded = true;
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, true));
                    } else {
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, false));
                    }
                }
            }

            if (shouldBeIncluded) {
                seatMapModelForAllocationWithHighlight.setSeatModelsForAllocationViaSeatMap(seatModels);
                seatMapModels.add(seatMapModelForAllocationWithHighlight);
            }
        }

        return seatMapModels;
    }


    private List<SeatMapModelForAllocationWithHighlight> retrieveAvailableSeatMapsForAllocationByFunction(CompanyFunction function,
                                                                                                          Schedule schedule,
                                                                                                          SeatAllocationTypeEnum seatAllocationTypeEnum,
                                                                                                          Office office) {

        List<SeatMapModelForAllocationWithHighlight> seatMapModels = new ArrayList<>();
        List<SeatMap> availableSeatMaps = seatMapRepository.findOnesWithSeatsAllocatedToFunction(function.getId());

        for (SeatMap seatMap :
                availableSeatMaps) {

            SeatMapModelForAllocationWithHighlight seatMapModelForAllocationWithHighlight = new SeatMapModelForAllocationWithHighlight();
            seatMapModelForAllocationWithHighlight.setId(seatMap.getId());
            seatMapModelForAllocationWithHighlight.setCode(seatMap.getCode());
            List<SeatModelWithHighlighting> seatModels = new ArrayList<>();

            if (!seatMap.getOffice().getId().equals(office.getId())) {
                continue;
            }
            boolean shouldBeIncluded = false;

            for (Seat seat :
                    seatMap.getSeats()) {
                if (seat.getFunctionAssigned() != null && seat.getFunctionAssigned().getId().equals(function.getId())) {
                    // Check schedule clashes
                    if (!seatService.hasScheduleClash(seat, schedule, seatAllocationTypeEnum)) {
                        // its seat map should be included; need to convert the seat as well
                        shouldBeIncluded = true;
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, true));
                    } else {
                        seatModels.add(entityModelConversionService.convertSeatToSeatModelWithHighlighting(seat, false));
                    }
                }
            }

            if (shouldBeIncluded) {
                seatMapModelForAllocationWithHighlight.setSeatModelsForAllocationViaSeatMap(seatModels);
                seatMapModels.add(seatMapModelForAllocationWithHighlight);
            }
        }

        return seatMapModels;
    }


    public Optional<SeatMap> retrieveSeatMapWithActiveEmployeeAllocations(String seatMapId, String employeeId) throws EmployeeNotFoundException, SeatMapNotFoundException {
        Employee employee = employeeService.retrieveEmployeeById(employeeId);
        return seatMapRepository.findBySeatMapIdAndActiveEmployeeSeatAllocation(seatMapId, employee.getId());
    }



    // ---------------------------------- Update -----------------------------------

    // Pre-condition:
    // 1. Update of seat maps does not allow changing a normal seat into an office seat (vice versa). This must be done through
    //    deletion of the original seat first accompanied by the addition of new seat (with the new seat type).
    public void updateSeatMap(SeatMapModelForSetup seatMapModelForSetup) throws SeatMapUpdateException, SeatMapNotFoundException {

        System.out.println("******************** Update Seat Map ********************");

        // Validation of passed in seat map information
        if (seatMapModelForSetup.getId() == null || seatMapModelForSetup.getId().trim().length() == 0) {
            throw new SeatMapUpdateException("Seat map update failed: insufficient seat map info given.");
        }
        Collections.sort(seatMapModelForSetup.getSeats());
        if (hasDuplicateSeatModelIdOrSerialNumber(seatMapModelForSetup)) {
            throw new SeatMapUpdateException("Seat map update failed: there are seats with duplicate serial number or id.");
        }

        SeatMap seatMap = getSeatMapById(seatMapModelForSetup.getId().trim());
        List<String> originalSeatsIds = new ArrayList<>();
        for (Seat originalSeat:
             seatMap.getSeats()) {
            originalSeatsIds.add(originalSeat.getId());
        }

        System.out.println("********** the number of original seats: " + originalSeatsIds.size() + " **********");
        // Find the seat in the original seat map.
        for (SeatModelForSeatMap seatModel:
             seatMapModelForSetup.getSeats()) {
            if (seatModel.getId() == null || seatModel.getId().trim().length() == 0) {
                // This is a new seat. Need to create this seat and add to the existing seat map.
                Seat newSeat = seatService.createNewSeat(seatModel);
                seatMap.getSeats().add(newSeat);
                newSeat.setSeatMap(seatMap);
            } else {
                // Find the seat in the original seat map.
                boolean doesSeatExist = false;

                for (Seat seatToUpdate:
                     seatMap.getSeats()) {
                    if (seatToUpdate.getId().equals(seatModel.getId())) {
                        doesSeatExist = true;

                        System.out.println("-------------------------------------------");
                        // Update seat information directly, even if the information is actually the same
                        System.out.println("***** updating seat: " + seatToUpdate.getId() + " *****");
                        System.out.println("***** original X: " + seatToUpdate.getxCoordinate() + " *****");
                        System.out.println("***** original Y: " + seatToUpdate.getyCoordinate() + " *****");
                        seatToUpdate.setSerialNumber(seatModel.getSerialNumber());
                        seatToUpdate.setxCoordinate(seatModel.getX());
                        seatToUpdate.setyCoordinate(seatModel.getY());
                        System.out.println("***** new X: " + seatToUpdate.getxCoordinate() + " *****");
                        System.out.println("***** new Y: " + seatToUpdate.getyCoordinate() + " *****");

                        if (seatToUpdate.isUnderOffice()) {
                            seatToUpdate.setAdjacentSeatSeqNum(seatModel.getAdjacentSeatSeqNum());
                        }
                        originalSeatsIds.remove(seatToUpdate.getId());

                        break;
                    }
                }

                if (!doesSeatExist) {
                    throw new SeatMapUpdateException("Seat map update failed: invalid seat info given. There is no seat" +
                            "with id " + seatModel.getId() + "or the seat map does not have such seat");
                }
            }
        }

        System.out.println("********** Pick out extra seats **********");
        // Pick out the seats that are in the original seat map but not in the updated seat map.
        ListIterator<Seat> seatListIterator = seatMap.getSeats().listIterator();
        List<Seat> extraSeats = new ArrayList<>();
        while (seatListIterator.hasNext()) {
            Seat thisSeat = seatListIterator.next();
            if (originalSeatsIds.contains(thisSeat.getId())) {
                extraSeats.add(thisSeat);
                seatListIterator.remove();
            }
        }

        System.out.println("********** Check for duplicates/consecutive condition of serial number **********");
        // Check for duplicates of serial number in seatmap of type SeatMap.
        Collections.sort(seatMap.getSeats());
        if (hasDuplicateOrInconsecutiveSerialNumber(seatMap)) {
            throw new SeatMapUpdateException("Seat map update failed: there are seats with duplicate or inconsecutive seat numbers.");
        }
        if (hasInvalidOfficeSeat(seatMap)) {
            throw new SeatMapUpdateException("Seat map update failed: there are seats under office with invalid adjacent seat serial number.");
        }

        seatService.deleteSeats(extraSeats);
        System.out.println("********** Save all the changes **********");
        // Save all the changes.
        // Need to set all the seats' code to null to avoid SQLIntegrityConstraintViolationException: duplicate entry of code
        for (Seat updatedSeat:
                seatMap.getSeats()) {
            updatedSeat.setCode(null);
            seatRepository.saveAndFlush(updatedSeat);
        }

        refreshSeatCode(seatMap);
        seatMap.setNumOfSeats(seatMap.getSeats().size());

        for (Seat updatedSeat:
             seatMap.getSeats()) {
            System.out.println("-------------------------------------------");
            System.out.println("***** saving seat: " + updatedSeat.getId() + " *****");
            System.out.println("***** serial number: " + updatedSeat.getSerialNumber() + " *****");
            System.out.println("***** X coordinate: " + updatedSeat.getxCoordinate() + " *****");
            System.out.println("***** Y coordinate: " + updatedSeat.getyCoordinate() + " *****");
            seatRepository.saveAndFlush(updatedSeat);
        }
        seatMapRepository.saveAndFlush(seatMap);
    }


    // ---------------------------------- Deletion -----------------------------------

    // Pre-condition:
    // 1. a user will be warned at the front-end if any seat in the seatmap has active allocations
    // 2. a user can enforce to delete a seat map even if being warned
    // Post-condition:
    // 1. all the seats together with their seat allocations in the seat map will be soft-deleted
    public void deleteSeatMapById(String id) throws SeatMapNotFoundException {

        SeatMap seatMap = getSeatMapById(id);

        seatService.deleteSeats(seatMap.getSeats());

        Office office = seatMap.getOffice();

        // Update numOfFloors in office if needed
        if (!seatMapRepository.findByOfficeIdAndFloor(office.getId(), seatMap.getFloor()).isPresent()) {
            office.getFloors().remove(seatMap.getFloor());
            office.setNumOfFloors(office.getFloors().size());
            officeRepository.save(office);
        }

        seatMap.setDeleted(true);
        seatMap.setCode(null);
        seatMapRepository.save(seatMap);
    }


    // -----------------------------------------------Helper methods-----------------------------------------------

    private boolean hasDuplicateSeatModelIdOrSerialNumber(SeatMapModelForSetup seatMap) {
        List<String> ids = new ArrayList<>();

        for (int count = 0; count < seatMap.getSeats().size() - 1; count++ ) {
            SeatModelForSeatMap thisSeat = seatMap.getSeats().get(count);
            if (thisSeat.getSerialNumber().equals(seatMap.getSeats().get(count + 1).getSerialNumber())) {
                return true;
            } else if (thisSeat.getId() != null && thisSeat.getId().trim().length() > 0){
                if (ids.contains(thisSeat.getId())) {
                    return true;
                } else {
                    ids.add(thisSeat.getId());
                }
            }
        }

        return false;
    }


    private boolean hasDuplicateOrInconsecutiveSerialNumber(SeatMap seatMap) {

        for (int count = 0; count < seatMap.getSeats().size() - 1; count++ ) {
            if (seatMap.getSeats().get(count).getSerialNumber().equals(seatMap.getSeats().get(count + 1).getSerialNumber())
                | !seatMap.getSeats().get(count).getSerialNumber().equals(seatMap.getSeats().get(count + 1).getSerialNumber() - 1)) {
                return true;
            }
        }
        return false;
    }


    private boolean hasInvalidOfficeSeat(SeatMap seatMap) {
        HashMap<Integer, Integer> adjacentSeatSeqNumPairs = new HashMap<>();
        for (Seat seat :
                seatMap.getSeats()) {
            if (seat.isUnderOffice() && seat.getAdjacentSeatSeqNum() != null) {
                Integer adjacentSeatSeqNum = seat.getAdjacentSeatSeqNum();
                if (adjacentSeatSeqNum <= 0) {
                    return true;
                } else {
                    if (adjacentSeatSeqNumPairs.containsKey(seat.getSerialNumber())) {
                        if (adjacentSeatSeqNumPairs.get(seat.getSerialNumber()) != adjacentSeatSeqNum) {
                            return true;
                        } else {
                            adjacentSeatSeqNumPairs.remove(seat.getSerialNumber());
                        }
                    } else {
                        adjacentSeatSeqNumPairs.put(adjacentSeatSeqNum, seat.getSerialNumber());
                    }
                }
            }
        }

        if (adjacentSeatSeqNumPairs.size() > 0) { // there is some seat that doesn't have any matched adjacent seat
            return true;
        }

        return false;
    }


    private void refreshSeatCode(SeatMap seatMap) {
        for (Seat seat: seatMap.getSeats()) {
            // Calculate how many 0s are needed
            Integer numOf0Count = 0;
            Integer bigCount = 0;
            Integer smallCount = 0;

            Integer numOfSeats = seatMap.getSeats().size();
            while (numOfSeats > 0) {
                bigCount++;
                numOfSeats /= 10;
            }

            Integer serialSize = seat.getSerialNumber();
            while (serialSize > 0) {
                smallCount++;
                serialSize /= 10;
            }
            numOf0Count = bigCount - smallCount;
            String zeroString = "";
            while(numOf0Count >= 0) {
                zeroString += "0";
                numOf0Count --;
            }

            String finalString = seatMap.getCode() + "-" + zeroString + seat.getSerialNumber();
            seat.setCode(finalString);
            seat.setHierachyPath(finalString);
            seat.setObjectName(finalString);
        }
    }
}
