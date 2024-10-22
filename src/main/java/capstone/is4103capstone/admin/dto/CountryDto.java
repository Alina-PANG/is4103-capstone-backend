package capstone.is4103capstone.admin.dto;

import capstone.is4103capstone.general.model.GeneralEntityModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class CountryDto implements Serializable {

    private Optional<String> id = Optional.empty();
    private Optional<String> objectName = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> regionId = Optional.empty();
    private Optional<List<GeneralEntityModel>> functions = Optional.empty();

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

    public Optional<String> getRegionId() {
        return regionId;
    }

    public void setRegionId(Optional<String> regionId) {
        this.regionId = regionId;
    }

    public Optional<List<GeneralEntityModel>> getFunctions() {
        return functions;
    }

    public void setFunctions(Optional<List<GeneralEntityModel>> functions) {
        this.functions = functions;
    }
}
