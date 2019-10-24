package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.*;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.ApproveSeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.CreateSeatAllocationRequestModel;
import capstone.is4103capstone.seat.repository.*;
import capstone.is4103capstone.util.SeatManagementEntityCodeHPGenerator;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatAllocationRequestService {

    private static SeatManagementEntityCodeHPGenerator seatManagementEntityCodeHPGenerator = new SeatManagementEntityCodeHPGenerator();

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;
    @Autowired
    private SeatRequestAdminMatchService seatRequestAdminMatchService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private BusinessUnitService businessUnitService;
    @Autowired
    private CompanyFunctionService companyFunctionService;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatAllocationRequestRepository seatAllocationRequestRepository;
    @Autowired
    private ApprovalForRequestRepository approvalForRequestRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private SeatAllocationInactivationLogRepository seatAllocationInactivationLogRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // ---------------------------------- Create New ----------------------------------

    // Only a team's seat admin (by default can be the team manager) can create a seat allocation request
    public void createNewSeatAllocationRequest(CreateSeatAllocationRequestModel createSeatAllocationRequestModel) {
        SeatAllocationRequest seatAllocationRequest = new SeatAllocationRequest();

        if (createSeatAllocationRequestModel.getRequesterId() == null || createSeatAllocationRequestModel.getRequesterId().trim().length() == 0) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: requester info is required!");
        }
        String requesterId = createSeatAllocationRequestModel.getRequesterId();
        Employee requester = employeeService.retrieveEmployeeById(requesterId);
        // Validate the requester: check whether the requester has the right to create a seat allocation request
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(requester.getTeam().getId());
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(requesterId)) {
            throw new CreateSeatAllocationRequestRightException("Creating seat allocation request failed: employee with ID " + requesterId +
                    " does not have the right to create such request!");
        }

        if (createSeatAllocationRequestModel.getEmployeeIdOfAllocation() == null || createSeatAllocationRequestModel.getEmployeeIdOfAllocation().trim().length() == 0) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: employee info of the allocation is required!");
        }
        String employeeIdOfAllocation = createSeatAllocationRequestModel.getEmployeeIdOfAllocation();
        Employee employeeOfAllocation = employeeService.retrieveEmployeeById(employeeIdOfAllocation);
        // Validate the employee: check whether the employee is under the team managed by the requester
        if (!employeeOfAllocation.getTeam().getId().equals(requester.getTeam().getId())) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: employee " + employeeIdOfAllocation +
                    " does not belong to the team managed by the requester!");
        }


        // Allocation type and schedule
        if (createSeatAllocationRequestModel.getAllocationType() == null || createSeatAllocationRequestModel.getAllocationType().trim().length() == 0) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: allocation type is required!");
        }
        if (createSeatAllocationRequestModel.getAllocationType().equals("FIXED")) {
            seatAllocationRequest.setAllocationType(SeatAllocationTypeEnum.FIXED);
        } else if (createSeatAllocationRequestModel.getAllocationType().equals("SHARED")) {
            seatAllocationRequest.setAllocationType(SeatAllocationTypeEnum.SHARED);
        } else if (createSeatAllocationRequestModel.getAllocationType().equals("HOTDESK")) {
            seatAllocationRequest.setAllocationType(SeatAllocationTypeEnum.HOTDESK);
        } else {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: invalid allocation type!");
        }
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleModel scheduleModel:
                createSeatAllocationRequestModel.getSchedules()) {
            Schedule schedule = entityModelConversionService.convertScheduleModelToSeatAllocationSchedule(scheduleModel,
                    seatAllocationRequest.getAllocationType().toString());

            if (!schedule.getStartDateTime().after(new Date())) {
                throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: start date time cannot be a historical date time!");
            }
            GregorianCalendar oneYearLater = new GregorianCalendar();
            oneYearLater.roll(Calendar.YEAR,1);
            Date oneYearLaterDate = oneYearLater.getTime();
            if (schedule.getStartDateTime().after(oneYearLaterDate)) {
                throw new SeatAllocationException("Assigning seat failed: the start date time of the occupancy cannot be later than 1 year " +
                        "after the current date!");
            }
            if (schedule.getEndDateTime() != null && schedule.getEndDateTime().before(schedule.getStartDateTime())) {
                throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: end date time cannot be before start date time!");
            }

            schedule = scheduleRepository.save(schedule);
            schedules.add(schedule);
        }

        // Set the new request attributes
        seatAllocationRequest.setTeam(requester.getTeam());
        seatAllocationRequest.setEmployeeOfAllocation(employeeOfAllocation);
        seatAllocationRequest.setSeatAllocationSchedules(schedules);
        seatAllocationRequest.setRequester(requester);

        // Create the initial approval ticket: escalate to the business unit level
        BusinessUnit businessUnit = requester.getTeam().getBusinessUnit();
        SeatRequestAdminMatch nextLevelMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(businessUnit.getId());
        Employee approver = nextLevelMatch.getSeatAdmin();
        String requestorComment = createSeatAllocationRequestModel.getComment();

        // Copy from ApprovalTicketService
        ApprovalForRequest ticket = new ApprovalForRequest();
        ticket.setCreatedBy(requester.getUserName());
        ticket.setRequestedItemId(seatAllocationRequest.getId());
        ticket.setApprovalType(ApprovalTypeEnum.SEAT_ALLOCATION);
        ticket.setRequester(requester);
        ticket.setApprover(approver);
        ticket.setApprovalStatus(ApprovalStatusEnum.PENDING);
        ticket.setCommentByRequester(requestorComment);
        ticket = approvalForRequestRepository.save(ticket);
        try {
            String code = seatManagementEntityCodeHPGenerator.generateCode(approvalForRequestRepository, ticket);
            ticket.setCode(code);
            ticket.setObjectName(code);
        } catch (Exception ex) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: " + ex.getMessage());
        }

        // Update the seat allocation request
        seatAllocationRequest.setEscalatedHierarchyLevel(HierarchyTypeEnum.BUSINESS_UNIT);
        seatAllocationRequest.setEscalatedHierarchyId(businessUnit.getId());
        seatAllocationRequest = seatAllocationRequestRepository.save(seatAllocationRequest);
        try {
            String code = seatManagementEntityCodeHPGenerator.generateCode(seatAllocationRequestRepository, seatAllocationRequest);
            seatAllocationRequest.setCode(code);
            seatAllocationRequest.setObjectName(code);
        } catch (Exception ex) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: " + ex.getMessage());
        }

        ticket.setRequestedItemId(seatAllocationRequest.getId());
        seatAllocationRequest.setCurrentPendingTicket(ticket);
        seatAllocationRequest.getApprovalTickets().add(ticket);
        requester.getMyRequestTickets().add(ticket.getId());
        approver.getMyApprovals().add(ticket.getId());

        approvalForRequestRepository.save(ticket);
        seatAllocationRequestRepository.save(seatAllocationRequest);
        employeeRepository.save(requester);
        employeeRepository.save(approver);
    }


    // ---------------------------------- Retrieve ----------------------------------

    public SeatAllocationRequest retrieveSeatAllocationRequestById(String seatAllocationRequestId) {
        if (seatAllocationRequestId == null || seatAllocationRequestId.trim().length() == 0) {
            throw new SeatAllocationRequestNotFoundException("Seat allocation request ID is required!");
        }
        Optional<SeatAllocationRequest> optionalSeatAllocationRequest = seatAllocationRequestRepository.findUndeletedById(seatAllocationRequestId);
        if (!optionalSeatAllocationRequest.isPresent()) {
            throw new SeatAllocationRequestNotFoundException("Seat allocation request with ID " + seatAllocationRequestId + " does not exist!");
        }
        return optionalSeatAllocationRequest.get();
    }

    public SeatAllocationRequest retrieveSeatAllocationRequestByTicketId(String ticketId) {
        if (ticketId == null || ticketId.trim().length() == 0) {
            throw new SeatAllocationRequestNotFoundException("Ticket ID is required!");
        }
        Optional<SeatAllocationRequest> optionalSeatAllocationRequest = seatAllocationRequestRepository.findUndeletedByTicketId(ticketId);
        if (!optionalSeatAllocationRequest.isPresent()) {
            throw new SeatAllocationRequestNotFoundException("Seat allocation request with one of its tickets ID being " + ticketId + " does not exist!");
        }
        return optionalSeatAllocationRequest.get();
    }

    public ApprovalForRequest retrieveApprovalForRequestById(String ticketId) {
        if (ticketId == null || ticketId.trim().length() == 0) {
            throw new ApprovalForRequestNotFoundException("Approval ticket is missing or invalid!");
        }
        Optional<ApprovalForRequest> optionalApprovalForRequest = approvalForRequestRepository.findUndeletedTicketById(ticketId);
        if (!optionalApprovalForRequest.isPresent()) {
            throw new ApprovalForRequestNotFoundException("Approval ticket with ID " + ticketId + " does not exist!");
        }
        return optionalApprovalForRequest.get();
    }

    public List<SeatAllocationRequest> retrieveSeatAllocationRequestsWithApprovalTicketsByReviewerId(String reviewerId) {
        if (reviewerId == null || reviewerId.trim().length() == 0) {
            throw new InvalidInputException("Invalid input: reviewer ID is required for this retrieval!");
        }

        List<SeatAllocationRequest> seatAllocationRequests = new ArrayList<>();
        seatAllocationRequests = seatAllocationRequestRepository.findOnesByReviewerId(reviewerId);

        // Remove approval tickets that are created after the one assigned to the reviewer (escalated by the reviewer)
        for (SeatAllocationRequest request :
                seatAllocationRequests) {
            String ticketIdAssignedToReviewer = "";
            Date ticketCreatedDateTime = new Date();
            for (ApprovalForRequest ticket :
                    request.getApprovalTickets()) {
                if (ticket.getApprover().getId().equals(reviewerId)) {
                    ticketIdAssignedToReviewer = ticket.getId();
                    ticketCreatedDateTime = ticket.getCreatedDateTime();
                }
            }

            ListIterator<ApprovalForRequest> listIterator = request.getApprovalTickets().listIterator();
            while(listIterator.hasNext()) {
                ApprovalForRequest thisTicket = listIterator.next();
                if (!thisTicket.getApprover().getId().equals(reviewerId) && thisTicket.getCreatedDateTime().after(ticketCreatedDateTime)) {
                    listIterator.remove();
                }
            }
        }
        return seatAllocationRequests;
    }

    public List<SeatAllocationRequest> retrieveSeatAllocationRequestsWithApprovalTicketsByRequesterId(String requesterId) {
        if (requesterId == null || requesterId.trim().length() == 0) {
            throw new InvalidInputException("Invalid input: requesterId ID is required for this retrieval!");
        }
        List<SeatAllocationRequest> seatAllocationRequests = new ArrayList<>();
        seatAllocationRequests = seatAllocationRequestRepository.findOnesByRequesterId(requesterId);
        return seatAllocationRequests;
    }


    // ---------------------------------- Process: Approve or Reject ----------------------------------

    // TODO: Check whether the requester has the right to process this seat allocation request + send email to the requester for notification
    public void approveSeatAllocationRequest(ApproveSeatAllocationRequestModel approveSeatAllocationRequestModel) {
        if (approveSeatAllocationRequestModel.getSeatId() == null || approveSeatAllocationRequestModel.getSeatId().trim().length() == 0) {
            throw new SeatAllocationRequestProcessingException("Seat Id is required for approving seat allocation request!");
        }
        if (approveSeatAllocationRequestModel.getApprovalTicket().getId() == null || approveSeatAllocationRequestModel.getApprovalTicket().getId().trim().length() == 0) {
            throw new SeatAllocationRequestProcessingException("Approval ticket Id is required for approving seat allocation request!");
        }
        ApprovalForRequest approvalForRequest = retrieveApprovalForRequestById(approveSeatAllocationRequestModel.getApprovalTicket().getId());
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestById(approvalForRequest.getRequestedItemId());

        // Check whether the approval ticket type is valid
        if (!approvalForRequest.getApprovalType().equals(ApprovalTypeEnum.SEAT_ALLOCATION)) {
            throw new SeatAllocationRequestProcessingException("Mismatched approval ticket type!");
        }

        // TODO: Check whether the requester has the right to process this seat allocation request
//        Employee reviewer = approvalForRequest.getApprover();
        String escalatedHierarchyId = seatAllocationRequest.getEscalatedHierarchyId();
//        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(escalatedHierarchyId);
//        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(reviewer.getId())) {
//            throw new CreateSeatAllocationRequestRightException("Processing seat allocation request failed: employee with ID " + reviewer.getId() +
//                    " does not have the right to process the request!");
//        }

        // Check whether the ticket/request has already been processed
        if (!approvalForRequest.getApprovalStatus().equals(ApprovalStatusEnum.PENDING)) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the ticket has already been processed!");
        }
        if (seatAllocationRequest.isResolved()) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the request has already been resolved!");
        }

        // Check whether the seat can accommodate the required schedule:
        // 1. the seat is allocated under the right hierarchical level
        Seat seat = seatService.retrieveSeatById(approveSeatAllocationRequestModel.getSeatId());
        HierarchyTypeEnum hierarchyTypeEnum = seatAllocationRequest.getEscalatedHierarchyLevel();
        if (hierarchyTypeEnum.equals(HierarchyTypeEnum.BUSINESS_UNIT)) {
            Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findByIdNonDeleted(escalatedHierarchyId);
            BusinessUnit businessUnit = optionalBusinessUnit.get();
            if (!seat.getBusinessUnitAssigned().getId().equals(businessUnit.getId())) {
                throw new SeatAllocationRequestProcessingException("Invalid seat choice for this allocation request!");
            }
        } else if (hierarchyTypeEnum.equals(HierarchyTypeEnum.COMPANY_FUNCTION)) {
            CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(escalatedHierarchyId);
            if (!seat.getBusinessUnitAssigned().getFunction().getId().equals(companyFunction.getId())) {
                throw new SeatAllocationRequestProcessingException("Invalid seat choice for this allocation request!");
            }
        }

        // 2. the seat is in the office where the employee's team is located
        if (!seat.getSeatMap().getOffice().getId().equals(seatAllocationRequest.getEmployeeOfAllocation().getTeam().getOffice().getId())) {
            throw new SeatAllocationRequestProcessingException("Invalid seat choice for this allocation request: the seat must be at the office where" +
                    " the employee's team is located.");
        }

        // 3. there is no schedule clash with the required schedule
        for (Schedule schedule :
                seatAllocationRequest.getSeatAllocationSchedules()) {
            if (seatService.hasScheduleClash(seat, schedule, seatAllocationRequest.getAllocationType())) {
                throw new SeatAllocationRequestProcessingException("Approval of seat allocation request failed: there is a schedule clash!");
            }
        }

        for (Schedule schedule :
                seatAllocationRequest.getSeatAllocationSchedules()) {
            // 4. create a new allocation for the employee; allocation type will be the required type even if the seat is actually assigned to another unit entity
            // IMPORTANT: in the seat utilization reports, we only concern the fact that:
            // - how many seats are allocated to team A
            // - Among these seats, how many are occupied/empty -> we DON'T care who are using the seat
            SeatAllocation newSeatAllocation = new SeatAllocation();
            newSeatAllocation.setSchedule(schedule);
            newSeatAllocation.setEmployee(seatAllocationRequest.getEmployeeOfAllocation());
            newSeatAllocation.setAllocationType(seatAllocationRequest.getAllocationType());
            newSeatAllocation.setSeat(seat);
            newSeatAllocation = seatAllocationRepository.save(newSeatAllocation);
            if (schedule.getEndDateTime() != null) {
                SeatAllocationInactivationLog log = new SeatAllocationInactivationLog();
                log.setAllocation_id(newSeatAllocation.getId());
                log.setInactivate_time(schedule.getEndDateTime());
                seatAllocationInactivationLogRepository.save(log);
            }
            seat.getActiveSeatAllocations().add(newSeatAllocation);
            seatRepository.save(seat);
            seatAllocationRequest.getResultedSeatAllocations().add(newSeatAllocation);
        }

        approvalForRequest.setApprovalStatus(ApprovalStatusEnum.APPROVED);
        if (approveSeatAllocationRequestModel.getApprovalTicket().getApproverComment() != null) {
            approvalForRequest.setCommentByApprover(approveSeatAllocationRequestModel.getApprovalTicket().getApproverComment());
        }
        approvalForRequestRepository.save(approvalForRequest);
        seatAllocationRequest.setCurrentPendingTicket(null);
        seatAllocationRequest.setResolved(true);
        seatAllocationRequestRepository.save(seatAllocationRequest);
        // TODO: send email to the requester for notification
    }

    // TODO: Check whether the requester has the right to process this seat allocation request + send email to the requester for notification
    public void rejectSeatAllocationRequest(ApprovalTicketModel approvalTicketModel) {
        if (approvalTicketModel.getId() == null || approvalTicketModel.getId().trim().length() == 0) {
            throw new SeatAllocationRequestProcessingException("Ticket Id is required for rejecting a seat allocation request!");
        }
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestByTicketId(approvalTicketModel.getId());
        ApprovalForRequest approvalForRequest = null;
        for (ApprovalForRequest ticket :
                seatAllocationRequest.getApprovalTickets()) {
            if (ticket.getId().equals(approvalTicketModel.getId())) {
                approvalForRequest = ticket;
            }
        }

        // TODO: Check whether the requester has the right to process this seat allocation request using the current user account
//        Employee reviewer = approvalForRequest.getApprover();
//        String escalatedHierarchyId = seatAllocationRequest.getEscalatedHierarchyId();
//        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(escalatedHierarchyId);
//        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(reviewer.getId())) {
//            throw new CreateSeatAllocationRequestRightException("Processing seat allocation request failed: employee with ID " + reviewer.getId() +
//                    " does not have the right to process the request!");
//        }

        // Check whether the ticket/request has already been processed
        if (!approvalForRequest.getApprovalStatus().equals(ApprovalStatusEnum.PENDING)) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the ticket has already been processed!");
        }
        if (seatAllocationRequest.isResolved()) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the request has already been resolved!");
        }

        // Reject the ticket
        approvalForRequest.setApprovalStatus(ApprovalStatusEnum.REJECTED);
        if (approvalTicketModel.getApproverComment() != null) {
            approvalForRequest.setCommentByApprover(approvalTicketModel.getApproverComment());
        }
        seatAllocationRequest.setCurrentPendingTicket(null);
        seatAllocationRequest.setResolved(true);
        approvalForRequestRepository.save(approvalForRequest);
        seatAllocationRequestRepository.save(seatAllocationRequest);
        // TODO: send email to the requester for notification
    }

    // TODO: Check whether the requester has the right to process this seat allocation request + send email to the requester & new reviewer for notification
    public void escalateSeatAllocationRequest(ApprovalTicketModel approvalTicketModel) {
        if (approvalTicketModel.getId() == null || approvalTicketModel.getId().trim().length() == 0) {
            throw new SeatAllocationRequestProcessingException("Approval ticket Id is required for escalating seat allocation request!");
        }
        ApprovalForRequest approvalForRequest = retrieveApprovalForRequestById(approvalTicketModel.getId());
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestById(approvalForRequest.getRequestedItemId());

        // Check whether the approval ticket type is valid
        if (!approvalForRequest.getApprovalType().equals(ApprovalTypeEnum.SEAT_ALLOCATION)) {
            throw new SeatAllocationRequestProcessingException("Mismatched approval ticket type!");
        }

        // TODO: Check whether the requester has the right to process this seat allocation request
//        Employee reviewer = approvalForRequest.getApprover();
        String escalatedHierarchyId = seatAllocationRequest.getEscalatedHierarchyId();
//        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(escalatedHierarchyId);
//        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(reviewer.getId())) {
//            throw new CreateSeatAllocationRequestRightException("Processing seat allocation request failed: employee with ID " + reviewer.getId() +
//                    " does not have the right to process the request!");
//        }

        // Check whether the ticket/request has already been processed
        if (!approvalForRequest.getApprovalStatus().equals(ApprovalStatusEnum.PENDING)) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the ticket has already been processed!");
        }
        if (seatAllocationRequest.isResolved()) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: the request has already been resolved!");
        }

        // Approve this ticket and escalate it by creating a new approval ticket
        approvalForRequest.setApprovalStatus(ApprovalStatusEnum.APPROVED);
        if (approvalTicketModel.getApproverComment() != null) {
            approvalForRequest.setCommentByApprover(approvalTicketModel.getApproverComment());
        }


        ApprovalForRequest newApprovalForRequest = new ApprovalForRequest();
        // Find the new escalate hierarchy and the corresponding unit
        if (seatAllocationRequest.getEscalatedHierarchyLevel().equals(HierarchyTypeEnum.COMPANY_FUNCTION)) {
            throw new SeatAllocationRequestProcessingException("Processing seat allocation request failed: this request cannot be escalated further because" +
                    " this is already the highest level. Please negotiate with the office manager to manually arrange for another seat. E.g., a new seat can be" +
                    " added in the office.");
        }
        BusinessUnit businessUnit = businessUnitService.retrieveBusinessUnitById(escalatedHierarchyId);
        CompanyFunction companyFunction = businessUnit.getFunction();
        seatAllocationRequest.setEscalatedHierarchyId(companyFunction.getId());
        seatAllocationRequest.setEscalatedHierarchyLevel(HierarchyTypeEnum.COMPANY_FUNCTION);
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(companyFunction.getId());
        Employee newReviewer = seatRequestAdminMatch.getSeatAdmin();
        newApprovalForRequest.setApprover(newReviewer);
        newApprovalForRequest.setRequester(seatAllocationRequest.getRequester());
        newApprovalForRequest.setApprovalType(ApprovalTypeEnum.SEAT_ALLOCATION);
        newApprovalForRequest.setRequestedItemId(seatAllocationRequest.getId());
        newApprovalForRequest.setCommentByRequester(approvalForRequest.getCommentByRequester());
        newApprovalForRequest = approvalForRequestRepository.save(newApprovalForRequest);
        seatAllocationRequest.getApprovalTickets().add(newApprovalForRequest);
        seatAllocationRequest.setCurrentPendingTicket(newApprovalForRequest);
        newReviewer.getMyApprovals().add(newApprovalForRequest.getId());
        seatAllocationRequest.getRequester().getMyRequestTickets().add(newApprovalForRequest.getId());

        approvalForRequestRepository.save(approvalForRequest);
        seatAllocationRequestRepository.save(seatAllocationRequest);
        employeeRepository.save(newReviewer);
        employeeRepository.save(seatAllocationRequest.getRequester());
        // TODO: send email to the requester and the new approver for notification
    }


    // ---------------------------------- Delete a Pending Request (by the original requester) ----------------------------------
    // TODO: Check whether the requester has the right to process this seat allocation request + send email to all the previous reviewers for notification
    public void deletePendingSeatAllocationRequest(String requestId) {
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestById(requestId);
        // Check whether the request has already been resolved
        if (seatAllocationRequest.isResolved()) {
            throw new SeatAllocationRequestProcessingException("Deleting seat allocation request failed: the request has already been resolved!");
        }

        // TODO: Check whether the requester has the right to process this seat allocation request

        // Delete all the tickets
        for (ApprovalForRequest ticket :
                seatAllocationRequest.getApprovalTickets()) {
            ticket.setDeleted(true);
            approvalForRequestRepository.save(ticket);
            ticket.getApprover().getMyApprovals().remove(ticket.getId());
            employeeRepository.save(ticket.getApprover());
            seatAllocationRequest.getRequester().getMyRequestTickets().remove(ticket.getId());
        }
        seatAllocationRequest.setDeleted(true);
        seatAllocationRequestRepository.save(seatAllocationRequest);
        employeeRepository.save(seatAllocationRequest.getRequester());
        // TODO: send email to all the previous approvers for notification
    }


    // ---------------------------------- Forecasting Queries ----------------------------------
    // 1. By Hiring
    // 2. By Leavers
    // 3. Net Demand


}
