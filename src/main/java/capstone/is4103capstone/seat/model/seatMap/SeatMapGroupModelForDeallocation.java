package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.GroupModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapGroupModelForDeallocation implements Serializable {
    private List<GroupModel> seatMaps = new ArrayList<>();

    public SeatMapGroupModelForDeallocation() {

    }

    public SeatMapGroupModelForDeallocation(List<GroupModel> seatMaps) {
        this.seatMaps = seatMaps;
    }

    public List<GroupModel> getSeatMaps() {
        return seatMaps;
    }

    public void setSeatMaps(List<GroupModel> seatMaps) {
        this.seatMaps = seatMaps;
    }
}
