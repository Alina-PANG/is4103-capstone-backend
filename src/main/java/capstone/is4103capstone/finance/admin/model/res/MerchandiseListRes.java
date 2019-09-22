package capstone.is4103capstone.finance.admin.model.res;

import capstone.is4103capstone.finance.admin.model.MerchandiseModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class MerchandiseListRes extends GeneralRes {
    List<MerchandiseModel> items = new ArrayList<>();
    int totalCount;

    public MerchandiseListRes() {
    }

    public List<MerchandiseModel> getItems() {
        return items;
    }

    public void setItems(List<MerchandiseModel> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
