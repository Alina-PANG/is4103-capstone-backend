package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Schedule extends DBEntityTemplate {
    @NotNull
    private boolean isRecurring = false;
    private ScheduleRecurringBasisEnum recurringBasis;
    @ElementCollection(targetClass=DayOfWeek.class)
    private List<DayOfWeek> recurringWeekdays = new ArrayList<>();
    @ElementCollection(targetClass=Date.class)
    private List<Date> recurringDates = new ArrayList<>();
    @ElementCollection(targetClass=Date.class)
    private List<Date> recurringTimes = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    public Schedule() {

    }

    public Schedule(String objectName, String code, String hierachyPath, boolean isRecurring, ScheduleRecurringBasisEnum recurringBasis, List<DayOfWeek> recurringWeekdays, List<Date> recurringDates, List<Date> recurringTimes, Date startDateTime, Date endDateTime) {
        super(objectName, code, hierachyPath);
        this.isRecurring = isRecurring;
        this.recurringBasis = recurringBasis;
        this.recurringWeekdays = recurringWeekdays;
        this.recurringDates = recurringDates;
        this.recurringTimes = recurringTimes;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Schedule(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, boolean isRecurring, ScheduleRecurringBasisEnum recurringBasis, List<DayOfWeek> recurringWeekdays, List<Date> recurringDates, List<Date> recurringTimes, Date startDateTime, Date endDateTime) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.isRecurring = isRecurring;
        this.recurringBasis = recurringBasis;
        this.recurringWeekdays = recurringWeekdays;
        this.recurringDates = recurringDates;
        this.recurringTimes = recurringTimes;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public ScheduleRecurringBasisEnum getRecurringBasis() {
        return recurringBasis;
    }

    public void setRecurringBasis(ScheduleRecurringBasisEnum recurringBasis) {
        this.recurringBasis = recurringBasis;
    }

    public List<DayOfWeek> getRecurringWeekdays() {
        return recurringWeekdays;
    }

    public void setRecurringWeekdays(List<DayOfWeek> recurringWeekdays) {
        this.recurringWeekdays = recurringWeekdays;
    }

    public List<Date> getRecurringDates() {
        return recurringDates;
    }

    public void setRecurringDates(List<Date> recurringDates) {
        this.recurringDates = recurringDates;
    }

    public List<Date> getRecurringTimes() {
        return recurringTimes;
    }

    public void setRecurringTimes(List<Date> recurringTimes) {
        this.recurringTimes = recurringTimes;
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
}
