package capstone.is4103capstone.seat.model.seatMap;

import java.io.Serializable;

public class SeatMapExistenceCheckingModel implements Serializable {
    private boolean doesExist;

    public SeatMapExistenceCheckingModel(boolean doesExist) {
        this.doesExist = doesExist;
    }

    public boolean isDoesExist() {
        return doesExist;
    }

    public void setDoesExist(boolean doesExist) {
        this.doesExist = doesExist;
    }
}
