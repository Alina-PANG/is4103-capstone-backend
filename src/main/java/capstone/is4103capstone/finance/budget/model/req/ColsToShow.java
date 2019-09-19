package capstone.is4103capstone.finance.budget.model.req;

import java.io.Serializable;

public class ColsToShow implements Serializable {


    boolean country;
    boolean region;
    boolean year;
    boolean status;
    boolean createdBy;
    boolean modifiedBy;
    boolean createdDateTime;
    boolean modifiedDateTime;
    // currencyAbbr, amount: will be shown for sure

    public ColsToShow() {
    }

    public ColsToShow(boolean country, boolean region, boolean year, boolean status, boolean createdBy, boolean modifiedBy, boolean createdDateTime, boolean modifiedDateTime) {
        this.country = country;
        this.region = region;
        this.year = year;
        this.status = status;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public boolean isCountry() {
        return country;
    }

    public void setCountry(boolean country) {
        this.country = country;
    }

    public boolean isRegion() {
        return region;
    }

    public void setRegion(boolean region) {
        this.region = region;
    }

    public boolean isYear() {
        return year;
    }

    public void setYear(boolean year) {
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(boolean createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(boolean modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(boolean createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(boolean modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
}
