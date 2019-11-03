package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActualsTable extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costCenter_id")
    @JsonIgnore
    private CostCenter costCenter;

    private Integer forYear;

//    @OneToMany(mappedBy = "actuals")
//    private List<SpendingRecord> spendings = new ArrayList<>();


    public ActualsTable() {
    }

    public ActualsTable(CostCenter costCenter, Integer forYear) {
        this.costCenter = costCenter;
        this.forYear = forYear;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getForYear() {
        return forYear;
    }

    public void setForYear(Integer forYear) {
        this.forYear = forYear;
    }

//    public List<SpendingRecord> getSpendings() {
//        return spendings;
//    }
//
//    public void setSpendings(List<SpendingRecord> spendings) {
//        this.spendings = spendings;
//    }


}
