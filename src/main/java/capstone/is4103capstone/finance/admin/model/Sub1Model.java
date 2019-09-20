package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sub1Model implements Serializable {
    private String name;
    private String code;
    List<Sub2Model> sub2List = new ArrayList<>();

    public Sub1Model(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Sub1Model(String name, String code, List<Sub2Model> sub2List) {
        this.name = name;
        this.code = code;
        this.sub2List = sub2List;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
