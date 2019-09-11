package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Region extends DBEntityTemplate {

    @OneToMany(mappedBy = "region")
    private List<Country> countries = new ArrayList<>();

    public Region() {
    }

    public Region(String regionName, String regionCode, String hierachyPath) {
        super(regionName, regionCode, hierachyPath);
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
