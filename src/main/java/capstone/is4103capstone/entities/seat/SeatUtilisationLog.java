package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class SeatUtilisationLog implements Comparable<SeatUtilisationLog> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false,length = 36)
    @Length(min=36, max=36)
    private String id;

    @NotNull
    private String levelEntityId;
    @NotNull
    private String officeId;
    @NotNull
    private HierarchyTypeEnum hierarchyType;

    @NotNull
    @Min(0)
    private Integer inventoryCount = 0;
    @NotNull
    @Min(0)
    private Integer occupancyCount = 0;


    @NotNull
    @Min(1000)
    @Max(9999)
    private Integer year;
    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;
    @NotNull
    @Min(1)
    @Max(31)
    private Integer dayOfMonth;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public SeatUtilisationLog() {
    }

    public SeatUtilisationLog(@Length(min = 36, max = 36) String id, @NotNull String levelEntityId, @NotNull String officeId,
                              @NotNull HierarchyTypeEnum hierarchyType, @NotNull @Min(0) Integer inventoryCount,
                              @NotNull @Min(0) Integer occupancyCount, @NotNull @Min(1000) @Max(9999) Integer year,
                              @NotNull @Min(1) @Max(12) Integer month, @NotNull @Min(1) @Max(31) Integer dayOfMonth, Date createdTime) {
        this.id = id;
        this.levelEntityId = levelEntityId;
        this.officeId = officeId;
        this.hierarchyType = hierarchyType;
        this.inventoryCount = inventoryCount;
        this.occupancyCount = occupancyCount;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevelEntityId() {
        return levelEntityId;
    }

    public void setLevelEntityId(String levelEntityId) {
        this.levelEntityId = levelEntityId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public HierarchyTypeEnum getHierarchyType() {
        return hierarchyType;
    }

    public void setHierarchyType(HierarchyTypeEnum hierarchyType) {
        this.hierarchyType = hierarchyType;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public Integer getOccupancyCount() {
        return occupancyCount;
    }

    public void setOccupancyCount(Integer occupancyCount) {
        this.occupancyCount = occupancyCount;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public int compareTo(SeatUtilisationLog anotherLog) {
        if (this.createdTime.before(anotherLog.createdTime)) {
            return -1;
        } else if (this.createdTime.equals(anotherLog.createdTime)) {
            return 0;
        } else {
            return 1;
        }
    }
}
