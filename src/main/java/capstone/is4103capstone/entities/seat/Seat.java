package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.OfficeFacility;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.awt.geom.Point2D;
import java.util.List;

@Entity
@Table
public class Seat extends OfficeFacility {
    @Enumerated(EnumType.STRING)
    private SeatTypeEnum type = SeatTypeEnum.FIXED;
    private Point2D.Double coordinate;

    @OneToOne
    private SeatAllocation currentOccupancy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatmap_id")
    @JsonIgnore
    @NotNull
    private SeatMap seatMap;
    @OneToMany(mappedBy = "seat")
    private List<SeatAllocation> seatAllocations;

    public Seat() {
    }

    public Seat(String objectName, String code, String hierachyPath, Point2D.Double coordinate) {
        super(objectName, code, hierachyPath);
        this.coordinate = coordinate;
    }

    public Seat(String objectName, String code, String hierachyPath, Office office, @Min(1) Integer floor, Point2D.Double coordinate) {
        super(objectName, code, hierachyPath, office, floor);
        this.coordinate = coordinate;
    }

    public SeatTypeEnum getType() {
        return type;
    }

    public void setType(SeatTypeEnum type) {
        this.type = type;
    }

    public Point2D.Double getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point2D.Double coordinate) {
        this.coordinate = coordinate;
    }

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

    public List<SeatAllocation> getSeatAllocations() {
        return seatAllocations;
    }

    public void setSeatAllocations(List<SeatAllocation> seatAllocations) {
        this.seatAllocations = seatAllocations;
    }
}
