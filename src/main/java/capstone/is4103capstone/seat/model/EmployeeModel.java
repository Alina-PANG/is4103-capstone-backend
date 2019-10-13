package capstone.is4103capstone.seat.model;

import capstone.is4103capstone.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeModel implements Serializable {
    private String id;
    private String fullName;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EmployeeModel() {
    }

    public EmployeeModel(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public EmployeeModel(Employee e) {
        setId(e.getId());
        setUsername(e.getUserName());
        setFullName(e.getFullName());
    }

    public EmployeeModel(String id, String fullName, String username) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
