package capstone.is4103capstone.seat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatAdminMatchGroupModel implements Serializable {
    private List<GroupModel> accessibleEntities = new ArrayList<>();

    public SeatAdminMatchGroupModel() {
    }

    public SeatAdminMatchGroupModel(List<GroupModel> accessibleEntities) {
        this.accessibleEntities = accessibleEntities;
    }

    public List<GroupModel> getAccessibleEntities() {
        return accessibleEntities;
    }

    public void setAccessibleEntities(List<GroupModel> accessibleEntities) {
        this.accessibleEntities = accessibleEntities;
    }
}
