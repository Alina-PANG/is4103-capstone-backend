package capstone.is4103capstone.seat.model.seatMap;

import capstone.is4103capstone.seat.model.seat.SeatModelForSeatMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModelForSetup implements Serializable {
    private String id;
    private String region;
    private String country;
    private String countryId;
    private String office;
    private String officeId;
    private String floor;
    private List<SeatModelForSeatMap> seats = new ArrayList<>();

    public SeatMapModelForSetup() {
    }

    public SeatMapModelForSetup(String id, String region, String country, String countryId, String office, String officeId, String floor, List<SeatModelForSeatMap> seats) {
        this.id = id;
        this.region = region;
        this.country = country;
        this.countryId = countryId;
        this.office = office;
        this.officeId = officeId;
        this.floor = floor;
        this.seats = seats;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryId() { return countryId; }

    public void setCountryId(String countryId) { this.countryId = countryId; }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getOfficeId() { return officeId; }

    public void setOfficeId(String officeId) { this.officeId = officeId; }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public List<SeatModelForSeatMap> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatModelForSeatMap> seats) {
        this.seats = seats;
    }
}
