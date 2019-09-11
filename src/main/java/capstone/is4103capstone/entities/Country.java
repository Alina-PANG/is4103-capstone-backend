package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Country extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "region_country",nullable = false)
    @JsonIgnore
    private Region region;

    @ManyToMany
    @JoinTable(name = "country_function",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private List<CompanyFunction> functions;

    @OneToMany(mappedBy = "country")
    private List<Office> offices;
}
