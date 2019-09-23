package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatAllocationModelForFunction {
    private GroupModel function; // function id
    private List<SeatModelForAllocation> seats = new ArrayList<>();

    public SeatAllocationModelForFunction() {
    }

    public SeatAllocationModelForFunction(GroupModel function, List<SeatModelForAllocation> seats) {
        this.function = function;
        this.seats = seats;
    }

    public GroupModel getFunction() {
        return function;
    }

    public void setFunction(GroupModel function) {
        this.function = function;
    }

    public List<SeatModelForAllocation> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatModelForAllocation> seats) {
        this.seats = seats;
    }
}
