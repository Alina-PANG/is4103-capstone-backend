package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeModelWithValidityChecking implements Serializable {
    private String id;
    private String name;
    private boolean hasValidEmployeeId;

    public EmployeeModelWithValidityChecking() {
    }

    public EmployeeModelWithValidityChecking(String id, String name, boolean hasValidEmployeeId) {
        this.id = id;
        this.name = name;
        this.hasValidEmployeeId = hasValidEmployeeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasValidEmployeeId() {
        return hasValidEmployeeId;
    }

    public void setHasValidEmployeeId(boolean hasValidEmployeeId) {
        this.hasValidEmployeeId = hasValidEmployeeId;
    }
}
