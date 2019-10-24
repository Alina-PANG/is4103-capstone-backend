package capstone.is4103capstone.seat.model.seatAllocationRequest;

import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationRequestModel implements Serializable {

    private String id;
    private String requestNumber;

    private GroupModel team;
    private GroupModel office;

    private Date createdDateTime;
    private String escalatedHierarchyLevel;
    private String escalatedHierarchyId;

    private List<ScheduleModel> seatAllocationSchedules = new ArrayList<>();
    private EmployeeModel employeeOfAllocation;
    private String allocationType;

    private EmployeeModel requester;
    private List<ApprovalTicketModel> approvalTickets = new ArrayList<>();
    private ApprovalTicketModel currentPendingTicket;

    private boolean resolved;
    private List<SeatAllocationModelForEmployee> resultedSeatAllocations = new ArrayList<>();

    public SeatAllocationRequestModel() {
    }

    public SeatAllocationRequestModel(String id, String requestNumber, GroupModel team, GroupModel office, Date createdDateTime,
                                      String escalatedHierarchyLevel, String escalatedHierarchyId,
                                      List<ScheduleModel> seatAllocationSchedules, EmployeeModel employeeOfAllocation, String allocationType,
                                      EmployeeModel requester, List<ApprovalTicketModel> approvalTickets,
                                      ApprovalTicketModel currentPendingTicket, boolean resolved, List<SeatAllocationModelForEmployee> resultedSeatAllocations) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.team = team;
        this.office = office;
        this.createdDateTime = createdDateTime;
        this.escalatedHierarchyLevel = escalatedHierarchyLevel;
        this.escalatedHierarchyId = escalatedHierarchyId;
        this.seatAllocationSchedules = seatAllocationSchedules;
        this.employeeOfAllocation = employeeOfAllocation;
        this.allocationType = allocationType;
        this.requester = requester;
        this.approvalTickets = approvalTickets;
        this.currentPendingTicket = currentPendingTicket;
        this.resolved = resolved;
        this.resultedSeatAllocations = resultedSeatAllocations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public GroupModel getOffice() {
        return office;
    }

    public void setOffice(GroupModel office) {
        this.office = office;
    }

    public String getEscalatedHierarchyLevel() {
        return escalatedHierarchyLevel;
    }

    public void setEscalatedHierarchyLevel(String escalatedHierarchyLevel) {
        this.escalatedHierarchyLevel = escalatedHierarchyLevel;
    }

    public String getEscalatedHierarchyId() {
        return escalatedHierarchyId;
    }

    public void setEscalatedHierarchyId(String escalatedHierarchyId) {
        this.escalatedHierarchyId = escalatedHierarchyId;
    }

    public GroupModel getTeam() {
        return team;
    }

    public void setTeam(GroupModel team) {
        this.team = team;
    }

    public List<ScheduleModel> getSeatAllocationSchedules() {
        return seatAllocationSchedules;
    }

    public void setSeatAllocationSchedules(List<ScheduleModel> seatAllocationSchedules) {
        this.seatAllocationSchedules = seatAllocationSchedules;
    }

    public EmployeeModel getEmployeeOfAllocation() {
        return employeeOfAllocation;
    }

    public void setEmployeeOfAllocation(EmployeeModel employeeOfAllocation) {
        this.employeeOfAllocation = employeeOfAllocation;
    }

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType;
    }

    public EmployeeModel getRequester() {
        return requester;
    }

    public void setRequester(EmployeeModel requester) {
        this.requester = requester;
    }

    public List<ApprovalTicketModel> getApprovalTickets() {
        return approvalTickets;
    }

    public void setApprovalTickets(List<ApprovalTicketModel> approvalTickets) {
        this.approvalTickets = approvalTickets;
    }

    public ApprovalTicketModel getCurrentPendingTicket() {
        return currentPendingTicket;
    }

    public void setCurrentPendingTicket(ApprovalTicketModel currentPendingTicket) {
        this.currentPendingTicket = currentPendingTicket;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public List<SeatAllocationModelForEmployee> getResultedSeatAllocations() {
        return resultedSeatAllocations;
    }

    public void setResultedSeatAllocations(List<SeatAllocationModelForEmployee> resultedSeatAllocations) {
        this.resultedSeatAllocations = resultedSeatAllocations;
    }
}
