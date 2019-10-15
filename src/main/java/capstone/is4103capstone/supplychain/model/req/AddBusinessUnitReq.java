package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class AddBusinessUnitReq implements Serializable {
    private String username;
    private String businessUnitId;

    public AddBusinessUnitReq() {
    }

    public AddBusinessUnitReq(String username, String businessUnitId) {
        this.username = username;
        this.businessUnitId = businessUnitId;
    }

    public String getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
