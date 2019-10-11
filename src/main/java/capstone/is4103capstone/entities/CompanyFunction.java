package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompanyFunction extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @Convert(converter = StringListConverter.class)
    private List<String> officesCodeOfFunction = new ArrayList<>();

    public CompanyFunction() {
    }

    public CompanyFunction(String functionName, String functionCode, String hierarchyPath) {
        super(functionName, functionCode, hierarchyPath);
    }

    public Country getCountry() { return country; }

    public void setCountry(Country country) { this.country = country; }

    public List<String> getOfficesCodeOfFunction() {
        return officesCodeOfFunction;
    }

    public void setOfficesCodeOfFunction(List<String> officesCodeOfFunction) {
        this.officesCodeOfFunction = officesCodeOfFunction;
    }
}
