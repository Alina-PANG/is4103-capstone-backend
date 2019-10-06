package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ContractModel;

import java.io.Serializable;

public class GetContractRes extends GeneralRes implements Serializable{
    private ContractModel contractModel;

    public GetContractRes(String message, Boolean hasError, ContractModel contractModel) {
        super(message, hasError);
        this.contractModel = contractModel;
    }

    public GetContractRes() {
    }

    public ContractModel getContractModel() {
        return contractModel;
    }

    public void setContractModel(ContractModel contractModel) {
        this.contractModel = contractModel;
    }
}