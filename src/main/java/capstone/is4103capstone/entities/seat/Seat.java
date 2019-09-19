package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Seat extends DBEntityTemplate implements Comparable<Seat> {
    // Seat code example: SG-ORQ-26-01

    @Enumerated(EnumType.STRING)
    private SeatTypeEnum type = SeatTypeEnum.FIXED;
    @NotNull
    @Min(0)
    private Integer xCoordinate;
    @NotNull
    @Min(0)
    private Integer yCoordinate;
    @NotNull
    @Min(1)
    private Integer serialNumber;

    @OneToOne
    private SeatAllocation currentOccupancy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatmap_id")
    @JsonIgnore
    @NotNull
    private SeatMap seatMap;
    @OneToMany(mappedBy = "seat")
    private List<SeatAllocation> activeSeatAllocations = new ArrayList<>();
    @OneToMany(mappedBy = "seat")
    private List<SeatAllocation> inactiveSeatAllocations = new ArrayList<>();

    public Seat() {

    }

    public Seat(String objectName, String code, String hierachyPath, Integer xCoordinate, Integer yCoordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.seatMap = seatMap;
    }

    public Seat(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Integer xCoordinate, Integer yCoordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.seatMap = seatMap;
    }

    public SeatTypeEnum getType() {
        return type;
    }

    public void setType(SeatTypeEnum type) {
        this.type = type;
    }

    public Integer getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(Integer xCoordinate) { this.xCoordinate = xCoordinate; }

    public Integer getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(Integer yCoordinate) { this.yCoordinate = yCoordinate; }

    public Integer getSerialNumber() { return serialNumber; }

    public void setSerialNumber(Integer serialNumber) { this.serialNumber = serialNumber; }

    public SeatMap getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(SeatMap seatMap) {
        this.seatMap = seatMap;
    }

    public SeatAllocation getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(SeatAllocation currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public List<SeatAllocation> getActiveSeatAllocations() {
        return activeSeatAllocations;
    }

    public void setActiveSeatAllocations(List<SeatAllocation> activeSeatAllocations) {
        this.activeSeatAllocations = activeSeatAllocations;
    }

    public List<SeatAllocation> getInactiveSeatAllocations() {
        return inactiveSeatAllocations;
    }

    public void setInactiveSeatAllocations(List<SeatAllocation> inactiveSeatAllocations) {
        this.inactiveSeatAllocations = inactiveSeatAllocations;
    }

    @Override
    public int compareTo(Seat anotherSeat) {
        return this.serialNumber - anotherSeat.getSerialNumber();
    }
}
