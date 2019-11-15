package capstone.is4103capstone.entities.helper;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class WebAppPermissionMap {

    private boolean accessAdminModule = false;
    private boolean accessSeatManagementModule = false;
    private boolean accessSupplyChainManagementModule = false;
    private boolean accessFinancialManagementModule = false;
    private boolean accessDashboardModule = false;

    public WebAppPermissionMap() {
    }

    public WebAppPermissionMap(boolean accessAdminModule, boolean accessSeatManagementModule, boolean accessSupplyChainManagementModule, boolean accessFinancialManagementModule) {
        this.accessAdminModule = accessAdminModule;
        this.accessSeatManagementModule = accessSeatManagementModule;
        this.accessSupplyChainManagementModule = accessSupplyChainManagementModule;
        this.accessFinancialManagementModule = accessFinancialManagementModule;
    }
}
