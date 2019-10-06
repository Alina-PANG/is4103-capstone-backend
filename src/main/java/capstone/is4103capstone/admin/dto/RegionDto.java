package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class RegionDto implements Serializable {

    public Optional<String> id = Optional.empty();
    public Optional<String> objectName = Optional.empty();
    public Optional<String> code = Optional.empty();
    public Optional<List<String>> countryIds = Optional.empty();

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

    public Optional<List<String>> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(Optional<List<String>> countryIds) {
        this.countryIds = countryIds;
    }
}
