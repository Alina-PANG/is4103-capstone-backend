package capstone.is4103capstone.entities.helper;


import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class Address{

    private String addressLine1;

    private String adddressLine2;

    private String postalCode;

    private String city;

    private String countryCode;

    private String regionCode;

    public Address() {
    }

    public Address(String addressLine1, String adddressLine2, String postalCode, String city, String countryCode, String regionCode) {
        this.addressLine1 = addressLine1;
        this.adddressLine2 = adddressLine2;
        this.postalCode = postalCode;
        this.city = city;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAdddressLine2() {
        return adddressLine2;
    }

    public void setAdddressLine2(String adddressLine2) {
        this.adddressLine2 = adddressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
