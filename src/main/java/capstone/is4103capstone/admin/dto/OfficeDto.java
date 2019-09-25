package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class OfficeDto {

    private Optional<String> id = Optional.empty();
    private String objectName;
    private String code;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String city;
    private String countryCode;
    private String countryId;
    private List<String> floors;
}
