package capstone.is4103capstone.seat.model.seatAllocation;

import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForEmployee implements Serializable {
    private String id;
    private String type;
    private String seatId;

    private EmployeeModel employee;
    private ScheduleModel schedule;

    public SeatAllocationModelForEmployee() {
    }

    public SeatAllocationModelForEmployee(String id, String type, String seatId, EmployeeModel employee, ScheduleModel schedule) {
        this.id = id;
        this.type = type;
        this.seatId = seatId;
        this.employee = employee;
        this.schedule = schedule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }
}
