package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;

public class Sub1Model implements Serializable {
    private String name;
    private String code;

    public Sub1Model(String name, String code) {
        this.name = name;
        this.code = code;
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
