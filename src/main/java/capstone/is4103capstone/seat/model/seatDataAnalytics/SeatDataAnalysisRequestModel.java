package capstone.is4103capstone.seat.model.seatDataAnalytics;

import java.io.Serializable;
import java.util.Date;

public class SeatDataAnalysisRequestModel implements Serializable {
    private String hierarchyType;
    private String entityId;
    private String officeId;
    private Date startDate;
    private Date endDate;

    public SeatDataAnalysisRequestModel(Date endDate) {
        this.endDate = endDate;
    }

    public SeatDataAnalysisRequestModel(String hierarchyType, String entityId, String officeId, Date startDate, Date endDate) {
        this.hierarchyType = hierarchyType;
        this.entityId = entityId;
        this.officeId = officeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getHierarchyType() {
        return hierarchyType;
    }

    public void setHierarchyType(String hierarchyType) {
        this.hierarchyType = hierarchyType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
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
