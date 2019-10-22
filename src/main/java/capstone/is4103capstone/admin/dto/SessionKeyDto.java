package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // don't serialize nulls since there are optional classes
public class SessionKeyDto implements Serializable {

    private Optional<String> sessionKey = Optional.empty();
    private Optional<String> linkedUserUuid = Optional.empty();
    private Optional<String> linkedUserName = Optional.empty();
    private Optional<String> linkedUserFullName = Optional.empty();
    private Optional<String> linkedUserSid = Optional.empty();
    private Optional<WebAppPermissionMapDto> webAppPermissions = Optional.empty();
}
