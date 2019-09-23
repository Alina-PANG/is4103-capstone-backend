package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForTeam {
    private GroupModel team; // team id
    private List<SeatModelForAllocation> seats = new ArrayList<>();

    public SeatAllocationModelForTeam() {
    }

    public SeatAllocationModelForTeam(GroupModel team, List<SeatModelForAllocation> seats) {
        this.team = team;
        this.seats = seats;
    }

    public GroupModel getTeam() {
        return team;
    }

    public void setTeam(GroupModel team) {
        this.team = team;
    }

    public List<SeatModelForAllocation> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatModelForAllocation> seats) {
        this.seats = seats;
    }
}
