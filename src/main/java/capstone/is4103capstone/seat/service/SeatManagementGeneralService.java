package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRequestRepository;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private SeatRequestAdminMatchRepository seatRequestAdminMatchRepository;

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
        List<SeatRequestAdminMatch> seatRequestAdminMatches = seatRequestAdminMatchRepository.findUndeletedOnesByAdminId(employee.getId());
        for (SeatRequestAdminMatch match :
                seatRequestAdminMatches) {
            match.setDeleted(true);
            match.setLastModifiedBy("SYSTEM");
            seatRequestAdminMatchRepository.save(match);
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
