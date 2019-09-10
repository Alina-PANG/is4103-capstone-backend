package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.nonentity.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Office extends DBEntityTemplate {
    private Address address;

    private Integer numOfFloors;

    @ManyToOne
    private Country country;

    @ManyToMany
    private List<Function> functions;
}
