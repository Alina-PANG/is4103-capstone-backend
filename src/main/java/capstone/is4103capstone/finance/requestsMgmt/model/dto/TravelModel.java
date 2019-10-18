package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.entities.finance.TravelForm;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.Tools;

public class TravelModel extends RequestFormModel {
    private String destCity;
    private String destCountry;
    private String startDate;
    private String travelType;
    private String endDate;

    public TravelModel() {
    }

    public TravelModel(TravelForm e, boolean isSimpleVersion){
        setRequester(new EmployeeModel(e.getRequester()));
        setStartDate(Tools.dateFormatter.format(e.getStartDate()));
        setEndDate(Tools.dateFormatter.format(e.getEndDate()));

        setDestCity(e.getDestCity());
        setDestCountry(e.getDestCountry());

        setApprovalStatus(e.getApprovalStatus().name());
        setId(e.getId());
        setTravelType(e.getTravelType().name());
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
    }

    public TravelModel(TravelForm e) {
        super(e);
        setDestCity(e.getDestCity());
        setDestCountry(e.getDestCountry());
        setEndDate(Tools.dateFormatter.format(e.getEndDate()));
        setStartDate(Tools.dateFormatter.format(e.getStartDate()));
        setTravelType(e.getTravelType().name());
    }

    public TravelModel(String destCity, String destCountry, String startDate, String travelType, String endDate) {
        this.destCity = destCity;
        this.destCountry = destCountry;
        this.startDate = startDate;
        this.travelType = travelType;
        this.endDate = endDate;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
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
}
