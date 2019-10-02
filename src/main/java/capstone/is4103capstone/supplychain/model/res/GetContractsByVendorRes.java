package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.VendorModel;

import java.io.Serializable;
import java.util.List;

public class GetContractsByVendorRes extends GeneralRes implements Serializable {
    public List<ContractModel> contractModelList;

    public GetContractsByVendorRes(String message, Boolean hasError, List<ContractModel> contractModelList) {
        super(message, hasError);
        this.contractModelList = contractModelList;
    }

    public GetContractsByVendorRes() {
    }

    public List<ContractModel> getContractModelList() {
        return contractModelList;
    }

    public void setContractModelList(List<ContractModel> contractModelList) {
        this.contractModelList = contractModelList;
    }
}
