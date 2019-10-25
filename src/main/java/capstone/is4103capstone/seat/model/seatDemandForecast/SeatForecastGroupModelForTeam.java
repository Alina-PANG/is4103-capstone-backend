package capstone.is4103capstone.seat.model.seatDemandForecast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatForecastGroupModelForTeam implements Serializable {
    private List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam = new ArrayList<>();

    public SeatForecastGroupModelForTeam() {
    }

    public SeatForecastGroupModelForTeam(List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam) {
        this.monthlySeatForecastModelsForTeam = monthlySeatForecastModelsForTeam;
    }

    public List<MonthlySeatForecastModelForTeam> getMonthlySeatForecastModelsForTeam() {
        return monthlySeatForecastModelsForTeam;
    }

    public void setMonthlySeatForecastModelsForTeam(List<MonthlySeatForecastModelForTeam> monthlySeatForecastModelsForTeam) {
        this.monthlySeatForecastModelsForTeam = monthlySeatForecastModelsForTeam;
    }
}
