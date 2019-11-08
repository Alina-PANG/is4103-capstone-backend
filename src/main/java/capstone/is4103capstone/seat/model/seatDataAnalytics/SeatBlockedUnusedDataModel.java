package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatBlockedUnusedDataModel implements Serializable {
    private GroupModel entity;
    private Integer currentInventoryCount = 0;
    private Integer unusedCount = 0;
    private List<SeatBlockedUnusedDataModel> children = new ArrayList<>();

    public SeatBlockedUnusedDataModel() {
    }

    public SeatBlockedUnusedDataModel(GroupModel entity, Integer currentInventoryCount, Integer unusedCount, List<SeatBlockedUnusedDataModel> children) {
        this.entity = entity;
        this.currentInventoryCount = currentInventoryCount;
        this.unusedCount = unusedCount;
        this.children = children;
    }

    public GroupModel getEntity() {
        return entity;
    }

    public void setEntity(GroupModel entity) {
        this.entity = entity;
    }

    public Integer getCurrentInventoryCount() {
        return currentInventoryCount;
    }

    public void setCurrentInventoryCount(Integer currentInventoryCount) {
        this.currentInventoryCount = currentInventoryCount;
    }

    public Integer getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(Integer unusedCount) {
        this.unusedCount = unusedCount;
    }

    public List<SeatBlockedUnusedDataModel> getChildren() {
        return children;
    }

    public void setChildren(List<SeatBlockedUnusedDataModel> children) {
        this.children = children;
    }
}
