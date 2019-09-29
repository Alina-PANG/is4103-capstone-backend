package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class EmployeeDto {

    private Optional<String> id = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> userName = Optional.empty();
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> middleName = Optional.empty();
    private Optional<String> securityId = Optional.empty();
    private Optional<String> password = Optional.empty();

}
