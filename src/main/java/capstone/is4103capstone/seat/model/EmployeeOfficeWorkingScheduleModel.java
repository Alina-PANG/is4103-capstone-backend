package capstone.is4103capstone.seat.model;

import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.ScheduleModel;

import java.io.Serializable;

public class EmployeeOfficeWorkingScheduleModel implements Serializable {
    private String id;
    private EmployeeModel employee;
    private GroupModel office;
    private ScheduleModel schedule;
    private String employeeType; // PERMANENT, TEMPORARY, WORKING_FROM_HOME

    public EmployeeOfficeWorkingScheduleModel() {
    }

    public EmployeeOfficeWorkingScheduleModel(String id, EmployeeModel employee, GroupModel office, ScheduleModel schedule, String employeeType) {
        this.id = id;
        this.employee = employee;
        this.office = office;
        this.schedule = schedule;
        this.employeeType = employeeType;
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

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
}
