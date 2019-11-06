package capstone.is4103capstone.finance.dashboard.model.res;

import capstone.is4103capstone.finance.dashboard.model.VendorContractAggreLineModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.List;


public class VendorContractAggregationRes extends GeneralRes {

    Integer topLevelLinesCnt;

    Integer year;

    List<VendorContractAggreLineModel> result;

    public VendorContractAggregationRes() {
    }

    public VendorContractAggregationRes(String message, Boolean hasError, Integer topLevelLinesCnt, Integer year, List<VendorContractAggreLineModel> result) {
        super(message, hasError);
        this.topLevelLinesCnt = topLevelLinesCnt;
        this.year = year;
        this.result = result;
    }

    public Integer getTopLevelLinesCnt() {
        return topLevelLinesCnt;
    }

    public void setTopLevelLinesCnt(Integer topLevelLinesCnt) {
        this.topLevelLinesCnt = topLevelLinesCnt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<VendorContractAggreLineModel> getResult() {
        return result;
    }

    public void setResult(List<VendorContractAggreLineModel> result) {
        this.result = result;
    }
}
