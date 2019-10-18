package capstone.is4103capstone.supplychain.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class ContractServiceModel implements Serializable {
    private String vendorName;
    private String vendorId;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    public ContractServiceModel() {
    }

    public ContractServiceModel(String vendorName, String vendorId, Date startDate, Date endDate) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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
