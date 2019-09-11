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
}
