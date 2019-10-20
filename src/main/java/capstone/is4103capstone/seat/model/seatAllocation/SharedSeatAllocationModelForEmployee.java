package capstone.is4103capstone.seat.model.seatAllocation;

import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedSeatAllocationModelForEmployee implements Serializable {
    private String id;
    private String type;
    private String seatId;

    private EmployeeModel employee;
    private List<ScheduleModel> schedules = new ArrayList<>();

    public SharedSeatAllocationModelForEmployee() {
    }

    public SharedSeatAllocationModelForEmployee(String id, String type, String seatId, EmployeeModel employee, List<ScheduleModel> schedules) {
        this.id = id;
        this.type = type;
        this.seatId = seatId;
        this.employee = employee;
        this.schedules = schedules;
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

    public List<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }
}
