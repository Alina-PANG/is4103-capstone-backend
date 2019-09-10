package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Function extends DBEntityTemplate {

    @ManyToMany
    private List<Country> countries;
    @OneToMany
    private List<Team> teams;
}
