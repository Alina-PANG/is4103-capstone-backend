package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.OutsourcingModel;

import java.io.Serializable;
import java.util.List;

public class GetOutsourcingsRes extends GeneralRes implements Serializable {
    private List<OutsourcingModel> outsourcingModelList;

    public GetOutsourcingsRes() {
    }

    public GetOutsourcingsRes(String message, Boolean hasError, List<OutsourcingModel> outsourcingModelList) {
        super(message, hasError);
        this.outsourcingModelList = outsourcingModelList;
    }

    public List<OutsourcingModel> getOutsourcingModelList() {
        return outsourcingModelList;
    }

    public void setOutsourcingModelList(List<OutsourcingModel> outsourcingModelList) {
        this.outsourcingModelList = outsourcingModelList;
    }
}
