package capstone.is4103capstone.finance.dashboard.model.res;

import capstone.is4103capstone.finance.dashboard.model.FinDashboardAggLineModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;

public class FinDashboardRes extends GeneralRes {

    Integer topLevelCnt;

    Integer totalLevel;

    List<String> levelTitles;

    List<String> displayColumns;

    List<FinDashboardAggLineModel> result;

    public FinDashboardRes() {
    }

    public FinDashboardRes(String message, Boolean hasError, List<FinDashboardAggLineModel> result) {
        super(message, hasError);
        this.result = result;
    }

    public FinDashboardRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public Integer getTopLevelCnt() {
        return topLevelCnt;
    }

    public void setTopLevelCnt(Integer topLevelCnt) {
        this.topLevelCnt = topLevelCnt;
    }

    public Integer getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(Integer totalLevel) {
        this.totalLevel = totalLevel;
    }

    public List<String> getLevelTitles() {
        return levelTitles;
    }

    public void setLevelTitles(List<String> levelTitles) {
        this.levelTitles = levelTitles;
    }

    public List<String> getDisplayColumns() {
        return displayColumns;
    }

    public void setDisplayColumns(List<String> displayColumns) {
        this.displayColumns = displayColumns;
    }

    public List<FinDashboardAggLineModel> getResult() {
        return result;
    }

    public void setResult(List<FinDashboardAggLineModel> result) {
        this.result = result;
    }
}
