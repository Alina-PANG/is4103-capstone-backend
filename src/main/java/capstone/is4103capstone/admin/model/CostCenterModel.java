package capstone.is4103capstone.admin.model;

import java.io.Serializable;

public class CostCenterModel implements Serializable {

    private String name;
    private String code;
    private String id;
    private String countryCode;
    private String managerId;


    public CostCenterModel() {
    }

    public CostCenterModel(String ccName, String code, String costCenterId, String countryCode, String ccManagerId) {
        this.name = ccName;
        this.code = code;
        this.countryCode = countryCode;
        this.id = costCenterId;
        this.managerId = ccManagerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
