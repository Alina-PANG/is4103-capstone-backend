package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatBlockedForUseDateModel implements Serializable {
    private GroupModel parent;
    private List<SeatBlockedForUseDateModel> children = new ArrayList<>();
    private JSONObject seatBlockedCountWithDate;

    public SeatBlockedForUseDateModel() {
    }

    public SeatBlockedForUseDateModel(GroupModel parent, List<SeatBlockedForUseDateModel> children, JSONObject seatBlockedCountWithDate) {
        this.parent = parent;
        this.children = children;
        this.seatBlockedCountWithDate = seatBlockedCountWithDate;
    }

    public GroupModel getParent() {
        return parent;
    }

    public void setParent(GroupModel parent) {
        this.parent = parent;
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
