package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sub2Model implements Serializable {
    private String name;
    private String code;
    private String sub1BelongsTo;
    List<MerchandiseModel> merchandiselList = new ArrayList<>();


    public Sub2Model(String name, String code, String sub1BelongsTo) {
        this.name = name;
        this.code = code;
        this.sub1BelongsTo = sub1BelongsTo;
    }

    public Sub2Model(String name, String code, String sub1BelongsTo, List<MerchandiseModel> merchandiselList) {
        this.name = name;
        this.code = code;
        this.sub1BelongsTo = sub1BelongsTo;
        this.merchandiselList = merchandiselList;
    }

    public Sub2Model() {
    }

    public String getSub1BelongsTo() {
        return sub1BelongsTo;
    }

    public void setSub1BelongsTo(String sub1BelongsTo) {
        this.sub1BelongsTo = sub1BelongsTo;
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

    public List<MerchandiseModel> getMerchandiselList() {
        return merchandiselList;
    }

    public void setMerchandiselList(List<MerchandiseModel> merchandiselList) {
        this.merchandiselList = merchandiselList;
    }
}
