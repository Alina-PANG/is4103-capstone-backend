package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatUtilisationDataModel implements Serializable {
    private GroupModel entity;
    private List<SeatUtilisationDataModel> children = new ArrayList<>();
    private List<SeatUtilisationLogModel> logs = new ArrayList<>();

    public SeatUtilisationDataModel() {
    }

    public SeatUtilisationDataModel(GroupModel entity, List<SeatUtilisationDataModel> children, List<SeatUtilisationLogModel> logs) {
        this.entity = entity;
        this.children = children;
        this.logs = logs;
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

    public List<SeatUtilisationLogModel> getLogs() {
        return logs;
    }

    public void setLogs(List<SeatUtilisationLogModel> logs) {
        this.logs = logs;
    }
}
