package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Seat extends DBEntityTemplate implements Comparable<Seat> {

    // Seat code example: SG-ORQ-26-01

    @Enumerated(EnumType.STRING)
    private SeatTypeEnum type = SeatTypeEnum.HOTDESK;
    @NotNull
    @Min(0)
    private Integer xCoordinate;
    @NotNull
    @Min(0)
    private Integer yCoordinate;
    @NotNull
    @Min(1)
    private Integer serialNumber;

    @NotNull
    private boolean underOffice = false;
    private Integer adjacentSeatSeqNum; // the sequence number of the adjacent seat in the same office; can be null (only one seat in an office)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id")
    @JsonIgnore
    private CompanyFunction functionAssigned;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id")
    @JsonIgnore
    private BusinessUnit businessUnitAssigned;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team teamAssigned;

    @OneToOne
    private SeatAllocation currentOccupancy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatmap_id")
    @JsonIgnore
    private SeatMap seatMap;
    private String originalSeatMapId;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "seat_activeallocations",
            joinColumns = @JoinColumn(name = "seat_id"),
            inverseJoinColumns = @JoinColumn(name = "allocation_id")
    )
    private List<SeatAllocation> activeSeatAllocations = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "seat_inactiveallocations",
            joinColumns = @JoinColumn(name = "seat_id"),
            inverseJoinColumns = @JoinColumn(name = "allocation_id")
    )
    private List<SeatAllocation> inactiveSeatAllocations = new ArrayList<>();

    public Seat() {
        this.setCreatedDateTime(new Date());
    }

    public Seat(String objectName, String code, String hierachyPath, Integer xCoordinate, Integer yCoordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.seatMap = seatMap;
        this.originalSeatMapId = seatMap.getId();
        this.setCreatedDateTime(new Date());
    }

    public Seat(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Integer xCoordinate, Integer yCoordinate, @NotNull SeatMap seatMap) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.seatMap = seatMap;
        this.originalSeatMapId = seatMap.getId();
        this.setCreatedDateTime(new Date());
    }

    public SeatTypeEnum getType() {
        return type;
    }

    public void setType(SeatTypeEnum type) {
        this.type = type;
        this.setLastModifiedDateTime(new Date());
    }

    public Integer getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(Integer xCoordinate) {
        this.xCoordinate = xCoordinate;
        this.setLastModifiedDateTime(new Date());
    }

    public Integer getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(Integer yCoordinate) {
        this.yCoordinate = yCoordinate;
        this.setLastModifiedDateTime(new Date());
    }

    public Integer getSerialNumber() { return serialNumber; }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
        this.setLastModifiedDateTime(new Date());
    }

    public boolean isUnderOffice() {
        return underOffice;
    }

    public void setUnderOffice(boolean underOffice) {
        this.underOffice = underOffice;
    }

    public Integer getAdjacentSeatSeqNum() {
        return adjacentSeatSeqNum;
    }

    public void setAdjacentSeatSeqNum(Integer adjacentSeatSeqNum) {
        this.adjacentSeatSeqNum = adjacentSeatSeqNum;
        this.setLastModifiedDateTime(new Date());
    }

    public CompanyFunction getFunctionAssigned() {
        return functionAssigned;
    }

    public void setFunctionAssigned(CompanyFunction functionAssigned) {
        this.functionAssigned = functionAssigned;
        this.setLastModifiedDateTime(new Date());
    }

    public BusinessUnit getBusinessUnitAssigned() { return businessUnitAssigned; }

    public void setBusinessUnitAssigned(BusinessUnit businessUnitAssigned) {
        this.businessUnitAssigned = businessUnitAssigned;
        this.setLastModifiedDateTime(new Date());
    }

    public Team getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(Team teamAssigned) {
        this.teamAssigned = teamAssigned;
        this.setLastModifiedDateTime(new Date());
    }

    public String getOriginalSeatMapId() {
        return originalSeatMapId;
    }

    public void setOriginalSeatMapId(String originalSeatMapId) {
        this.originalSeatMapId = originalSeatMapId;
        this.setLastModifiedDateTime(new Date());
    }

    public SeatMap getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(SeatMap seatMap) {
        this.seatMap = seatMap;
        if (seatMap != null) {
            this.originalSeatMapId = seatMap.getId();
        }

        this.setLastModifiedDateTime(new Date());
    }

    public SeatAllocation getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(SeatAllocation currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
        this.setLastModifiedDateTime(new Date());
    }

    public List<SeatAllocation> getActiveSeatAllocations() {
        return activeSeatAllocations;
    }

    public void setActiveSeatAllocations(List<SeatAllocation> activeSeatAllocations) {
        this.activeSeatAllocations = activeSeatAllocations;
        this.setLastModifiedDateTime(new Date());
    }

    public List<SeatAllocation> getInactiveSeatAllocations() {
        return inactiveSeatAllocations;
    }

    public void setInactiveSeatAllocations(List<SeatAllocation> inactiveSeatAllocations) {
        this.inactiveSeatAllocations = inactiveSeatAllocations;
        this.setLastModifiedDateTime(new Date());
    }

    @Override
    public int compareTo(Seat anotherSeat) {
        return this.serialNumber - anotherSeat.getSerialNumber();
    }
}
