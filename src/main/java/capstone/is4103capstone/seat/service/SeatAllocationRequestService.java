package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import capstone.is4103capstone.seat.model.seatAllocationRequest.CreateSeatAllocationRequestModel;
import capstone.is4103capstone.seat.repository.ScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRequestRepository;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SeatAllocationRequestService {

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
    private CompanyFunctionService companyFunctionService;

    @Autowired
    private SeatAllocationRequestRepository seatAllocationRequestRepository;
    @Autowired
    private ApprovalForRequestRepository approvalForRequestRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;

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
        Schedule schedule = entityModelConversionService.convertScheduleModelToSchedule(createSeatAllocationRequestModel.getSchedule(),
                seatAllocationRequest.getAllocationType().toString());
        if (schedule.getStartDateTime().after(new Date())) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: start date time cannot be a historical date time!");
        }
        if (schedule.getEndDateTime() != null && schedule.getEndDateTime().before(schedule.getStartDateTime())) {
            throw new CreateSeatAllocationRequestException("Creating seat allocation request failed: end date time cannot be before start date time!");
        }
        schedule = scheduleRepository.save(schedule);

        // Set the new request attributes
        seatAllocationRequest.setTeam(requester.getTeam());
        seatAllocationRequest.setEmployeeOfAllocation(employeeOfAllocation);
        seatAllocationRequest.setSeatAllocationSchedule(schedule);
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
        String code = EntityCodeHPGeneration.getCode(approvalForRequestRepository, ticket);
        ticket.setCode(code);
        ticket.setObjectName(code);
        approvalForRequestRepository.save(ticket);
        requester.getMyRequestTickets().add(ticket.getId());
        approver.getMyApprovals().add(ticket.getId());

        // Update the seat allocation request
        seatAllocationRequest.setEscalatedHierarchyLevel(HierarchyTypeEnum.BUSINESS_UNIT);
        seatAllocationRequest.setEscalatedHierarchyId(businessUnit.getId());
        seatAllocationRequest.setCurrentPendingTicket(ticket);
        seatAllocationRequest.getApprovalTickets().add(ticket);
        seatAllocationRequest = seatAllocationRequestRepository.save(seatAllocationRequest);

        ticket.setRequestedItemId(seatAllocationRequest.getId());
        approvalForRequestRepository.save(ticket);
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


    // ---------------------------------- Process: Approve or Reject ----------------------------------

    public void approveSeatAllocationRequest(ApprovalForRequest approvalForRequest, String seatId) {
        if (seatId == null || seatId.trim().length() == 0) {
            throw new SeatAllocationRequestProcessingException("Seat Id is required for approving seat allocation request!");
        }
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestById(approvalForRequest.getRequestedItemId());

        // Check whether the requester has the right to process this seat allocation request
        Employee reviewer = approvalForRequest.getApprover();
        String escalatedHierarchyId = seatAllocationRequest.getEscalatedHierarchyId();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(escalatedHierarchyId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(reviewer.getId())) {
            throw new CreateSeatAllocationRequestRightException("Processing seat allocation request failed: employee with ID " + reviewer.getId() +
                    " does not have the right to process the request!");
        }

        // Check whether the seat can accommodate the required schedule
        // 1. the seat is allocated under the right hierarchical level
        Seat seat = seatService.retrieveSeatById(seatId);
        HierarchyTypeEnum hierarchyTypeEnum = seatAllocationRequest.getEscalatedHierarchyLevel();
        if (hierarchyTypeEnum.equals(HierarchyTypeEnum.BUSINESS_UNIT)) {
            Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findByIdNonDeleted(escalatedHierarchyId);
            BusinessUnit businessUnit = optionalBusinessUnit.get();
            if (!seat.getBusinessUnitAssigned().getId().equals(businessUnit.getId())) {
                throw new SeatAllocationRequestProcessingException("Invalid seat choice for this allocation request!");
            }
        } else if (hierarchyTypeEnum.equals(HierarchyTypeEnum.COMPANY_FUNCTION)) {
            CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(escalatedHierarchyId);
            if (!seat.getBusinessUnitAssigned().getFunction().equals(companyFunction.getId())) {
                throw new SeatAllocationRequestProcessingException("Invalid seat choice for this allocation request!");
            }
        }
        // 2. there is no schedule clash with the required schedule

        // 3. create a new allocation for the employee; allocation type will be hot desk

    }

    // TODO: send email to the requester for notification
    public void rejectSeatAllocationRequest(ApprovalForRequest approvalForRequest) {
        SeatAllocationRequest seatAllocationRequest = retrieveSeatAllocationRequestById(approvalForRequest.getRequestedItemId());

        // Check whether the requester has the right to process this seat allocation request
        Employee reviewer = approvalForRequest.getApprover();
        String escalatedHierarchyId = seatAllocationRequest.getEscalatedHierarchyId();
        SeatRequestAdminMatch seatRequestAdminMatch = seatRequestAdminMatchService.retrieveMatchByHierarchyId(escalatedHierarchyId);
        if (!seatRequestAdminMatch.getSeatAdmin().getId().equals(reviewer.getId())) {
            throw new CreateSeatAllocationRequestRightException("Processing seat allocation request failed: employee with ID " + reviewer.getId() +
                    " does not have the right to process the request!");
        }

        seatAllocationRequest.setCurrentPendingTicket(null);
        seatAllocationRequest.setResolved(true);
        seatAllocationRequestRepository.save(seatAllocationRequest);
    }
}
