package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class TeamDto implements Serializable {

    private Optional<String> id = Optional.empty();
    private Optional<String> objectName = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> companyFunction = Optional.empty();
    private Optional<String> country = Optional.empty();

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Optional<String> getObjectName() {
        return objectName;
    }

    public void setObjectName(Optional<String> objectName) {
        this.objectName = objectName;
    }

    public Optional<String> getCode() {
        return code;
    }

    public void setCode(Optional<String> code) {
        this.code = code;
    }

    public Optional<String> getCompanyFunction() {
        return companyFunction;
    }

    public void setCompanyFunction(Optional<String> companyFunction) {
        this.companyFunction = companyFunction;
    }

    public Optional<String> getCountry() {
        return country;
    }

    public void setCountry(Optional<String> country) {
        this.country = country;
    }
}
