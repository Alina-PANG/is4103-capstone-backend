package capstone.is4103capstone.seat.model.seatDataAnalytics;

import capstone.is4103capstone.entities.CompanyFunction;

import java.io.Serializable;
import java.util.Date;

public class SeatUtilisationLogModel implements Serializable, Comparable<SeatUtilisationLogModel> {
    private Date date;
    private Integer year;
    private Integer month;
    private Integer dayOfMonth;
    private Integer inventoryCount;
    private Integer occupancyCount;

    public SeatUtilisationLogModel() {
    }

    public SeatUtilisationLogModel(Date date, Integer year, Integer month, Integer dayOfMonth, Integer inventoryCount, Integer occupancyCount) {
        this.date = date;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.inventoryCount = inventoryCount;
        this.occupancyCount = occupancyCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public Integer getOccupancyCount() {
        return occupancyCount;
    }

    public void setOccupancyCount(Integer occupancyCount) {
        this.occupancyCount = occupancyCount;
    }

    @Override
    public int compareTo(SeatUtilisationLogModel anotherModel) {
        if (this.date.before(anotherModel.date)) {
            return -1;
        } else if (this.date.equals(anotherModel.date)) {
            return 0;
        } else {
            return 1;
        }
    }
}
