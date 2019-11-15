package capstone.is4103capstone.admin.dto;

import capstone.is4103capstone.entities.helper.WebAppPermissionMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class WebAppPermissionMapDto implements Serializable {

    private Optional<String> userUuid = Optional.empty();
    private Optional<Boolean> accessAdminModule = Optional.empty();
    private Optional<Boolean> accessSeatManagementModule = Optional.empty();
    private Optional<Boolean> accessSupplyChainManagementModule = Optional.empty();
    private Optional<Boolean> accessFinancialManagementModule = Optional.empty();
    private Optional<Boolean> accessDashboardModule = Optional.empty();

    // to and from entity conversion
    public static WebAppPermissionMapDto fromEntity(WebAppPermissionMap input) {
        WebAppPermissionMapDto webAppPermissionMapDto = new WebAppPermissionMapDto();
        webAppPermissionMapDto.accessAdminModule = Optional.ofNullable(input.isAccessAdminModule());
        webAppPermissionMapDto.accessSeatManagementModule = Optional.ofNullable(input.isAccessSeatManagementModule());
        webAppPermissionMapDto.accessSupplyChainManagementModule = Optional.ofNullable(input.isAccessSupplyChainManagementModule());
        webAppPermissionMapDto.accessFinancialManagementModule = Optional.ofNullable(input.isAccessFinancialManagementModule());
        webAppPermissionMapDto.accessDashboardModule = Optional.ofNullable(input.isAccessDashboardModule());
        return webAppPermissionMapDto;
    }

    public static WebAppPermissionMap toEntity(WebAppPermissionMapDto input) {
        WebAppPermissionMap webAppPermissionMap = new WebAppPermissionMap();
        input.accessAdminModule.ifPresent(webAppPermissionMap::setAccessAdminModule);
        input.accessSeatManagementModule.ifPresent(webAppPermissionMap::setAccessSeatManagementModule);
        input.accessSupplyChainManagementModule.ifPresent(webAppPermissionMap::setAccessSupplyChainManagementModule);
        input.accessFinancialManagementModule.ifPresent(webAppPermissionMap::setAccessFinancialManagementModule);
        input.accessDashboardModule.ifPresent(webAppPermissionMap::setAccessDashboardModule);
        return webAppPermissionMap;
    }

    public WebAppPermissionMap thisToEntity() {
        return toEntity(this);
    }

}
