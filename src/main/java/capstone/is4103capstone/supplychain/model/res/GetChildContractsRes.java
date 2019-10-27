package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ChildContractModel;
import capstone.is4103capstone.supplychain.model.ContractModel;

import java.io.Serializable;
import java.util.List;

public class GetChildContractsRes extends GeneralRes implements Serializable {
    public List<ChildContractModel> childContractModelList;

    public GetChildContractsRes(String message, Boolean hasError, List<ChildContractModel> childContractModelList) {
        super(message, hasError);
        this.childContractModelList = childContractModelList;
    }

    public GetChildContractsRes() {
    }

    public List<ChildContractModel> getChildContractModelList() {
        return childContractModelList;
    }

    public void setChildContractModelList(List<ChildContractModel> childContractModelList) {
        this.childContractModelList = childContractModelList;
    }
}
