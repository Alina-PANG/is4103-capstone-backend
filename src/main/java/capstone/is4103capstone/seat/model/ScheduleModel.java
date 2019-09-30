package capstone.is4103capstone.seat.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleModel implements Serializable {
    private String id;
    private Date startDateTime; // >= today && < one year later
    private Date endDateTime; // > startDateTime
    private boolean isRecurring;
    // "EveryDay" (recurringStartTime, recurringEndTime),
    // "EveryWeek" (recurringWeekdays, recurringStartTime, recurringEndTime),
    // "EveryYear" (recurringDate, recurringStartTime, recurringEndTime)
    private String recurringBasis;
    private List<Integer> recurringWeekdays = new ArrayList<>();
    private List<Date> recurringDates = new ArrayList<>();
    private LocalTime recurringStartTime;
    private LocalTime recurringEndTime;


    public ScheduleModel() {
    }

    public ScheduleModel(String id, Date startDateTime, Date endDateTime, boolean isRecurring, String recurringBasis,
                         List<Integer> recurringWeekdays, List<Date> recurringDates, LocalTime recurringStartTime, LocalTime recurringEndTime) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isRecurring = isRecurring;
        this.recurringBasis = recurringBasis;
        this.recurringWeekdays = recurringWeekdays;
        this.recurringDates = recurringDates;
        this.recurringStartTime = recurringStartTime;
        this.recurringEndTime = recurringEndTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public String getRecurringBasis() {
        return recurringBasis;
    }

    public void setRecurringBasis(String recurringBasis) {
        this.recurringBasis = recurringBasis;
    }

    public List<Integer> getRecurringWeekdays() {
        return recurringWeekdays;
    }

    public void setRecurringWeekdays(List<Integer> recurringWeekdays) {
        this.recurringWeekdays = recurringWeekdays;
    }

    public List<Date> getRecurringDates() {
        return recurringDates;
    }

    public void setRecurringDates(List<Date> recurringDates) {
        this.recurringDates = recurringDates;
    }

    public LocalTime getRecurringStartTime() {
        return recurringStartTime;
    }

    public void setRecurringStartTime(LocalTime recurringStartTime) {
        this.recurringStartTime = recurringStartTime;
    }

    public LocalTime getRecurringEndTime() {
        return recurringEndTime;
    }

    public void setRecurringEndTime(LocalTime recurringEndTime) {
        this.recurringEndTime = recurringEndTime;
    }
}
