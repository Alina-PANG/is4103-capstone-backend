package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class EmployeeDto {

    private Optional<String> id = Optional.empty();
    private String code;
    private String userName;
    private String firstName;
    private String lastName;
    private Optional<String> middleName = Optional.empty();
    private Optional<String> securityId;

}
