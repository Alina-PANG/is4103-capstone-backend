package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class SeatMap extends DBEntityTemplate {
    @Min(1)
    private Integer numOfSeats = 0;
    @NotNull
    private String floor;

    @OneToMany(mappedBy = "seatMap", fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    @JsonIgnore
    @NotNull
    private Office office;

    public SeatMap() {
        this.setCreatedDateTime(new Date());
    }

    public SeatMap(String objectName, String code, String hierachyPath, Office office, String floor) {
        super(objectName, code, hierachyPath);
        this.office = office;
        this.floor = floor;
        this.setCreatedDateTime(new Date());
    }

    public SeatMap(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Office office, String floor) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.office = office;
        this.floor = floor;
        this.seats = seats;
        this.setCreatedDateTime(new Date());
    }

    public Office getOffice() { return office; }

    public void setOffice(Office office) { this.office = office; this.setLastModifiedDateTime(new Date()); }

    public String getFloor() { return floor; }

    public void setFloor(String floor) { this.floor = floor; this.setLastModifiedDateTime(new Date());}

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
        this.setLastModifiedDateTime(new Date());
    }

    public Integer getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(Integer numOfSeats) {
        this.numOfSeats = numOfSeats;
        this.setLastModifiedDateTime(new Date());
    }
}
