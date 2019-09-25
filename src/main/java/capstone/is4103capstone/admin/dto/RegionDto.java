package capstone.is4103capstone.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionDto {

    public String id;
    public String objectName;
    public String code;
    public List<String> countryIds;

}
