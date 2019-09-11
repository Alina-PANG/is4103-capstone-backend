package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Office extends DBEntityTemplate {
    @OneToOne
    @JoinColumn(name = "office_address")
    private Address address;

    private Integer numOfFloors;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_office",nullable = false)
    @JsonIgnore
    private Country country;

//    @ManyToMany//do we need this?
//    private List<CompanyFunction> companyFunctions;

}
