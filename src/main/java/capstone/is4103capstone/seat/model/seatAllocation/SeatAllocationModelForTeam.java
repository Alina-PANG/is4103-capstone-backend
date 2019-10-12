package capstone.is4103capstone.seat.model.seatAllocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForTeam implements Serializable {
    private String teamId;
    private List<String> seatIds = new ArrayList<>();

    public SeatAllocationModelForTeam() {
    }

    public SeatAllocationModelForTeam(String teamId, List<String> seatIds) {
        this.teamId = teamId;
        this.seatIds = seatIds;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
}
