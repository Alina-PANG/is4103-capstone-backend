package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class SeatAllocation extends DBEntityTemplate implements Comparable<SeatAllocation> {

    @NotNull
    @Enumerated(EnumType.STRING)
    private SeatAllocationTypeEnum allocationType;
    @OneToOne
    private Schedule schedule;
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonIgnore
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    public SeatAllocation(){
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(Schedule schedule, Seat seat, Employee employee) {
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(String objectName, String code, String hierachyPath, Schedule schedule, Seat seat, Employee employee) {
        super(objectName, code, hierachyPath);
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Schedule schedule, Seat seat, Employee employee) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocationTypeEnum getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(SeatAllocationTypeEnum allocationType) {
        this.allocationType = allocationType;
        this.setLastModifiedDateTime(new Date());
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        this.setLastModifiedDateTime(new Date());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
        this.setLastModifiedDateTime(new Date());
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setLastModifiedDateTime(new Date());
    }

    @Override
    public int compareTo(SeatAllocation anotherSeatAllocation) {
        return this.schedule.compareTo(anotherSeatAllocation.schedule);
    }
}