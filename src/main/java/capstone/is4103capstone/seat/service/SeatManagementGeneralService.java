package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.*;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRequestRepository;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatUtilisationLogRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeatManagementGeneralService {

    @Autowired
    private SeatAllocationService seatAllocationService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private BusinessUnitService businessUnitService;
    @Autowired
    private CompanyFunctionService companyFunctionService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SeatMapService seatMapService;

    @Autowired
    private EmployeeOfficeWorkingScheduleRepository employeeOfficeWorkingScheduleRepository;
    @Autowired
    private SeatAllocationRequestRepository seatAllocationRequestRepository;
    @Autowired
    private ApprovalForRequestRepository approvalForRequestRepository;
    @Autowired
    private SeatAdminMatchRepository seatAdminMatchRepository;
    @Autowired
    private SeatUtilisationLogRepository seatUtilisationLogRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    public void deleteSeatManagementDataOfEmployee(Employee employee) {
        // Seat allocations (together with their schedules)
        seatAllocationService.deleteActiveAllocationsByEmployeeId(employee.getId());
        seatAllocationService.deleteInactiveAllocationsByEmployeeId(employee.getId());

        // Working schedule
        List<EmployeeOfficeWorkingSchedule> employeeOfficeWorkingSchedules = employeeOfficeWorkingScheduleRepository.findUndeletedOnesByEmployeeId(employee.getId());
        for (EmployeeOfficeWorkingSchedule schedule :
                employeeOfficeWorkingSchedules) {
            schedule.setDeleted(true);
            schedule.setLastModifiedBy("SYSTEM");
            employeeOfficeWorkingScheduleRepository.save(schedule);
        }

        // Seat allocation requests (together with their approval tickets)
        deleteAssociatedSeatAllocationRequests(employee.getId());

        // Seat admin matches
        List<SeatAdminMatch> seatAdminMatches = seatAdminMatchRepository.findUndeletedOnesByAdminId(employee.getId());
        for (SeatAdminMatch match :
                seatAdminMatches) {
            match.setDeleted(true);
            match.setLastModifiedBy("SYSTEM");
            seatAdminMatchRepository.save(match);
        }
    }

    private void deleteAssociatedSeatAllocationRequests(String employeeId) {
        // Pending seat requests created for this employee
        List<SeatAllocationRequest> seatAllocationRequestsCreatedForThisEmployee = seatAllocationRequestRepository.findUndeletedPendingOnesByEmployeeOfAllocationId(employeeId);
        for (SeatAllocationRequest request :
                seatAllocationRequestsCreatedForThisEmployee) {
            request.setDeleted(true);
            request.setLastModifiedBy("SYSTEM");
            for (ApprovalForRequest ticket :
                    request.getApprovalTickets()) {
                ticket.setDeleted(true);
                ticket.setLastModifiedBy("SYSTEM");
                approvalForRequestRepository.save(ticket);
            }
            seatAllocationRequestRepository.save(request);
        }

        // Pending seat requests created by this employee
        List<SeatAllocationRequest> seatAllocationRequestsCreatedByThisEmployee = seatAllocationRequestRepository.findUndeletedPendingOnesByRequesterId(employeeId);
        for (SeatAllocationRequest request :
                seatAllocationRequestsCreatedByThisEmployee) {
            request.setDeleted(true);
            request.setLastModifiedBy("SYSTEM");
            for (ApprovalForRequest ticket :
                    request.getApprovalTickets()) {
                ticket.setDeleted(true);
                ticket.setLastModifiedBy("SYSTEM");
                approvalForRequestRepository.save(ticket);
            }
            seatAllocationRequestRepository.save(request);
        }

        // Pending seat requests assigned to this employee
        List<SeatAllocationRequest> seatAllocationRequestsAssignedToThisEmployee = seatAllocationRequestRepository.findUndeletedPendingOnesByReviewerId(employeeId);
        for (SeatAllocationRequest request :
                seatAllocationRequestsAssignedToThisEmployee) {
            request.setDeleted(true);
            request.setLastModifiedBy("SYSTEM");
            for (ApprovalForRequest ticket :
                    request.getApprovalTickets()) {
                ticket.setDeleted(true);
                ticket.setLastModifiedBy("SYSTEM");
                approvalForRequestRepository.save(ticket);
            }
            seatAllocationRequestRepository.save(request);
        }
    }

    public DBEntityTemplate validateBusinessEntityWithHierarchyType(String hierarchyType, String levelEntityId) throws InvalidInputException {

        if (hierarchyType == null || hierarchyType.trim().length() == 0 || levelEntityId == null || levelEntityId.trim().length() == 0) {
            throw new InvalidInputException("Business entity information input is incomplete.");
        }

        if (hierarchyType.equals("COMPANY_FUNCTION")) {
            CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(levelEntityId);
            return companyFunction;
        } else if (hierarchyType.equals("BUSINESS_UNIT")) {
            BusinessUnit businessUnit = businessUnitService.retrieveBusinessUnitById(levelEntityId);
            return businessUnit;
        } else if (hierarchyType.equals("TEAM")) {
            Team team = teamService.retrieveTeamById(levelEntityId);
            return team;
        } else if (hierarchyType.equals("OFFICE")) {
            try {
                Office office = officeService.getOfficeEntityByUuid(levelEntityId);
                return office;
            } catch (Exception ex) {
                throw new InvalidInputException("Office with ID " + levelEntityId + " does not exist.");
            }
        } else if (hierarchyType.equals("OFFICE_FLOOR")) {
            SeatMap seatMap = seatMapService.getSeatMapById(levelEntityId);
            return seatMap;
        } else {
            throw new InvalidInputException("Invalid hierarchy type.");
        }
    }
}
