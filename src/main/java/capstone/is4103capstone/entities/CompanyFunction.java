package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CompanyFunction extends DBEntityTemplate {

    @ManyToMany(mappedBy = "functions")
    private List<Country> countries;

    @OneToMany(mappedBy = "function")
    private List<Team> teams;

    @Convert(converter = StringListConverter.class)
    private List<String> officesCodeOfFunction;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<String> getOfficesCodeOfFunction() {
        return officesCodeOfFunction;
    }

    public void setOfficesCodeOfFunction(List<String> officesCodeOfFunction) {
        this.officesCodeOfFunction = officesCodeOfFunction;
    }
}
