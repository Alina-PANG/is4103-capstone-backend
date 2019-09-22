package capstone.is4103capstone.finance.admin.model.res;

import capstone.is4103capstone.finance.admin.model.FXRecordModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class ViewFXRecordRes extends GeneralRes {
    int totalRecord;
    String startDate;
    String endDate;

    List<FXRecordModel> records = new ArrayList<>();

    public ViewFXRecordRes() {
    }

    public ViewFXRecordRes(String message, Boolean hasError, int totalRecord, String startDate, String endDate, List<FXRecordModel> records) {
        super(message, hasError);
        this.totalRecord = totalRecord;
        this.startDate = startDate;
        this.endDate = endDate;
        this.records = records;
    }

    public ViewFXRecordRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<FXRecordModel> getRecords() {
        return records;
    }

    public void setRecords(List<FXRecordModel> records) {
        this.records = records;
    }
}
