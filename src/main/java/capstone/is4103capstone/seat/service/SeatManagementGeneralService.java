package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRequestRepository;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatUtilisationLogRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
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
}
