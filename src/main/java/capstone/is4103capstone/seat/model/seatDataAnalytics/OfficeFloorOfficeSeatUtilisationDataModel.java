package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;

import java.io.Serializable;

public class OfficeFloorOfficeSeatUtilisationDataModel implements Serializable {
    private GroupModel officeFloor;
    private Integer inventoryCount;
    private Integer occupancyCount; // have active allocations

    public OfficeFloorOfficeSeatUtilisationDataModel() {
    }

    public OfficeFloorOfficeSeatUtilisationDataModel(GroupModel officeFloor, Integer inventoryCount, Integer occupancyCount) {
        this.officeFloor = officeFloor;
        this.inventoryCount = inventoryCount;
        this.occupancyCount = occupancyCount;
    }

    public GroupModel getOfficeFloor() {
        return officeFloor;
    }

    public void setOfficeFloor(GroupModel officeFloor) {
        this.officeFloor = officeFloor;
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
}
