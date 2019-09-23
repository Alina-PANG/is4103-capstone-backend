package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatModelForAllocation {
    private String id;
    private String code;
    private String functionAssigned;
    private String teamAssigned;
    private boolean hasAllocation;

    public SeatModelForAllocation() {
    }

    public SeatModelForAllocation(String id, String code, String functionAssigned, String teamAssigned, boolean hasAllocation) {
        this.id = id;
        this.code = code;
        this.functionAssigned = functionAssigned;
        this.teamAssigned = teamAssigned;
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

    public String getFunctionAssigned() {
        return functionAssigned;
    }

    public void setFunctionAssigned(String functionAssigned) {
        this.functionAssigned = functionAssigned;
    }

    public String getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(String teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    public boolean isHasAllocation() {
        return hasAllocation;
    }

    public void setHasAllocation(boolean hasAllocation) {
        this.hasAllocation = hasAllocation;
    }
}
