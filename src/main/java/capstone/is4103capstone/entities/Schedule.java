package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule extends DBEntityTemplate implements Comparable<Schedule> {
    @NotNull
    private boolean isRecurring = false;
    private ScheduleRecurringBasisEnum recurringBasis;
    @ElementCollection(targetClass=DayOfWeek.class)
    private List<DayOfWeek> recurringWeekdays = new ArrayList<>();
    @ElementCollection(targetClass=Date.class)
    private List<Date> recurringDates = new ArrayList<>();
    private LocalTime recurringStartTime;
    private LocalTime recurringEndTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    public Schedule() {

    }

    public Schedule(String objectName, String code, String hierachyPath, boolean isRecurring, ScheduleRecurringBasisEnum recurringBasis, List<DayOfWeek> recurringWeekdays, List<Date> recurringDates, LocalTime recurringStartTime, LocalTime recurringEndTime, Date startDateTime, Date endDateTime) {
        super(objectName, code, hierachyPath);
        this.isRecurring = isRecurring;
        this.recurringBasis = recurringBasis;
        this.recurringWeekdays = recurringWeekdays;
        this.recurringDates = recurringDates;
        this.recurringStartTime = recurringStartTime;
        this.recurringEndTime = recurringEndTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Schedule(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, boolean isRecurring, ScheduleRecurringBasisEnum recurringBasis, List<DayOfWeek> recurringWeekdays, List<Date> recurringDates, LocalTime recurringStartTime, LocalTime recurringEndTime, Date startDateTime, Date endDateTime) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.isRecurring = isRecurring;
        this.recurringBasis = recurringBasis;
        this.recurringWeekdays = recurringWeekdays;
        this.recurringDates = recurringDates;
        this.recurringStartTime = recurringStartTime;
        this.recurringEndTime = recurringEndTime;
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

    @Override
    public int compareTo(Schedule anotherSchedule) {
        if (this.startDateTime.before(anotherSchedule.startDateTime)) {
            return  -1;
        } else if (this.startDateTime.equals(anotherSchedule.startDateTime)) {
            if (this.endDateTime != null) {
                if (anotherSchedule.endDateTime == null) {
                    return -1;
                } else {
                    if (this.endDateTime.before(anotherSchedule.endDateTime)) {
                        return -1;
                    } else if (this.endDateTime.equals(anotherSchedule.endDateTime)) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            } else {
                if (anotherSchedule.endDateTime == null) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    }
}
