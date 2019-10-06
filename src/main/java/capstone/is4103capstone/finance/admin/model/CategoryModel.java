package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel implements Serializable {
    private String name;
    private String code;
    private String countryCode;
    List<Sub1Model> sub1List = new ArrayList<>();

    public CategoryModel() {
    }

    public CategoryModel(String name, String code, String countryCode) {
        this.name = name;
        this.code = code;
        this.countryCode = countryCode;
    }

    public CategoryModel(String name, String code, String countryCode, List<Sub1Model> sub1List) {
        this.name = name;
        this.code = code;
        this.countryCode = countryCode;
        this.sub1List = sub1List;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Sub1Model> getSub1List() {
        return sub1List;
    }

    public void setSub1List(List<Sub1Model> sub1List) {
        this.sub1List = sub1List;
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
