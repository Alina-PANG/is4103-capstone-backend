package capstone.is4103capstone.seat.model.schedule;

import java.io.Serializable;
import java.util.List;

public class BulkEmployeeOfficeWorkingScheduleModel implements Serializable {

    private List<EmployeeOfficeWorkingScheduleModel> employeeOfficeWorkingScheduleModels;

    public BulkEmployeeOfficeWorkingScheduleModel() {
    }

    public BulkEmployeeOfficeWorkingScheduleModel(List<EmployeeOfficeWorkingScheduleModel> employeeOfficeWorkingScheduleModels) {
        this.employeeOfficeWorkingScheduleModels = employeeOfficeWorkingScheduleModels;
    }

    public List<EmployeeOfficeWorkingScheduleModel> getEmployeeOfficeWorkingScheduleModels() {
        return employeeOfficeWorkingScheduleModels;
    }

    public void setEmployeeOfficeWorkingScheduleModels(List<EmployeeOfficeWorkingScheduleModel> employeeOfficeWorkingScheduleModels) {
        this.employeeOfficeWorkingScheduleModels = employeeOfficeWorkingScheduleModels;
    }
}
