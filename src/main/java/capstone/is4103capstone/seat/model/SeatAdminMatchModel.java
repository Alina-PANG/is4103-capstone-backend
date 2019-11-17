package capstone.is4103capstone.seat.model;

import capstone.is4103capstone.seat.model.GroupModel;

import java.io.Serializable;

public class SeatAdminMatchModel implements Serializable {
    private GroupModel groupModel;
    private String hierarchyType;

    public SeatAdminMatchModel() {
    }

    public SeatAdminMatchModel(GroupModel groupModel, String hierarchyType) {
        this.groupModel = groupModel;
        this.hierarchyType = hierarchyType;
    }

    public GroupModel getGroupModel() {
        return groupModel;
    }

    public void setGroupModel(GroupModel groupModel) {
        this.groupModel = groupModel;
    }

    public String getHierarchyType() {
        return hierarchyType;
    }

    public void setHierarchyType(String hierarchyType) {
        this.hierarchyType = hierarchyType;
    }
}
