package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

//        {
//        “id”: String,
//        “region”: String,
//        “country”: String,
//        “office”: String,
//        “floor”: String,
//        “seats”: [
//        {
//        “serialNumber”: int,
//        “id”: String,
//        “hasAllocation”: boolean,
//        “x”: int,
//        “y”: int
//        }
//        ]
//        }
//
//        Example:
//        {
//        “id”: “9409d218-012c-4394-ac17-9f790f8c2bfe”,
//        “region”: “APAC”,
//        “country”: “Singapore”,
//        “office”: “One Raffles Quay”,
//        “floor”: “23”,
//        “seats”: [
//        {
//        “serialNumber”: 1,
//        “id”: “c8ef5295-f274-47df-bb0f-a98fccad77e5”,
//        “hasAllocation”: false,
//        “x”: 0,
//        “y”: 0
//        },
//        {
//        “serialNumber”: 2,
//        “id”: “500afc65-8324-451e-9f4d-a4d238c5b7f1”,
//        “hasAllocation”: true,
//        “x”: 100,
//        “y”: 0
//        },
//        {
//        “serialNumber”: 3,
//        “id”: “bc75ea1b-cc88-4ddc-bc96-4d2e14c6ca4a”,
//        “hasAllocation”: true,
//        “x”: 200,
//        “y”: 100
//        },
//        {
//        “serialNumber”: 4,
//        “id”: “84015e83-5389-4ecd-85c3-196acc34f464”,
//        “hasAllocation”: false,
//        “x”: 300,
//        “y”: 100
//        }
//        ]
//        }

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapModel {
    private String id;
    private String region;
    private String country;
    private String office;
    private String floor;
    private List<SeatModelForSeatMap> seats = new ArrayList<>();

    public SeatMapModel() {
    }

    public SeatMapModel(String id, String region, String country, String office, String floor, List<SeatModelForSeatMap> seats) {
        this.region = region;
        this.country = country;
        this.office = office;
        this.floor = floor;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<SeatModelForSeatMap> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatModelForSeatMap> seats) {
        this.seats = seats;
    }
}
