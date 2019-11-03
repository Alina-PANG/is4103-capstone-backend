package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.ContractDistributionModel;

import java.io.Serializable;

public class GetContractDistributionRes extends GeneralRes implements Serializable {
    private ContractDistributionModel contractDistributionModel;

    public GetContractDistributionRes() {
    }

    public GetContractDistributionRes(String message, Boolean hasError, ContractDistributionModel contractDistributionModel) {
        super(message, hasError);
        this.contractDistributionModel = contractDistributionModel;
    }

    public ContractDistributionModel getContractDistributionModel() {
        return contractDistributionModel;
    }

    public void setContractDistributionModel(ContractDistributionModel contractDistributionModel) {
        this.contractDistributionModel = contractDistributionModel;
    }
}
