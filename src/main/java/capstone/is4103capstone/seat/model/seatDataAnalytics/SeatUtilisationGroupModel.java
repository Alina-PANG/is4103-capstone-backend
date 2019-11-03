package capstone.is4103capstone.seat.model.seatDataAnalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatUtilisationGroupModel implements Serializable {
    private List<SeatUtilisationModel> seatUtilisationModels = new ArrayList<>();

    public SeatUtilisationGroupModel() {
    }

    public SeatUtilisationGroupModel(List<SeatUtilisationModel> seatUtilisationModels) {
        this.seatUtilisationModels = seatUtilisationModels;
    }

    public List<SeatUtilisationModel> getSeatUtilisationModels() {
        return seatUtilisationModels;
    }

    public void setSeatUtilisationModels(List<SeatUtilisationModel> seatUtilisationModels) {
        this.seatUtilisationModels = seatUtilisationModels;
    }
}
