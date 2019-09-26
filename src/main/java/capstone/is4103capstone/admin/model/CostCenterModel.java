package capstone.is4103capstone.admin.model;

import java.io.Serializable;

public class CostCenterModel implements Serializable {

    private String ccName;
    private String ccCode;
    private String countryCode;
    private String ccId;
    private String ccManagerId;


    public CostCenterModel() {
    }

    public CostCenterModel(String ccName, String ccCode, String countryCode, String ccId, String ccManagerId) {
        this.ccName = ccName;
        this.ccCode = ccCode;
        this.countryCode = countryCode;
        this.ccId = ccId;
        this.ccManagerId = ccManagerId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcCode() {
        return ccCode;
    }

    public void setCcCode(String ccCode) {
        this.ccCode = ccCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getCcManagerId() {
        return ccManagerId;
    }

    public void setCcManagerId(String ccManagerId) {
        this.ccManagerId = ccManagerId;
    }
}
