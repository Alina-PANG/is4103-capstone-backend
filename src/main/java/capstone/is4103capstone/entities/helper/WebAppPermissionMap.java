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

    public WebAppPermissionMap(boolean accessAdminModule, boolean accessSeatManagementModule, boolean accessSupplyChainManagementModule, boolean accessFinancialManagementModule, boolean accessDashboardModule) {
        this.accessAdminModule = accessAdminModule;
        this.accessSeatManagementModule = accessSeatManagementModule;
        this.accessSupplyChainManagementModule = accessSupplyChainManagementModule;
        this.accessFinancialManagementModule = accessFinancialManagementModule;
        this.accessDashboardModule = accessDashboardModule;
    }

    public boolean isAccessAdminModule() {
        return accessAdminModule;
    }

    public void setAccessAdminModule(boolean accessAdminModule) {
        this.accessAdminModule = accessAdminModule;
    }

    public boolean isAccessSeatManagementModule() {
        return accessSeatManagementModule;
    }

    public void setAccessSeatManagementModule(boolean accessSeatManagementModule) {
        this.accessSeatManagementModule = accessSeatManagementModule;
    }

    public boolean isAccessSupplyChainManagementModule() {
        return accessSupplyChainManagementModule;
    }

    public void setAccessSupplyChainManagementModule(boolean accessSupplyChainManagementModule) {
        this.accessSupplyChainManagementModule = accessSupplyChainManagementModule;
    }

    public boolean isAccessFinancialManagementModule() {
        return accessFinancialManagementModule;
    }

    public void setAccessFinancialManagementModule(boolean accessFinancialManagementModule) {
        this.accessFinancialManagementModule = accessFinancialManagementModule;
    }

    public boolean isAccessDashboardModule() {
        return accessDashboardModule;
    }

    public void setAccessDashboardModule(boolean accessDashboardModule) {
        this.accessDashboardModule = accessDashboardModule;
    }
}
