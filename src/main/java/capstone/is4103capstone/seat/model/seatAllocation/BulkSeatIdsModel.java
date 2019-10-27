package capstone.is4103capstone.seat.model.seatAllocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkSeatIdsModel implements Serializable {
    private List<String> seatIds;

    public BulkSeatIdsModel() {
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
}
