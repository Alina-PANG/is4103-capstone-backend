package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class TravelForm extends RequestFormTemplate {

    private String destCity;
    private String destCountry;

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public TravelForm() {
    }

    public TravelForm(String destCity, String destCountry, Date startDate, Date endDate) {
        this.destCity = destCity;
        this.destCountry = destCountry;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDestCountry() {
        return destCountry;
    }

    public void setDestCountry(String destCountry) {
        this.destCountry = destCountry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
