package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Seat extends DBEntityTemplate {
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
    private List<SeatAllocation> seatAllocations = new ArrayList<>();

    public Seat() {
    }

    public Seat(String objectName, String code, String hierachyPath, Point2D.Double coordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath);
        this.coordinate = coordinate;
        this.seatMap = seatMap;
    }

    public Seat(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Point2D.Double coordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.coordinate = coordinate;
        this.seatMap = seatMap;
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
