package capstone.is4103capstone.admin.model.res;

import capstone.is4103capstone.admin.model.CostCenterModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class GetCostCenterListRes extends GeneralRes {
    List<CostCenterModel> costCentersList = new ArrayList<>();

    public GetCostCenterListRes() {
    }

    public GetCostCenterListRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public GetCostCenterListRes(String message, Boolean hasError, List<CostCenterModel> costCentersList) {
        super(message, hasError);
        this.costCentersList = costCentersList;
    }

    public List<CostCenterModel> getCostCentersList() {
        return costCentersList;
    }

    public void setCostCentersList(List<CostCenterModel> costCentersList) {
        this.costCentersList = costCentersList;
    }
}
