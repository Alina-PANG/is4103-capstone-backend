package capstone.is4103capstone.seat.model.seatDataAnalytics;

import java.io.Serializable;
import java.util.Date;

public class SeatUtilisationModel implements Serializable, Comparable<SeatUtilisationModel> {
    private String entityId;
    private Integer inventoryCount = 0;
    private Integer occupancyCount = 0;
    private Integer unoccupiedCount = 0;
    private Date date;

    public SeatUtilisationModel() {
    }

    public SeatUtilisationModel(String entityId, Integer inventoryCount, Integer occupancyCount, Date date) {
        this.entityId = entityId;
        this.inventoryCount = inventoryCount;
        this.occupancyCount = occupancyCount;
        this.date = date;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
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

    public Integer getUnoccupiedCount() {
        return unoccupiedCount;
    }

    public void setUnoccupiedCount(Integer unoccupiedCount) {
        this.unoccupiedCount = unoccupiedCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(SeatUtilisationModel anotherSeatUtilisationModel) {
        if (this.date.before(anotherSeatUtilisationModel.date)) {
            return -1;
        } else if (this.date.equals(anotherSeatUtilisationModel)) {
            return 0;
        } else {
            return 1;
        }
    }
}
