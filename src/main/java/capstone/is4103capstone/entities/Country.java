package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Country extends DBEntityTemplate {

    @ManyToOne
    private Region region;

    @ManyToMany
    private List<Function> functions;
}
