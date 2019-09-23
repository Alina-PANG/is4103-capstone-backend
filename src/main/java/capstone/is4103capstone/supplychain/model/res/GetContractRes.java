package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;

public class GetContractRes extends GeneralRes implements Serializable{
    private Contract contract;

    public GetContractRes() {
    }

    public GetContractRes(String message, Boolean error, Contract contract){
        super(message,error);
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}