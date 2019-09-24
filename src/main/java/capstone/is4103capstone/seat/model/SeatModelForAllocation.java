package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatModelForAllocation {
    private String id;
    private String code;
    private String type;

    private EmployeeModel employee;
    private GroupModel functionAssigned;
    private GroupModel teamAssigned;

    private ScheduleModel schedule;
    private boolean hasAllocation;

    public SeatModelForAllocation() {
    }

    public SeatModelForAllocation(String id, String code, String type, EmployeeModel employee, GroupModel functionAssigned, GroupModel teamAssigned, ScheduleModel schedule, boolean hasAllocation) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.employee = employee;
        this.functionAssigned = functionAssigned;
        this.teamAssigned = teamAssigned;
        this.schedule = schedule;
        this.hasAllocation = hasAllocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public GroupModel getFunctionAssigned() {
        return functionAssigned;
    }

    public void setFunctionAssigned(GroupModel functionAssigned) {
        this.functionAssigned = functionAssigned;
    }

    public GroupModel getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(GroupModel teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }

    public boolean isHasAllocation() {
        return hasAllocation;
    }

    public void setHasAllocation(boolean hasAllocation) {
        this.hasAllocation = hasAllocation;
    }
}
