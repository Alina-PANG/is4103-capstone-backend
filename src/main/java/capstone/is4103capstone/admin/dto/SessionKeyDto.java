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

    public Optional<String> getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Optional<String> sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Optional<String> getLinkedUserUuid() {
        return linkedUserUuid;
    }

    public void setLinkedUserUuid(Optional<String> linkedUserUuid) {
        this.linkedUserUuid = linkedUserUuid;
    }

    public Optional<String> getLinkedUserName() {
        return linkedUserName;
    }

    public void setLinkedUserName(Optional<String> linkedUserName) {
        this.linkedUserName = linkedUserName;
    }

    public Optional<String> getLinkedUserFullName() {
        return linkedUserFullName;
    }

    public void setLinkedUserFullName(Optional<String> linkedUserFullName) {
        this.linkedUserFullName = linkedUserFullName;
    }

    public Optional<String> getLinkedUserSid() {
        return linkedUserSid;
    }

    public void setLinkedUserSid(Optional<String> linkedUserSid) {
        this.linkedUserSid = linkedUserSid;
    }

    public Optional<WebAppPermissionMapDto> getWebAppPermissions() {
        return webAppPermissions;
    }

    public void setWebAppPermissions(Optional<WebAppPermissionMapDto> webAppPermissions) {
        this.webAppPermissions = webAppPermissions;
    }
}
