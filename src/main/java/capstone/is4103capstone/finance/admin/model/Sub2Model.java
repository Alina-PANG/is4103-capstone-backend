package capstone.is4103capstone.finance.admin.model;

import java.util.ArrayList;
import java.util.List;

public class Sub2Model {
    String name;
    String code;
    List<MerchandiseModel> merchandiselList = new ArrayList<>();

    public Sub2Model(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Sub2Model() {
    }
}
