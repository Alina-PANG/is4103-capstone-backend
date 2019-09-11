package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "costcenter")
public class CostCenter extends DBEntityTemplate {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter_employees")//uni-directional
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter_manager")
    @JsonIgnore
    private Employee costCenterManager;
}
