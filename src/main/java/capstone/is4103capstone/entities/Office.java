package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Office extends DBEntityTemplate {
    @Embedded
    private Address address;

    private Integer numOfFloors;

    @ElementCollection(targetClass= String.class)
    private List<String> floors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @OneToMany(mappedBy = "office")
    private List<Team> teams = new ArrayList<>();

    @ElementCollection(targetClass= String.class)
    private List<String> functionsCodeInOffice = new ArrayList<>();

    @ElementCollection(targetClass= String.class)
    private List<String> businessUnitsCodeInOffice = new ArrayList<>();

    public Office() {
    }

    public Office(String officeName, String officeCode, String hierachyPath) {
        super(officeName, officeCode, hierachyPath);
    }

    public Office(String officeName, String officeCode, String hierachyPath, Address address, Integer numOfFloors) {
        super(officeName, officeCode, hierachyPath);
        this.address = address;
        this.numOfFloors = numOfFloors;
    }

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

    public List<Team> getTeams() { return teams; }

    public void setTeams(List<Team> teams) { this.teams = teams; }

    public List<String> getFunctionsCodeInOffice() {
        return functionsCodeInOffice;
    }

    public void setFunctionsCodeInOffice(List<String> functionsCodeInOffice) {
        this.functionsCodeInOffice = functionsCodeInOffice;
    }

    public List<String> getBusinessUnitsCodeInOffice() {
        return businessUnitsCodeInOffice;
    }

    public void setBusinessUnitsCodeInOffice(List<String> businessUnitsCodeInOffice) {
        this.businessUnitsCodeInOffice = businessUnitsCodeInOffice;
    }

    public List<String> getFloors() {
        return floors;
    }

    public void setFloors(List<String> floors) {
        this.floors = floors;
    }
}