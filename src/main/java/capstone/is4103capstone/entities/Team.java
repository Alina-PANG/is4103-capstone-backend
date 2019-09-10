package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class Team extends DBEntityTemplate {

    @ManyToOne
    private Function function;
    @ManyToMany
    private List<Employee> members;
}
