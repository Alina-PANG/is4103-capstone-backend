package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team extends DBEntityTemplate {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id")
    @JsonIgnore
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    @JsonIgnore
    private Office office;

    @OneToOne(mappedBy = "team",optional = true)
    @JsonIgnore
    private CostCenter costCenter;

    @OneToMany(mappedBy = "team")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Employee> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_leader_id")
    @JsonIgnore
    private Employee teamLeader;

    public Team() {
    }

    public Team(String teamName, String teamCode, String hierarchyPath) {
        super(teamName, teamCode, hierarchyPath);
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Employee getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(Employee teamLeader) {
        this.teamLeader = teamLeader;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public BusinessUnit getBusinessUnit() { return businessUnit; }

    public void setBusinessUnit(BusinessUnit businessUnit) { this.businessUnit = businessUnit; }

    public Office getOffice() { return office; }

    public void setOffice(Office office) { this.office = office; }

    public List<Employee> getMembers() {
        return members;
    }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }
}
