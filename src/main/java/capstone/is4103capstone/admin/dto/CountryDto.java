package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class CountryDto {

    private Optional<String> id = Optional.empty();
    private String objectName;
    private String code;
    private Optional<String> regionId = Optional.empty();

}
