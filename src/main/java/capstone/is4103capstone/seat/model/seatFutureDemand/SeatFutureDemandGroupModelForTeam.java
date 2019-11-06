package capstone.is4103capstone.seat.model.seatFutureDemand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatFutureDemandGroupModelForTeam implements Serializable {
    private List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam = new ArrayList<>();

    public SeatFutureDemandGroupModelForTeam() {
    }

    public SeatFutureDemandGroupModelForTeam(List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam) {
        this.monthlySeatForecastModelsForTeam = monthlySeatForecastModelsForTeam;
    }

    public List<MonthlySeatFutureDemandModelForTeam> getMonthlySeatForecastModelsForTeam() {
        return monthlySeatForecastModelsForTeam;
    }

    public void setMonthlySeatForecastModelsForTeam(List<MonthlySeatFutureDemandModelForTeam> monthlySeatForecastModelsForTeam) {
        this.monthlySeatForecastModelsForTeam = monthlySeatForecastModelsForTeam;
    }
}
