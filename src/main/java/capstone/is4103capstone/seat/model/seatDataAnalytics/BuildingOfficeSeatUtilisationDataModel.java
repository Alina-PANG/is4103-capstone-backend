package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.seat.model.GroupModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuildingOfficeSeatUtilisationDataModel implements Serializable {
    private GroupModel office;
    private List<OfficeFloorOfficeSeatUtilisationDataModel> officeFloorOfficeSeatUtilisationDataModels = new ArrayList<>();

    public BuildingOfficeSeatUtilisationDataModel() {
    }

    public BuildingOfficeSeatUtilisationDataModel(GroupModel office, List<OfficeFloorOfficeSeatUtilisationDataModel> officeFloorOfficeSeatUtilisationDataModels) {
        this.office = office;
        this.officeFloorOfficeSeatUtilisationDataModels = officeFloorOfficeSeatUtilisationDataModels;
    }

    public GroupModel getOffice() {
        return office;
    }

    public void setOffice(GroupModel office) {
        this.office = office;
    }

    public List<OfficeFloorOfficeSeatUtilisationDataModel> getOfficeFloorOfficeSeatUtilisationDataModels() {
        return officeFloorOfficeSeatUtilisationDataModels;
    }

    public void setOfficeFloorOfficeSeatUtilisationDataModels(List<OfficeFloorOfficeSeatUtilisationDataModel> officeFloorOfficeSeatUtilisationDataModels) {
        this.officeFloorOfficeSeatUtilisationDataModels = officeFloorOfficeSeatUtilisationDataModels;
    }
}
