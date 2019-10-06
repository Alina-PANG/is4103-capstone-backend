package capstone.is4103capstone.admin.model.res;

import capstone.is4103capstone.admin.model.CostCenterModel;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.general.model.GeneralRes;

public class GetSingleCostCenterRes extends GeneralRes {
    CostCenterModel costCenter;

    public GetSingleCostCenterRes() {
    }

    public GetSingleCostCenterRes(CostCenterModel costCenter) {
        this.costCenter = costCenter;
    }

    public GetSingleCostCenterRes(String message, Boolean hasError, CostCenterModel costCenter) {
        super(message, hasError);
        this.costCenter = costCenter;
    }

    public CostCenterModel getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenterModel costCenter) {
        this.costCenter = costCenter;
    }
}
