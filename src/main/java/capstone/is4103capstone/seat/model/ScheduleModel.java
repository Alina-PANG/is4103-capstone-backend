package capstone.is4103capstone.seat.model;

import java.util.Date;

public class ScheduleModel {
    private String id;
    private Date startDateTime; // >= today
    private Date endDateTime; // > startDateTime

    public ScheduleModel() {
    }

    public ScheduleModel(String id, Date startDateTime, Date endDateTime) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
}
