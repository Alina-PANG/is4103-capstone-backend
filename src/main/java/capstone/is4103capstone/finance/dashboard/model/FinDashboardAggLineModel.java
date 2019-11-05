package capstone.is4103capstone.finance.dashboard.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class FinDashboardAggLineModel implements Serializable {

    String levelName;

    String levelCode;

    String currencyCode;

    BigDecimal totalBudget;

    BigDecimal latestReforecast;

    BigDecimal actuals;

    public FinDashboardAggLineModel() {
    }

    //中间用
    public FinDashboardAggLineModel(String levelCode, String currencyCode, BigDecimal totalBudget, BigDecimal latestReforecast) {
        this.levelCode = levelCode;
        this.currencyCode = currencyCode;
        this.totalBudget = totalBudget;
        this.latestReforecast = latestReforecast;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getLatestReforecast() {
        return latestReforecast;
    }

    public void setLatestReforecast(BigDecimal latestReforecast) {
        this.latestReforecast = latestReforecast;
    }

    public BigDecimal getActuals() {
        return actuals;
    }

    public void setActuals(BigDecimal actuals) {
        this.actuals = actuals;
    }
}
