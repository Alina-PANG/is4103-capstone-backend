package capstone.is4103capstone.seat.model.schedule;

import capstone.is4103capstone.seat.model.schedule.ScheduleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BulkScheduleModel implements Serializable {
    private List<ScheduleModel> schedules = new ArrayList<>();

    public BulkScheduleModel() {
    }

    public BulkScheduleModel(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }

    public List<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }
}
