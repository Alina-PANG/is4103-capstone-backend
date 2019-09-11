package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CostCenter extends DBEntityTemplate {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "costcenter-employees")
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter-manager")
    @JsonIgnore
    private Employee costCenterManager;
}
