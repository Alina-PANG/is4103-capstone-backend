package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Team extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "function_team",nullable = false)
    @JsonIgnore
    private CompanyFunction function;

    @ManyToMany
    @JoinTable(name = "team_employee",
    joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> members;
}
