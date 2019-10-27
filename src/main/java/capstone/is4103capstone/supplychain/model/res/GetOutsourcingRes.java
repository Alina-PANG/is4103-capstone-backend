package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.OutsourcingModel;

import java.io.Serializable;

public class GetOutsourcingRes extends GeneralRes implements Serializable {
    private OutsourcingModel outsourcingModel;

    public GetOutsourcingRes() {
    }

    public GetOutsourcingRes(String message, Boolean hasError, OutsourcingModel outsourcingModel) {
        super(message, hasError);
        this.outsourcingModel = outsourcingModel;
    }

    public OutsourcingModel getOutsourcingModel() {
        return outsourcingModel;
    }

    public void setOutsourcingModel(OutsourcingModel outsourcingModel) {
        this.outsourcingModel = outsourcingModel;
    }
}
