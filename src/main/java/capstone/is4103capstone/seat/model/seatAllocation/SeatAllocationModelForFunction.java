package capstone.is4103capstone.seat.model.seatAllocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForFunction implements Serializable {
    private String functionId;
    private List<String> seatIds = new ArrayList<>();

    public SeatAllocationModelForFunction() {
    }

    public SeatAllocationModelForFunction(String functionId, List<String> seatIds) {
        this.functionId = functionId;
        this.seatIds = seatIds;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
}
