package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModelForAllocation implements Serializable {
    private String id;
    private String code;
    private List<SeatModelWithHighlighting> seatModelsForDeallocationViaSeatMap = new ArrayList<>();

    public SeatMapModelForAllocation() {
    }

    public SeatMapModelForAllocation(String id, String code, List<SeatModelWithHighlighting> seatModelsForDeallocationViaSeatMap) {
        this.id = id;
        this.code = code;
        this.seatModelsForDeallocationViaSeatMap = seatModelsForDeallocationViaSeatMap;
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

    public List<SeatModelWithHighlighting> getSeatModelsForDeallocationViaSeatMap() {
        return seatModelsForDeallocationViaSeatMap;
    }

    public void setSeatModelsForDeallocationViaSeatMap(List<SeatModelWithHighlighting> seatModelsForDeallocationViaSeatMap) {
        this.seatModelsForDeallocationViaSeatMap = seatModelsForDeallocationViaSeatMap;
    }
}
