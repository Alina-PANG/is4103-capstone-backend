package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Region extends DBEntityTemplate {

    @OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
    private List<Country> countries = new ArrayList<>();

    public Region() {
    }

    public Region(String regionName, String regionCode, String hierachyPath) {
        super(regionName, regionCode, hierachyPath);
    }

    @JsonProperty(value = "countries")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
