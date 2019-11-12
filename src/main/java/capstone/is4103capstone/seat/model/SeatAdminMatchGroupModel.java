package capstone.is4103capstone.seat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatAdminMatchGroupModel implements Serializable {
    private List<SeatAdminMatchModel> accessibleEntities = new ArrayList<>();

    public SeatAdminMatchGroupModel() {
    }

    public SeatAdminMatchGroupModel(List<SeatAdminMatchModel> accessibleEntities) {
        this.accessibleEntities = accessibleEntities;
    }

    public List<SeatAdminMatchModel> getAccessibleEntities() {
        return accessibleEntities;
    }

    public void setAccessibleEntities(List<SeatAdminMatchModel> accessibleEntities) {
        this.accessibleEntities = accessibleEntities;
    }
}
