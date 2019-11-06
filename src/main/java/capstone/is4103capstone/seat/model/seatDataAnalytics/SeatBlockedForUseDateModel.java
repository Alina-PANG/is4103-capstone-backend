package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatBlockedForUseDateModel implements Serializable {
    private GroupModel entity;
    private List<SeatBlockedForUseDateModel> children = new ArrayList<>();
    private JSONObject seatBlockedCountWithDate;

    public SeatBlockedForUseDateModel() {
    }

    public SeatBlockedForUseDateModel(GroupModel entity, List<SeatBlockedForUseDateModel> children, JSONObject seatBlockedCountWithDate) {
        this.entity = entity;
        this.children = children;
        this.seatBlockedCountWithDate = seatBlockedCountWithDate;
    }

    public GroupModel getEntity() {
        return entity;
    }

    public void setEntity(GroupModel entity) {
        this.entity = entity;
    }

    public List<SeatBlockedForUseDateModel> getChildren() {
        return children;
    }

    public void setChildren(List<SeatBlockedForUseDateModel> children) {
        this.children = children;
    }

    public JSONObject getSeatBlockedCountWithDate() {
        return seatBlockedCountWithDate;
    }

    public void setSeatBlockedCountWithDate(JSONObject seatBlockedCountWithDate) {
        this.seatBlockedCountWithDate = seatBlockedCountWithDate;
    }
}
