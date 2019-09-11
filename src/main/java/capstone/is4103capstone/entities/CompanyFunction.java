package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CompanyFunction extends DBEntityTemplate {

    @ManyToMany(mappedBy = "functions",fetch = FetchType.EAGER)
    private List<Country> countries = new ArrayList<>();

    @OneToMany(mappedBy = "function")
    private List<Team> teams = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<String> officesCodeOfFunction = new ArrayList<>();

    public CompanyFunction() {
    }

    public CompanyFunction(String functionName, String functionCode, String hierachyPath) {
        super(functionName, functionCode, hierachyPath);
    }

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