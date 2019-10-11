package capstone.is4103capstone.seat.model.seat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatModelForSeatMap implements Comparable<SeatModelForSeatMap>, Serializable {
    private Integer serialNumber;
    private String id;
    private boolean hasAllocation;
    private Integer x;
    private Integer y;
    private boolean underOffice;
    private Integer adjacentSeatSeqNum;

    public SeatModelForSeatMap() {
    }

    public SeatModelForSeatMap(Integer serialNumber, String id, boolean hasAllocation, Integer x, Integer y, boolean underOffice, Integer adjacentSeatSeqNum) {
        this.serialNumber = serialNumber;
        this.id = id;
        this.hasAllocation = hasAllocation;
        this.x = x;
        this.y = y;
        this.underOffice = underOffice;
        this.adjacentSeatSeqNum = adjacentSeatSeqNum;
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

    public boolean isUnderOffice() { return underOffice; }

    public void setUnderOffice(boolean underOffice) { this.underOffice = underOffice; }

    public Integer getAdjacentSeatSeqNum() { return adjacentSeatSeqNum; }

    public void setAdjacentSeatSeqNum(Integer adjacentSeatSeqNum) { this.adjacentSeatSeqNum = adjacentSeatSeqNum; }

    @Override
    public int compareTo(SeatModelForSeatMap anotherSeat) {
        return this.serialNumber - anotherSeat.getSerialNumber();
    }
}
