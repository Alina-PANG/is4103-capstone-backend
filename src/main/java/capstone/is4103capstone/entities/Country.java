package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Country extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private Region region;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "country_function",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private List<CompanyFunction> functions = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<Office> offices = new ArrayList<>();

    public Country() {
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
