package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class EmployeeOfficeWorkingSchedule extends DBEntityTemplate {

    // At any point of time an employee should only have one working schedule object at a particular office
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "office_id", nullable = false)
    @JsonIgnore
    private Office office;

    // The end date of the schedule means the last day of work for the employee -> should still occupies a seat
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_working_schedule_object",
            joinColumns = @JoinColumn(name = "working_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_object_id")
    )
    private List<Schedule> schedules = new ArrayList<>();


    public EmployeeOfficeWorkingSchedule() {
    }

    public EmployeeOfficeWorkingSchedule(Employee employee, Office office, List<Schedule> schedules) {
        this.employee = employee;
        this.office = office;
        this.schedules = schedules;
    }

    public EmployeeOfficeWorkingSchedule(String objectName, String code, String hierachyPath, Employee employee, Office office,
                                         List<Schedule> schedules) {
        super(objectName, code, hierachyPath);
        this.employee = employee;
        this.office = office;
        this.schedules = schedules;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

}
