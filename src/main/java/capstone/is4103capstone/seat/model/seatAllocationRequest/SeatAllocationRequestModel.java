package capstone.is4103capstone.seat.model.seatAllocationRequest;

import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationRequestModel implements Serializable {

    private String id;
    private GroupModel team;
    private String escalatedHierarchyLevel;
    private String escalatedHierarchyId;
    private ScheduleModel seatAllocationSchedule;
    private EmployeeModel employeeOfAllocation;
    private String allocationType;
    private EmployeeModel requester;
    private List<ApprovalTicketModel> approvalTickets = new ArrayList<>();
    private ApprovalTicketModel currentPendingTicket;
    private boolean resolved;
    private String resultedSeatAllocationId;

    public SeatAllocationRequestModel() {
    }

    public SeatAllocationRequestModel(String id, GroupModel team, String escalatedHierarchyLevel, String escalatedHierarchyId,
                                      ScheduleModel seatAllocationSchedule, EmployeeModel employeeOfAllocation, String allocationType,
                                      EmployeeModel requester, List<ApprovalTicketModel> approvalTickets,
                                      ApprovalTicketModel currentPendingTicket, boolean resolved, String resultedSeatAllocationId) {
        this.id = id;
        this.team = team;
        this.escalatedHierarchyLevel = escalatedHierarchyLevel;
        this.escalatedHierarchyId = escalatedHierarchyId;
        this.seatAllocationSchedule = seatAllocationSchedule;
        this.employeeOfAllocation = employeeOfAllocation;
        this.allocationType = allocationType;
        this.requester = requester;
        this.approvalTickets = approvalTickets;
        this.currentPendingTicket = currentPendingTicket;
        this.resolved = resolved;
        this.resultedSeatAllocationId = resultedSeatAllocationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ScheduleModel getSeatAllocationSchedule() {
        return seatAllocationSchedule;
    }

    public void setSeatAllocationSchedule(ScheduleModel seatAllocationSchedule) {
        this.seatAllocationSchedule = seatAllocationSchedule;
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

    public String getResultedSeatAllocationId() {
        return resultedSeatAllocationId;
    }

    public void setResultedSeatAllocationId(String resultedSeatAllocationId) {
        this.resultedSeatAllocationId = resultedSeatAllocationId;
    }
}
