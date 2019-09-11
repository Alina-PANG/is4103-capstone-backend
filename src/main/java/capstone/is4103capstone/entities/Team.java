package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Team extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id")
    @JsonIgnore
    private CompanyFunction function;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_employee",
    joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> members = new ArrayList<>();

    public Team() {
    }

    public Team(String teamName, String teamCode, String hierachyPath) {
        super(teamName, teamCode, hierachyPath);
    }

    public CompanyFunction getFunction() {
        return function;
    }

    public void setFunction(CompanyFunction function) {
        this.function = function;
    }

    public List<Employee> getMembers() {
        return members;
    }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }
}
