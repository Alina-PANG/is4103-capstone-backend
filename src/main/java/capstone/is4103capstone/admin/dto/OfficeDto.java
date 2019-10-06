package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class OfficeDto implements Serializable {

    private Optional<String> id = Optional.empty();
    private Optional<String> objectName = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> addressLine1 = Optional.empty();
    private Optional<String> addressLine2 = Optional.empty();
    private Optional<String> postalCode = Optional.empty();
    private Optional<String> city = Optional.empty();
    private Optional<String> countryCode = Optional.empty();
    private Optional<String> countryId = Optional.empty();
    private Optional<List<String>> floors = Optional.empty();


    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Optional<String> getObjectName() {
        return objectName;
    }

    public void setObjectName(Optional<String> objectName) {
        this.objectName = objectName;
    }

    public Optional<String> getCode() {
        return code;
    }

    public void setCode(Optional<String> code) {
        this.code = code;
    }

    public Optional<String> getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(Optional<String> addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Optional<String> getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(Optional<String> addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Optional<String> getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Optional<String> postalCode) {
        this.postalCode = postalCode;
    }

    public Optional<String> getCity() {
        return city;
    }

    public void setCity(Optional<String> city) {
        this.city = city;
    }

    public Optional<String> getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Optional<String> countryCode) {
        this.countryCode = countryCode;
    }

    public Optional<String> getCountryId() {
        return countryId;
    }

    public void setCountryId(Optional<String> countryId) {
        this.countryId = countryId;
    }

    public Optional<List<String>> getFloors() {
        return floors;
    }

    public void setFloors(Optional<List<String>> floors) {
        this.floors = floors;
    }
}
