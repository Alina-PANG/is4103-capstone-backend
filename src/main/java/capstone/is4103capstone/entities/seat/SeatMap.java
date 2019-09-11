package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.OfficeFacility;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class SeatMap extends OfficeFacility {
    @Min(1)
    private Integer numOfSeats;

    @OneToMany(mappedBy = "seatMap")
    private List<Seat> seats = new ArrayList<>();

    public SeatMap() {
    }

    public SeatMap(String objectName, String code, String hierachyPath, Integer numOfSeats) {
        super(objectName, code, hierachyPath);
        this.numOfSeats = numOfSeats;
    }

    public SeatMap(String objectName, String code, String hierachyPath, Office office, @Min(1) Integer floor, Integer numOfSeats) {
        super(objectName, code, hierachyPath, office, floor);
        this.numOfSeats = numOfSeats;
    }

    public Integer getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(Integer numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
