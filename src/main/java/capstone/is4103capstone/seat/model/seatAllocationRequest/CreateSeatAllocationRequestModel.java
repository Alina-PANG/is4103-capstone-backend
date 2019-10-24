package capstone.is4103capstone.seat.model.seatAllocationRequest;

import capstone.is4103capstone.seat.model.ScheduleModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSeatAllocationRequestModel implements Serializable {

    private List<ScheduleModel> schedules;
    private String employeeIdOfAllocation;
    private String allocationType;
    private String requesterId;
    private String comment;

    public CreateSeatAllocationRequestModel() {
    }

    public CreateSeatAllocationRequestModel(List<ScheduleModel> schedules, String employeeIdOfAllocation, String allocationType,
                                            String requesterId, String comment) {
        this.schedules = schedules;
        this.employeeIdOfAllocation = employeeIdOfAllocation;
        this.allocationType = allocationType;
        this.requesterId = requesterId;
        this.comment = comment;
    }

    public List<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }

    public String getEmployeeIdOfAllocation() {
        return employeeIdOfAllocation;
    }

    public void setEmployeeIdOfAllocation(String employeeIdOfAllocation) {
        this.employeeIdOfAllocation = employeeIdOfAllocation;
    }

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
