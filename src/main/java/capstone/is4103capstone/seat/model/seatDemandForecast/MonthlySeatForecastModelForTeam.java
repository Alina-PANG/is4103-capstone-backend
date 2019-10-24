package capstone.is4103capstone.seat.model.seatDemandForecast;

import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonthlySeatForecastModelForTeam implements Serializable {
    private Integer seqNum;
    private String yearMonth;
    private GroupModel team;
    private Integer inventoryCount;
    private Integer numOfSeatsOccupied;
    private List<EmployeeModel> employeesWhoNeedSeats = new ArrayList<>();
    private List<EmployeeModel> employeesWithUnnecessaryAllocations = new ArrayList<>();

    public MonthlySeatForecastModelForTeam() {
    }

    public MonthlySeatForecastModelForTeam(Integer seqNum, String yearMonth, GroupModel team, Integer inventoryCount,
                                           Integer numOfSeatsOccupied, List<EmployeeModel> employeesWhoNeedSeats,
                                           List<EmployeeModel> employeesWithUnnecessaryAllocations) {
        this.seqNum = seqNum;
        this.yearMonth = yearMonth;
        this.team = team;
        this.inventoryCount = inventoryCount;
        this.numOfSeatsOccupied = numOfSeatsOccupied;
        this.employeesWhoNeedSeats = employeesWhoNeedSeats;
        this.employeesWithUnnecessaryAllocations = employeesWithUnnecessaryAllocations;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public GroupModel getTeam() {
        return team;
    }

    public void setTeam(GroupModel team) {
        this.team = team;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public Integer getNumOfSeatsOccupied() {
        return numOfSeatsOccupied;
    }

    public void setNumOfSeatsOccupied(Integer numOfSeatsOccupied) {
        this.numOfSeatsOccupied = numOfSeatsOccupied;
    }

    public List<EmployeeModel> getEmployeesWhoNeedSeats() {
        return employeesWhoNeedSeats;
    }

    public void setEmployeesWhoNeedSeats(List<EmployeeModel> employeesWhoNeedSeats) {
        this.employeesWhoNeedSeats = employeesWhoNeedSeats;
    }

    public List<EmployeeModel> getEmployeesWithUnnecessaryAllocations() {
        return employeesWithUnnecessaryAllocations;
    }

    public void setEmployeesWithUnnecessaryAllocations(List<EmployeeModel> employeesWithUnnecessaryAllocations) {
        this.employeesWithUnnecessaryAllocations = employeesWithUnnecessaryAllocations;
    }
}
