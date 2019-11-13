package capstone.is4103capstone.finance.dashboard.model.res;

import capstone.is4103capstone.finance.dashboard.model.PoPmtScheduleModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class PoSchedulRes extends GeneralRes {
    List<PoPmtScheduleModel> records;

    public PoSchedulRes() {
    }

    public PoSchedulRes(String message, Boolean hasError, List<PoPmtScheduleModel> records) {
        super(message, hasError);
        this.records = records;
    }

    public List<PoPmtScheduleModel> getRecords() {
        return records;
    }

    public void setRecords(List<PoPmtScheduleModel> records) {
        this.records = records;
    }
}
