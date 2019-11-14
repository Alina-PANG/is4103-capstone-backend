package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.SeatAdminMatchGroupModel;
import capstone.is4103capstone.seat.model.SeatAdminMatchModel;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRequestRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatAdminMatchService {

    @Autowired
    private SeatAdminMatchRepository seatAdminMatchRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SeatAllocationRequestRepository seatAllocationRequestRepository;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SeatManagementGeneralService seatManagementGeneralService;


    // ---------------------------------- Create New Rights ----------------------------------

    // Note: currently there is no mechanism in place to check whether the user creating the new seat admin match has the right to do this action.
    //          Checking logic should be put in place for security reasons.
    public void createNewSeatAdminMatch(String entityId, String hierarchyType, String adminId) throws CreateSeatAdminMatchException, InvalidInputException {

        if (entityId == null || entityId.trim().length() == 0) {
            throw new InvalidInputException("Creating new seat admin access right failed: business entity info is missing.");
        }
        entityId = entityId.trim();
        if (hierarchyType == null || hierarchyType.trim().length() == 0) {
            throw new InvalidInputException("Creating new seat admin access right failed: hierarchy type info is missing.");
        }
        hierarchyType = hierarchyType.trim();
        if (adminId == null || adminId.trim().length() == 0) {
            throw new InvalidInputException("Creating new seat admin access right failed: admin user info is missing.");
        }
        adminId = adminId.trim();

        Optional<SeatAdminMatch> optionalSeatAdminMatch = seatAdminMatchRepository.findUndeletedOneByEntityAndAdminId(entityId, adminId);
        if (optionalSeatAdminMatch.isPresent()) {
            throw new CreateSeatAdminMatchException("Creating new seat admin access right failed: the access right already exists.");
        }

        try {
            Employee admin = employeeService.getEmployeeEntityByUuid(adminId);
            SeatAdminMatch newMatch = new SeatAdminMatch();
            newMatch.setSeatAdmin(admin);
            // Get the entity right
            DBEntityTemplate entity = seatManagementGeneralService.validateBusinessEntityWithHierarchyType(hierarchyType, entityId);
            if (entity.getClass().getSimpleName().equals("CompanyFunction")) {
                newMatch.setHierarchyId(entityId);
                newMatch.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
            } else if (entity.getClass().getSimpleName().equals("BusinessUnit")) {
                newMatch.setHierarchyId(entityId);
                newMatch.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
            } else if (entity.getClass().getSimpleName().equals("Team")) {
                newMatch.setHierarchyId(entityId);
                newMatch.setHierarchyType(HierarchyTypeEnum.TEAM);
            } else if (entity.getClass().getSimpleName().equals("Office")) {
                newMatch.setHierarchyId(entityId);
                newMatch.setHierarchyType(HierarchyTypeEnum.OFFICE);
            } else if (entity.getClass().getSimpleName().equals("SeatMap")) {
                newMatch.setHierarchyId(entityId);
                newMatch.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
            }
            seatAdminMatchRepository.save(newMatch);
        } catch (Exception ex) {
            throw new CreateSeatAdminMatchException("Creating new seat admin access right failed: " +ex.getMessage());
        }
    }

    // ---------------------------------- Retrieve ----------------------------------

    public SeatAdminMatch retrieveMatchByHierarchyIdAndAdminId(String hierarchyId, String adminId) throws SeatRequestAdminMatchNotFoundException {
        if (hierarchyId == null || hierarchyId.trim().length() == 0) {
            throw new SeatRequestAdminMatchNotFoundException("Invalid hierarchy ID given!");
        }
        Optional<SeatAdminMatch> optionalSeatRequestAdminMatch = seatAdminMatchRepository.findUndeletedOneByEntityAndAdminId(hierarchyId, adminId);
        if (!optionalSeatRequestAdminMatch.isPresent()) {
            throw new SeatRequestAdminMatchNotFoundException("Seat Request Admin Match under hierarchy ID " + hierarchyId + " does not exist!");
        }
        return optionalSeatRequestAdminMatch.get();
    }

    public SeatAdminMatchGroupModel retrieveAccessibleHierarchyLevels() {

        SeatAdminMatchGroupModel seatAdminMatchGroupModel = new SeatAdminMatchGroupModel();
        List<SeatAdminMatchModel> groupModels = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();

        List<SeatAdminMatch> seatAdminMatches = seatAdminMatchRepository.findUndeletedOnesByAdminId(currentEmployee.getId());
        for (SeatAdminMatch match :
                seatAdminMatches) {
            GroupModel groupModel = new GroupModel();
            if (match.getHierarchyType().equals(HierarchyTypeEnum.COMPANY_FUNCTION)) {
                CompanyFunction companyFunction = functionRepository.findById(match.getHierarchyId()).get();
                groupModel.setId(companyFunction.getId());
                groupModel.setName(companyFunction.getObjectName());
                groupModel.setCode(companyFunction.getCode());
                groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.COMPANY_FUNCTION.toString()));
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.BUSINESS_UNIT)) {
                BusinessUnit businessUnit = businessUnitRepository.findByIdNonDeleted(match.getHierarchyId()).get();
                groupModel.setId(businessUnit.getId());
                groupModel.setName(businessUnit.getObjectName());
                groupModel.setCode(businessUnit.getCode());
                groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.BUSINESS_UNIT.toString()));
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.TEAM)) {
                Team team = teamRepository.findById(match.getHierarchyId()).get();
                groupModel.setId(team.getId());
                groupModel.setName(team.getObjectName());
                groupModel.setCode(team.getCode());
                groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.TEAM.toString()));
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.OFFICE)) {
                Office office = officeRepository.findById(match.getHierarchyId()).get();
                groupModel.setId(office.getId());
                groupModel.setName(office.getObjectName());
                groupModel.setCode(office.getCode());
                groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.OFFICE.toString()));
            } else {
                SeatMap seatMap = seatMapRepository.findById(match.getHierarchyId()).get();
                groupModel.setId(seatMap.getId());
                groupModel.setName(seatMap.getObjectName());
                groupModel.setCode(seatMap.getCode());
                groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.OFFICE_FLOOR.toString()));
            }
        }
        seatAdminMatchGroupModel.setAccessibleEntities(groupModels);
        return seatAdminMatchGroupModel;
    }

    public SeatAdminMatchGroupModel retrieveAccessibleOffices() {

        SeatAdminMatchGroupModel seatAdminMatchGroupModel = new SeatAdminMatchGroupModel();
        List<SeatAdminMatchModel> groupModels = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();

        List<String> officeIds = new ArrayList<>();

        List<SeatAdminMatch> seatAdminMatches = seatAdminMatchRepository.findUndeletedOnesByAdminId(currentEmployee.getId());
        for (SeatAdminMatch match :
                seatAdminMatches) {
            GroupModel groupModel = new GroupModel();
            if (match.getHierarchyType().equals(HierarchyTypeEnum.COMPANY_FUNCTION)) {
                CompanyFunction companyFunction = functionRepository.findById(match.getHierarchyId()).get();
                List<Office> offices = officeRepository.findOnesUnderCompanyFunction(companyFunction.getId());
                for (Office office :
                        offices) {
                    if (!officeIds.contains(office.getId())) {
                        officeIds.add(office.getId());
                    }
                }
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.BUSINESS_UNIT)) {
                BusinessUnit businessUnit = businessUnitRepository.findByIdNonDeleted(match.getHierarchyId()).get();
                List<Office> offices = officeRepository.findOnesUnderBusinessUnit(businessUnit.getId());
                for (Office office :
                        offices) {
                    if (!officeIds.contains(office.getId())) {
                        officeIds.add(office.getId());
                    }
                }
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.TEAM)) {
                Team team = teamRepository.findById(match.getHierarchyId()).get();
                if (!officeIds.contains(team.getOffice().getId())) {
                    officeIds.add(team.getOffice().getId());
                }
            } else if (match.getHierarchyType().equals(HierarchyTypeEnum.OFFICE)) {
                Office office = officeRepository.findById(match.getHierarchyId()).get();
                if (!officeIds.contains(office.getId())) {
                    officeIds.add(office.getId());
                }
            } else {
                SeatMap seatMap = seatMapRepository.findById(match.getHierarchyId()).get();
                if (!officeIds.contains(seatMap.getOffice().getId())) {
                    officeIds.add(seatMap.getOffice().getId());
                }
            }
        }

        for (String officeId :
                officeIds) {
            GroupModel groupModel = new GroupModel();
            Office office = officeRepository.findById(officeId).get();
            groupModel.setId(officeId);
            groupModel.setName(office.getObjectName());
            groupModel.setCode(office.getCode());
            groupModels.add(new SeatAdminMatchModel(groupModel, HierarchyTypeEnum.OFFICE.toString()));
        }
        seatAdminMatchGroupModel.setAccessibleEntities(groupModels);
        return seatAdminMatchGroupModel;
    }



    // ---------------------------------- Delete Rights ----------------------------------

    public void deleteSeatAdminMatch(String entityId, String adminId) throws SeatAdminMatchNotFoundException, InvalidInputException, DeleteSeatAdminMatchException {

        if (entityId == null || entityId.trim().length() == 0) {
            throw new InvalidInputException("Deleting new seat admin access right failed: business entity info is missing.");
        }
        entityId = entityId.trim();
        if (adminId == null || adminId.trim().length() == 0) {
            throw new InvalidInputException("Deleting new seat admin access right failed: admin user info is missing.");
        }
        adminId = adminId.trim();

        Optional<SeatAdminMatch> optionalSeatAdminMatch = seatAdminMatchRepository.findUndeletedOneByEntityAndAdminId(entityId, adminId);
        if (optionalSeatAdminMatch.isPresent()) {
            SeatAdminMatch seatAdminMatch = optionalSeatAdminMatch.get();
            // Check whether the admin has any pending seat allocation requests to handle
            List<SeatAllocationRequest> pendingRequestsAssignedToAdmin = seatAllocationRequestRepository.findUndeletedPendingOnesByReviewerId(adminId);
            if (pendingRequestsAssignedToAdmin != null || pendingRequestsAssignedToAdmin.size() > 0) {
                throw new DeleteSeatAdminMatchException("Deleting new seat admin access right failed: the user has pending seat allocation requests assigned to him/her.");
            }

            List<SeatAllocationRequest> pendingRequestsCreatedByAdmin = seatAllocationRequestRepository.findUndeletedPendingOnesByRequesterId(adminId);
            if (pendingRequestsCreatedByAdmin != null || pendingRequestsCreatedByAdmin.size() > 0) {
                throw new DeleteSeatAdminMatchException("Deleting new seat admin access right failed: the user has pending seat allocation requests created by him/her.");
            }
            seatAdminMatchRepository.delete(seatAdminMatch);
        } else {
            throw new SeatAdminMatchNotFoundException("Deleting seat admin right failed: the seat admin right does not exist.");
        }
    }

    // ---------------------------------- Logic Checking ----------------------------------

    public boolean passCheckOfAdminRightByHierarchyIdLevelAndAdmin(String hierarchyId, String hierarchyType, String adminId)
            throws SeatRequestAdminMatchNotFoundException {
        Optional<SeatAdminMatch> optionalSeatRequestAdminMatch = seatAdminMatchRepository.
                findUndeletedOneByEntityAndAdminAndHierarchyType(hierarchyId,adminId, HierarchyTypeEnum.valueOf(hierarchyType).ordinal());
        if (!optionalSeatRequestAdminMatch.isPresent()) {
            return false;
        }
        return true;
    }

}
