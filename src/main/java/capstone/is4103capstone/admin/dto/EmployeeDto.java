package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class EmployeeDto implements Serializable {

    private Optional<String> id = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> userName = Optional.empty();
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> middleName = Optional.empty();
    private Optional<String> securityId = Optional.empty();
    private Optional<String> password = Optional.empty();

}
