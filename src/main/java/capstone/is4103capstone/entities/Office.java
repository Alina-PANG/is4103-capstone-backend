package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.ss.formula.functions.Count;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Office extends DBEntityTemplate {
    @Embedded
    private Address address;

    private Integer numOfFloors;

    @ManyToOne
    @JoinColumn(name = "country_office")
    @JsonIgnore
    private Country country;

    @Convert(converter = StringListConverter.class)
    private List<String> functionsCodeInOffice;

}