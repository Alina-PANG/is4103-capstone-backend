package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table
@MappedSuperclass
public class OfficeFacility extends DBEntityTemplate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    @JsonIgnore
    @NotNull
    protected Office office;

    @Min(1)
    protected Integer floor;

    public OfficeFacility() {
    }

    public OfficeFacility(String objectName, String code, String hierachyPath) {
        super(objectName, code, hierachyPath);
    }

    public OfficeFacility(String objectName, String code, String hierachyPath, Office office, @Min(1) Integer floor) {
        super(objectName, code, hierachyPath);
        this.office = office;
        this.floor = floor;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
