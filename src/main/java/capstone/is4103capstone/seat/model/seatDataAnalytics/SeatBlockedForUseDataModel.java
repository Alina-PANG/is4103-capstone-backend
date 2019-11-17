package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatBlockedForUseDataModel implements Serializable {
    private GroupModel entity;
    private List<SeatBlockedForUseDataModel> children = new ArrayList<>();
    private SeatBlockedForUseSingleDayGroupModel seatBlockedCountWithDate;

    public SeatBlockedForUseDataModel() {
    }

    public SeatBlockedForUseDataModel(GroupModel entity, List<SeatBlockedForUseDataModel> children, SeatBlockedForUseSingleDayGroupModel seatBlockedCountWithDate) {
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

    public List<SeatBlockedForUseDataModel> getChildren() {
        return children;
    }

    public void setChildren(List<SeatBlockedForUseDataModel> children) {
        this.children = children;
    }

    public SeatBlockedForUseSingleDayGroupModel getSeatBlockedCountWithDate() {
        return seatBlockedCountWithDate;
    }

    public void setSeatBlockedCountWithDate(SeatBlockedForUseSingleDayGroupModel seatBlockedCountWithDate) {
        this.seatBlockedCountWithDate = seatBlockedCountWithDate;
    }
}
