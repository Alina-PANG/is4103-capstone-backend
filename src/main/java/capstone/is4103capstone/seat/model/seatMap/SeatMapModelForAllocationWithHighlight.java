package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModelForAllocationWithHighlight implements Serializable {
    private String id;
    private String code;
    private List<SeatModelWithHighlighting> seatModelsForAllocationViaSeatMap = new ArrayList<>();

    public SeatMapModelForAllocationWithHighlight() {
    }

    public SeatMapModelForAllocationWithHighlight(String id, String code, List<SeatModelWithHighlighting> seatModelsForAllocationViaSeatMap) {
        this.id = id;
        this.code = code;
        this.seatModelsForAllocationViaSeatMap = seatModelsForAllocationViaSeatMap;
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

    public List<SeatModelWithHighlighting> getSeatModelsForAllocationViaSeatMap() {
        return seatModelsForAllocationViaSeatMap;
    }

    public void setSeatModelsForAllocationViaSeatMap(List<SeatModelWithHighlighting> seatModelsForAllocationViaSeatMap) {
        this.seatModelsForAllocationViaSeatMap = seatModelsForAllocationViaSeatMap;
    }
}
