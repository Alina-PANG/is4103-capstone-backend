package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class SeatAllocationRequest extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @NotNull
    private Team team;
    @NotNull
    private HierarchyTypeEnum escalatedHierarchyLevel;
    @NotNull
    private String escalatedHierarchyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @NotNull
    private Schedule seatAllocationSchedule;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_of_allocation_id", referencedColumnName = "id")
    @NotNull
    private Employee employeeOfAllocation;
    @NotNull
    private SeatAllocationTypeEnum allocationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @NotNull
    private Employee requester;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "seatAllocationRequest_approvalTickets",
            joinColumns = @JoinColumn(
                    name = "request_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ticket_id",
                    referencedColumnName = "id"
            )
    )
    private List<ApprovalForRequest> approvalTickets = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_pending_ticket_id", referencedColumnName = "id")
    private ApprovalForRequest currentPendingTicket;

    private boolean resolved = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resulted_seat_allocation_id", referencedColumnName = "id")
    private SeatAllocation resultedSeatAllocation;

    public SeatAllocationRequest() {
    }

    public SeatAllocationRequest(@NotNull Team team, String escalatedHierarchyId, @NotNull HierarchyTypeEnum escalatedHierarchyLevel,
                                 @NotNull Schedule seatAllocationSchedule, @NotNull Employee employeeOfAllocation, SeatAllocationTypeEnum allocationType,
                                 @NotNull Employee requester, List<ApprovalForRequest> approvalTickets,
                                 ApprovalForRequest currentPendingTicket, boolean resolved) {
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
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public HierarchyTypeEnum getEscalatedHierarchyLevel() {
        return escalatedHierarchyLevel;
    }

    public void setEscalatedHierarchyLevel(HierarchyTypeEnum escalatedHierarchyLevel) {
        this.escalatedHierarchyLevel = escalatedHierarchyLevel;
    }

    public String getEscalatedHierarchyId() {
        return escalatedHierarchyId;
    }

    public void setEscalatedHierarchyId(String escalatedHierarchyId) {
        this.escalatedHierarchyId = escalatedHierarchyId;
    }

    public Schedule getSeatAllocationSchedule() {
        return seatAllocationSchedule;
    }

    public void setSeatAllocationSchedule(Schedule seatAllocationSchedule) {
        this.seatAllocationSchedule = seatAllocationSchedule;
    }

    public Employee getEmployeeOfAllocation() {
        return employeeOfAllocation;
    }

    public SeatAllocationTypeEnum getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(SeatAllocationTypeEnum allocationType) {
        this.allocationType = allocationType;
    }

    public void setEmployeeOfAllocation(Employee employeeOfAllocation) {
        this.employeeOfAllocation = employeeOfAllocation;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    public List<ApprovalForRequest> getApprovalTickets() {
        return approvalTickets;
    }

    public void setApprovalTickets(List<ApprovalForRequest> approvalTickets) {
        this.approvalTickets = approvalTickets;
    }

    public ApprovalForRequest getCurrentPendingTicket() {
        return currentPendingTicket;
    }

    public void setCurrentPendingTicket(ApprovalForRequest currentPendingTicket) {
        this.currentPendingTicket = currentPendingTicket;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public SeatAllocation getResultedSeatAllocation() {
        return resultedSeatAllocation;
    }

    public void setResultedSeatAllocation(SeatAllocation resultedSeatAllocation) {
        this.resultedSeatAllocation = resultedSeatAllocation;
    }
}
