package capstone.is4103capstone.finance.admin.model.req;

import java.io.Serializable;

public class CreateMerchandiseRequest implements Serializable {

    private String vendorCode;
    private String itemName;
    private String sub2Code;
    private String measureUnit;

    private String username;

    public CreateMerchandiseRequest() {
    }

    public CreateMerchandiseRequest(String vendorCode, String itemName, String sub2Code, String measureUnit, String username) {
        this.vendorCode = vendorCode;
        this.itemName = itemName;
        this.sub2Code = sub2Code;
        this.measureUnit = measureUnit;
        this.username = username;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSub2Code() {
        return sub2Code;
    }

    public void setSub2Code(String sub2Code) {
        this.sub2Code = sub2Code;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
