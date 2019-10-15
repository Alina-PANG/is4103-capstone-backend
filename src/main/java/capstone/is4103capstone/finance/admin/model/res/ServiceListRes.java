package capstone.is4103capstone.finance.admin.model.res;

import capstone.is4103capstone.finance.admin.model.ServiceModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class ServiceListRes extends GeneralRes {
    List<ServiceModel> items = new ArrayList<>();
    int totalCount;

    public ServiceListRes(String message, Boolean hasError, List<ServiceModel> items, int totalCount) {
        super(message, hasError);
        this.items = items;
        this.totalCount = totalCount;
    }

    public ServiceListRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public ServiceListRes() {
    }

    public List<ServiceModel> getItems() {
        return items;
    }

    public void setItems(List<ServiceModel> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
