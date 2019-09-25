package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class SeatAllocation extends DBEntityTemplate {

    private SeatTypeEnum seatType = SeatTypeEnum.FIXED;

    @OneToOne
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id",nullable = false)
    @JsonIgnore
    private Seat seat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    public SeatAllocation(){
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(SeatTypeEnum seatType, Schedule schedule, Seat seat, Employee employee) {
        this.seatType = seatType;
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(String objectName, String code, String hierachyPath, SeatTypeEnum seatType, Schedule schedule, Seat seat, Employee employee) {
        super(objectName, code, hierachyPath);
        this.seatType = seatType;
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatAllocation(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, SeatTypeEnum seatType, Schedule schedule, Seat seat, Employee employee) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.seatType = seatType;
        this.schedule = schedule;
        this.seat = seat;
        this.employee = employee;
        this.setCreatedDateTime(new Date());
    }

    public SeatTypeEnum getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatTypeEnum seatType) {
        this.seatType = seatType;
        this.setLastModifiedDateTime(new Date());
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        this.setLastModifiedDateTime(new Date());
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
}