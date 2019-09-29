package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class OfficeDto {

    private Optional<String> id = Optional.empty();
    private Optional<String> objectName = Optional.empty();
    private Optional<String> code = Optional.empty();
    private Optional<String> addressLine1 = Optional.empty();
    private Optional<String> addressLine2 = Optional.empty();
    private Optional<String> postalCode = Optional.empty();
    private Optional<String> city = Optional.empty();
    private Optional<String> countryCode = Optional.empty();
    private Optional<String> countryId = Optional.empty();
    private Optional<List<String>> floors = Optional.empty();
}
