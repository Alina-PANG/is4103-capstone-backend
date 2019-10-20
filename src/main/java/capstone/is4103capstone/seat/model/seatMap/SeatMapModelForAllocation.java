package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModelForAllocation implements Serializable {
    private String id;
    private String code;
    private List<SeatModelForAllocation> seatModelsForAllocation = new ArrayList<>();

    public SeatMapModelForAllocation() {
    }

    public SeatMapModelForAllocation(String id, String code, List<SeatModelForAllocation> seatModelsForAllocation) {
        this.id = id;
        this.code = code;
        this.seatModelsForAllocation = seatModelsForAllocation;
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

    public List<SeatModelForAllocation> getSeatModelsForAllocation() {
        return seatModelsForAllocation;
    }

    public void setSeatModelsForAllocation(List<SeatModelForAllocation> seatModelsForAllocation) {
        this.seatModelsForAllocation = seatModelsForAllocation;
    }

}
