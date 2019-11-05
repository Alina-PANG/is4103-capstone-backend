package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.GeneralContractModel;

import java.io.Serializable;
import java.util.List;

public class GetGeneralContractsRes extends GeneralRes implements Serializable {
    public List<GeneralContractModel> contractModelList;

    public GetGeneralContractsRes() {
    }

    public GetGeneralContractsRes(String message, Boolean hasError, List<GeneralContractModel> contractModelList) {
        super(message, hasError);
        this.contractModelList = contractModelList;
    }

    public List<GeneralContractModel> getContractModelList() {
        return contractModelList;
    }

    public void setContractModelList(List<GeneralContractModel> contractModelList) {
        this.contractModelList = contractModelList;
    }
}
