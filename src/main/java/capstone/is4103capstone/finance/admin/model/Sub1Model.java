package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sub1Model implements Serializable {
    private String name;
    private String code;
    private String categoryBelongsTo;
    List<Sub2Model> sub2List = new ArrayList<>();

    public Sub1Model() {
    }

    public Sub1Model(String name, String code, String categoryBelongsTo) {
        this.name = name;
        this.code = code;
        this.categoryBelongsTo = categoryBelongsTo;
    }

    public Sub1Model(String name, String code, String categoryBelongsTo, List<Sub2Model> sub2List) {
        this.name = name;
        this.code = code;
        this.categoryBelongsTo = categoryBelongsTo;
        this.sub2List = sub2List;
    }

    public String getCategoryBelongsTo() {
        return categoryBelongsTo;
    }

    public void setCategoryBelongsTo(String categoryBelongsTo) {
        this.categoryBelongsTo = categoryBelongsTo;
    }

    public List<Sub2Model> getSub2List() {
        return sub2List;
    }

    public void setSub2List(List<Sub2Model> sub2List) {
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
