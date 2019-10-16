package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
