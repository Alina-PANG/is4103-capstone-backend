package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusinessUnit extends DBEntityTemplate {

    // Department -> Business Unit -> Team

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id")
    @JsonIgnore
    private CompanyFunction function;

    @OneToMany(mappedBy = "businessUnit")
    private List<Team> teams = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<String> officesCodeOfBusinessUnit = new ArrayList<>();

    public BusinessUnit() {
    }

    public BusinessUnit(String businessUnitName, String businessUnitCode, String hierarchyPath) {
        super(businessUnitName, businessUnitCode, hierarchyPath);
    }

    public CompanyFunction getFunction() {
        return function;
    }

    public void setFunction(CompanyFunction function) {
        this.function = function;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<String> getOfficesCodeOfBusinessUnit() {
        return officesCodeOfBusinessUnit;
    }

    public void setOfficesCodeOfBusinessUnit(List<String> officesCodeOfBusinessUnit) {
        this.officesCodeOfBusinessUnit = officesCodeOfBusinessUnit;
    }
}
