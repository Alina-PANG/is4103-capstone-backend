package capstone.is4103capstone.seat.model.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

//SeatMap:
//        {
//        “region”: String,
//        “country”: String,
//        “office”: String,
//        “floor”: String,
//        “seats”: [
//        {
//        “id”: String,
//        “x”: int,
//        “y”: int
//        }
//        ]
//        }
//
//        Example:
//        {
//        “region”: “APAC”,
//        “country”: “Singapore”,
//        “office”: “One Raffles Quay”,
//        “floor”: “23”,
//        “seats”: [
//        {
//        “id”: “ORQ-23-01”,
//        “x”: 0,
//        “y”: 0
//        },
//        {
//        “id”: “ORQ-23-02”,
//        “x”: 100,
//        “y”: 0
//        },
//        {
//        “id”: “ORQ-23-03”,
//        “x”: 300,
//        “y”: 0
//        },
//        {
//        “id”: “ORQ-23-04”,
//        “x”: 300,
//        “y”: 100
//        }
//        ]
//        }

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSeatMapReq {
    private String region;
    private String country;
    private String office;
    private String floor;
    private List<CreateSeatReq> seats = new ArrayList<>();

    public CreateSeatMapReq(){

    }

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

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public List<CreateSeatReq> getSeats() {
        return seats;
    }

    public void setSeats(List<CreateSeatReq> seats) {
        this.seats = seats;
    }
}
