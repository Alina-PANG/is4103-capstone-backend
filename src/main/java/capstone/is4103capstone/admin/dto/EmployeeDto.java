package capstone.is4103capstone.admin.dto;

import capstone.is4103capstone.entities.helper.WebAppPermissionMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Optional;


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
    private Optional<String> email = Optional.empty();
    private Optional<WebAppPermissionMap> permissions = Optional.empty();


    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Optional<String> getCode() {
        return code;
    }

    public void setCode(Optional<String> code) {
        this.code = code;
    }

    public Optional<String> getUserName() {
        return userName;
    }

    public void setUserName(Optional<String> userName) {
        this.userName = userName;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }

    public Optional<String> getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Optional<String> middleName) {
        this.middleName = middleName;
    }

    public Optional<String> getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Optional<String> securityId) {
        this.securityId = securityId;
    }

    @JsonIgnore // so DTO to JSON does not display password: null
    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<WebAppPermissionMap> getPermissions() {
        return permissions;
    }

    public void setPermissions(Optional<WebAppPermissionMap> permissions) {
        this.permissions = permissions;
    }
}
