package capstone.is4103capstone.seat.model.seatDataAnalytics;

import java.io.Serializable;
import java.util.Date;

public class SeatBlockedForUseSingleDayModel implements Serializable {
    private Date date;
    private Integer numOfSeats;

    public SeatBlockedForUseSingleDayModel() {
    }

    public SeatBlockedForUseSingleDayModel(Date date, Integer numOfSeats) {
        this.date = date;
        this.numOfSeats = numOfSeats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(Integer numOfSeats) {
        this.numOfSeats = numOfSeats;
    }
}
