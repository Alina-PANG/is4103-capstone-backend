package capstone.is4103capstone.finance.admin.model.req;

import java.io.Serializable;

public class FXRecordQueryReq implements Serializable {
    String startDate;
    String endDate;
    String baseCurr;
    String priceCurr;
    String username;

    public FXRecordQueryReq(String startDate, String endDate, String baseCurr, String priceCurr, String username) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseCurr = baseCurr;
        this.priceCurr = priceCurr;
        this.username = username;
    }

    public FXRecordQueryReq() {
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

    public String getBaseCurr() {
        return baseCurr;
    }

    public void setBaseCurr(String baseCurr) {
        this.baseCurr = baseCurr;
    }

    public String getPriceCurr() {
        return priceCurr;
    }

    public void setPriceCurr(String priceCurr) {
        this.priceCurr = priceCurr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
