package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.seat.SeatModelForDeallocationViaSeatMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModelForDeallocation implements Serializable {
    private String id;
    private String code;
    private List<SeatModelForDeallocationViaSeatMap> seatModelsForDeallocationViaSeatMap = new ArrayList<>();

    public SeatMapModelForDeallocation() {
    }

    public SeatMapModelForDeallocation(String id, String code, List<SeatModelForDeallocationViaSeatMap> seatModelsForDeallocationViaSeatMap) {
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

    public List<SeatModelForDeallocationViaSeatMap> getSeatModelsForDeallocationViaSeatMap() {
        return seatModelsForDeallocationViaSeatMap;
    }

    public void setSeatModelsForDeallocationViaSeatMap(List<SeatModelForDeallocationViaSeatMap> seatModelsForDeallocationViaSeatMap) {
        this.seatModelsForDeallocationViaSeatMap = seatModelsForDeallocationViaSeatMap;
    }
}
