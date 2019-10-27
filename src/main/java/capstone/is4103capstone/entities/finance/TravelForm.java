package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.util.enums.TravelTrainingTypeEnum;

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

    private TravelTrainingTypeEnum travelType;

    public TravelForm() {
    }

    public TravelForm(String destCity, String destCountry, Date startDate, Date endDate, TravelTrainingTypeEnum travelType) {
        this.destCity = destCity;
        this.destCountry = destCountry;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travelType = travelType;
    }

    public TravelTrainingTypeEnum getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelTrainingTypeEnum travelType) {
        this.travelType = travelType;
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
