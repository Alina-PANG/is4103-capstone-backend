package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//        {
//        “serialNumber”: 1,
//        “id”: “c8ef5295-f274-47df-bb0f-a98fccad77e5”,
//        “hasAllocation”: false,
//        “x”: 0,
//        “y”: 0
//        }

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatModelForSeatMap implements Comparable<SeatModelForSeatMap> {
    private Integer serialNumber;
    private String id;
    private boolean hasAllocation;
    private Integer x;
    private Integer y;

    public SeatModelForSeatMap() {
    }

    public SeatModelForSeatMap(Integer serialNumber, String id, boolean hasAllocation, Integer x, Integer y) {
        this.serialNumber = serialNumber;
        this.id = id;
        this.hasAllocation = hasAllocation;
        this.x = x;
        this.y = y;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHasAllocation() {
        return hasAllocation;
    }

    public void setHasAllocation(boolean hasAllocation) {
        this.hasAllocation = hasAllocation;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public int compareTo(SeatModelForSeatMap anotherSeat) {
        return this.serialNumber - anotherSeat.getSerialNumber();
    }
}
