package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Office extends DBEntityTemplate {
    @Embedded
    private Address address;

    private Integer numOfFloors;

    @ManyToOne
    @JoinColumn(name = "country_office")
    @JsonIgnore
    private Country country;

    @Convert(converter = StringListConverter.class)
    private List<String> functionsCodeInOffice;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getNumOfFloors() {
        return numOfFloors;
    }

    public void setNumOfFloors(Integer numOfFloors) {
        this.numOfFloors = numOfFloors;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<String> getFunctionsCodeInOffice() {
        return functionsCodeInOffice;
    }

    public void setFunctionsCodeInOffice(List<String> functionsCodeInOffice) {
        this.functionsCodeInOffice = functionsCodeInOffice;
    }
}