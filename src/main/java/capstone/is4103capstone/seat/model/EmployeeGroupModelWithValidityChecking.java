package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeGroupModelWithValidityChecking implements Serializable {
    private List<EmployeeModelWithValidityChecking> employees = new ArrayList<>();

    public EmployeeGroupModelWithValidityChecking() {
    }

    public EmployeeGroupModelWithValidityChecking(List<EmployeeModelWithValidityChecking> employees) {
        this.employees = employees;
    }

    public List<EmployeeModelWithValidityChecking> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeModelWithValidityChecking> employees) {
        this.employees = employees;
    }
}
