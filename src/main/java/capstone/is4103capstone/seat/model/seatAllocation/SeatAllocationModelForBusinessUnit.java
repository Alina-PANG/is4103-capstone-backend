package capstone.is4103capstone.seat.model.seatAllocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForBusinessUnit {
    private String businessUnitId;
    private List<String> seatIds = new ArrayList<>();

    public SeatAllocationModelForBusinessUnit() {
    }

    public SeatAllocationModelForBusinessUnit(String businessUnitId, List<String> seatIds) {
        this.businessUnitId = businessUnitId;
        this.seatIds = seatIds;
    }

    public String getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
}
