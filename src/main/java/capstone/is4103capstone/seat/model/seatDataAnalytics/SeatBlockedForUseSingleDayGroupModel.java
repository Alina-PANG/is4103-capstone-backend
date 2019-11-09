package capstone.is4103capstone.seat.model.seatDataAnalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatBlockedForUseSingleDayGroupModel implements Serializable {
    private List<SeatBlockedForUseSingleDayModel> usageData = new ArrayList<>();

    public SeatBlockedForUseSingleDayGroupModel() {
    }

    public SeatBlockedForUseSingleDayGroupModel(List<SeatBlockedForUseSingleDayModel> usageData) {
        this.usageData = usageData;
    }

    public List<SeatBlockedForUseSingleDayModel> getUsageData() {
        return usageData;
    }

    public void setUsageData(List<SeatBlockedForUseSingleDayModel> usageData) {
        this.usageData = usageData;
    }
}
