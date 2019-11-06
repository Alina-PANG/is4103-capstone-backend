package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatUtilisationDataModel implements Serializable {
    private GroupModel entity;
    private List<SeatUtilisationDataModel> children = new ArrayList<>();
    private JSONObject seatInventoryCountWithDate;
    private JSONObject seatOccupancyCountWithDate;

    public SeatUtilisationDataModel() {
    }

    public SeatUtilisationDataModel(GroupModel entity, List<SeatUtilisationDataModel> children, JSONObject seatInventoryCountWithDate,
                                    JSONObject seatOccupancyCountWithDate) {
        this.entity = entity;
        this.children = children;
        this.seatInventoryCountWithDate = seatInventoryCountWithDate;
        this.seatOccupancyCountWithDate = seatOccupancyCountWithDate;
    }

    public GroupModel getEntity() {
        return entity;
    }

    public void setEntity(GroupModel entity) {
        this.entity = entity;
    }

    public List<SeatUtilisationDataModel> getChildren() {
        return children;
    }

    public void setChildren(List<SeatUtilisationDataModel> children) {
        this.children = children;
    }

    public JSONObject getSeatInventoryCountWithDate() {
        return seatInventoryCountWithDate;
    }

    public void setSeatInventoryCountWithDate(JSONObject seatInventoryCountWithDate) {
        this.seatInventoryCountWithDate = seatInventoryCountWithDate;
    }

    public JSONObject getSeatOccupancyCountWithDate() {
        return seatOccupancyCountWithDate;
    }

    public void setSeatOccupancyCountWithDate(JSONObject seatOccupancyCountWithDate) {
        this.seatOccupancyCountWithDate = seatOccupancyCountWithDate;
    }
}
