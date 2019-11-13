package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.SeatAdminMatchGroupModel;
import capstone.is4103capstone.seat.model.SeatAdminMatchModel;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.SeatRequestAdminMatchNotFoundException;
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
                findUndeletedOneByEntityAndAdminAndHierarchyType(hierarchyId,adminId, HierarchyTypeEnum.valueOf(hierarchyType).ordinal());
        if (!optionalSeatRequestAdminMatch.isPresent()) {
            return false;
        }
        return true;
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

}
