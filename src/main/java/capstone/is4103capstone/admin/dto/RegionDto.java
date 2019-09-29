package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RegionDto {

    public Optional<String> id = Optional.empty();
    public Optional<String> objectName = Optional.empty();
    public Optional<String> code = Optional.empty();
    public Optional<List<String>> countryIds = Optional.empty();

}
