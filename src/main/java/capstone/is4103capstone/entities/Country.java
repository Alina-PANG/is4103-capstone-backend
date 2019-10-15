package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    private List<CompanyFunction> functions = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<Office> offices = new ArrayList<>();

    private String currencyCode;


    public Country() {
    }

    public Country(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy, Region region, List<CompanyFunction> functions, List<Office> offices, String currencyCode) {
        super(objectName, code, hierachyPath, createdBy, lastModifiedBy);
        this.region = region;
        this.functions = functions;
        this.offices = offices;
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Country(String countryName, String countryCode, String hierachyPath) {
        super(countryName, countryCode, hierachyPath);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<CompanyFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<CompanyFunction> functions) {
        this.functions = functions;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
}
