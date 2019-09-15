package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class SeatMap extends DBEntityTemplate {
    @Min(1)
    private Integer numOfSeats = 0;
    @Min(1)
    private Integer floor;

    @OneToMany(mappedBy = "seatMap")
    private List<Seat> seats = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatmap_id")
    @JsonIgnore
    @NotNull
    private Office office;

    public SeatMap() {
    }

    public SeatMap(String objectName, String code, String hierachyPath, Office office, @Min(1) Integer floor) {
        super(objectName, code, hierachyPath);
        this.office = office;
        this.floor = floor;
    }

    public SeatMap(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Office office, @Min(1) Integer floor) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.office = office;
        this.floor = floor;
        this.seats = seats;
    }

    public Office getOffice() { return office; }

    public void setOffice(Office office) { this.office = office; }

    public Integer getFloor() { return floor; }

    public void setFloor(Integer floor) { this.floor = floor; }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Integer getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(Integer numOfSeats) {
        this.numOfSeats = numOfSeats;
    }
}