package capstone.is4103capstone.seat.model.schedule;

import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.schedule.ScheduleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmployeeOfficeWorkingScheduleModel implements Serializable {
    private String id;
    private EmployeeModel employee;
    private GroupModel office;
    private List<ScheduleModel> schedules = new ArrayList<>();

    public EmployeeOfficeWorkingScheduleModel() {
    }

    public EmployeeOfficeWorkingScheduleModel(String id, EmployeeModel employee, GroupModel office, List<ScheduleModel> schedules) {
        this.id = id;
        this.employee = employee;
        this.office = office;
        this.schedules = schedules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public GroupModel getOffice() {
        return office;
    }

    public void setOffice(GroupModel office) {
        this.office = office;
    }

    public List<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }
}
