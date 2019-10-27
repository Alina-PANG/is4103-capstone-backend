package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ChildContractModel;

import java.io.Serializable;

public class GetChildContractRes extends GeneralRes implements Serializable {
    private ChildContractModel childContractModel;

    public GetChildContractRes(ChildContractModel childContractModel) {
        this.childContractModel = childContractModel;
    }

    public GetChildContractRes(String message, Boolean hasError, ChildContractModel childContractModel) {
        super(message, hasError);
        this.childContractModel = childContractModel;
    }

    public GetChildContractRes() {
    }

    public ChildContractModel getChildContractModel() {
        return childContractModel;
    }

    public void setChildContractModel(ChildContractModel childContractModel) {
        this.childContractModel = childContractModel;
    }
}
