package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Country extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "region_country",nullable = false)
    @JsonIgnore
    private Region region;

    @ManyToMany
    @JoinTable(name = "country_function",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private List<CompanyFunction> functions;

    @OneToMany(mappedBy = "country")
    private List<Office> offices;

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
