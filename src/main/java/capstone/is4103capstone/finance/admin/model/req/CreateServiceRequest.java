package capstone.is4103capstone.finance.admin.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateServiceRequest implements Serializable {

    private String itemName;
    private String sub2Code;
    private String measureUnit;
    private BigDecimal referencePrice;
    private String currency;

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String username;

    public CreateServiceRequest() {
    }

    public CreateServiceRequest(String itemName, String sub2Code, String measureUnit, BigDecimal referencePrice, String currency, String username) {
        this.itemName = itemName;
        this.sub2Code = sub2Code;
        this.measureUnit = measureUnit;
        this.referencePrice = referencePrice;
        this.currency = currency;
        this.username = username;
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
