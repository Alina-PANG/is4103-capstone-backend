package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class GetAllContractsRes extends GeneralRes implements Serializable{
    public List<Contract> contractList;

    public GetAllContractsRes() {
    }

    public GetAllContractsRes(String message, Boolean hasError, List<Contract> contractList) {
        super(message, hasError);
        this.contractList = contractList;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
