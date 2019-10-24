package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class EmployeeOfficeWorkingSchedule extends DBEntityTemplate implements Comparable<EmployeeOfficeWorkingSchedule> {
    @OneToOne
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "office_id", nullable = false)
    @JsonIgnore
    private Office office;

    @OneToOne
    private Schedule schedule;
    private EmployeeTypeEnum employeeType;

    public EmployeeOfficeWorkingSchedule() {
    }

    public EmployeeOfficeWorkingSchedule(Employee employee, Office office, Schedule schedule, EmployeeTypeEnum employeeType) {
        this.employee = employee;
        this.office = office;
        this.schedule = schedule;
        this.employeeType = employeeType;
    }

    public EmployeeOfficeWorkingSchedule(String objectName, String code, String hierachyPath, Employee employee, Office office,
                                         Schedule schedule, EmployeeTypeEnum employeeType) {
        super(objectName, code, hierachyPath);
        this.employee = employee;
        this.office = office;
        this.schedule = schedule;
        this.employeeType = employeeType;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public EmployeeTypeEnum getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeEnum employeeType) {
        this.employeeType = employeeType;
    }

    @Override
    public int compareTo(EmployeeOfficeWorkingSchedule anotherSchedule) {
        return this.getSchedule().compareTo(anotherSchedule.schedule);
    }
}
