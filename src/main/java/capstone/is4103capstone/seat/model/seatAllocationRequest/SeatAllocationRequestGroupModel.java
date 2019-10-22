package capstone.is4103capstone.seat.model.seatAllocationRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationRequestGroupModel {
    List<SeatAllocationRequestModel> seatAllocationRequestModels = new ArrayList<>();

    public SeatAllocationRequestGroupModel() {
    }

    public SeatAllocationRequestGroupModel(List<SeatAllocationRequestModel> seatAllocationRequestModels) {
        this.seatAllocationRequestModels = seatAllocationRequestModels;
    }

    public List<SeatAllocationRequestModel> getSeatAllocationRequestModels() {
        return seatAllocationRequestModels;
    }

    public void setSeatAllocationRequestModels(List<SeatAllocationRequestModel> seatAllocationRequestModels) {
        this.seatAllocationRequestModels = seatAllocationRequestModels;
    }
}
