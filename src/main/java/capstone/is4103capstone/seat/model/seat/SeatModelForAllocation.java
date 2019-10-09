package capstone.is4103capstone.seat.model.seat;

import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatModelForAllocation implements Serializable {
    private String id;
    private String code;
    private String type;

    private GroupModel functionAssigned;
    private GroupModel businessUnitAssigned;
    private GroupModel teamAssigned;

    private SeatAllocationModelForEmployee currentOccupancy;
    private List<SeatAllocationModelForEmployee> allocations = new ArrayList<>();

    public SeatModelForAllocation() {
    }

    public SeatModelForAllocation(String id, String code, String type, GroupModel functionAssigned, GroupModel businessUnitAssigned, GroupModel teamAssigned,
                                  SeatAllocationModelForEmployee currentOccupancy, List<SeatAllocationModelForEmployee> allocations) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.functionAssigned = functionAssigned;
        this.businessUnitAssigned = businessUnitAssigned;
        this.teamAssigned = teamAssigned;
        this.currentOccupancy = currentOccupancy;
        this.allocations = allocations;
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

    public GroupModel getFunctionAssigned() {
        return functionAssigned;
    }

    public void setFunctionAssigned(GroupModel functionAssigned) {
        this.functionAssigned = functionAssigned;
    }

    public GroupModel getBusinessUnitAssigned() { return businessUnitAssigned; }

    public void setBusinessUnitAssigned(GroupModel businessUnitAssigned) { this.businessUnitAssigned = businessUnitAssigned; }

    public GroupModel getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(GroupModel teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    public SeatAllocationModelForEmployee getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(SeatAllocationModelForEmployee currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public List<SeatAllocationModelForEmployee> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<SeatAllocationModelForEmployee> allocations) {
        this.allocations = allocations;
    }
}
