package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Outsourcing extends DBEntityTemplate {
    private String outsourcingDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_vendor")
    @JsonIgnore
    private Vendor outsourcedVendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_inCharge_outsourcing")
    @JsonIgnore
    private Employee employeeInChargeOutsourcing;

    @OneToMany(mappedBy = "outsourcing",fetch = FetchType.EAGER)
    private List<OutsourcingAssessment> outsourcingAssessmentList = new ArrayList<>();

    public Outsourcing(String outsourcingDescription, Vendor outsourcedVendor, Employee employeeInChargeOutsourcing, List<OutsourcingAssessment> outsourcingAssessmentList) {
        this.outsourcingDescription = outsourcingDescription;
        this.outsourcedVendor = outsourcedVendor;
        this.employeeInChargeOutsourcing = employeeInChargeOutsourcing;
        this.outsourcingAssessmentList = outsourcingAssessmentList;
    }

    public Outsourcing() {
    }

    public String getOutsourcingDescription() {
        return outsourcingDescription;
    }

    public void setOutsourcingDescription(String outsourcingDescription) {
        this.outsourcingDescription = outsourcingDescription;
    }

    public Vendor getOutsourcedVendor() {
        return outsourcedVendor;
    }

    public void setOutsourcedVendor(Vendor outsourcedVendor) {
        this.outsourcedVendor = outsourcedVendor;
    }

    public Employee getEmployeeInChargeOutsourcing() {
        return employeeInChargeOutsourcing;
    }

    public void setEmployeeInChargeOutsourcing(Employee employeeInChargeOutsourcing) {
        this.employeeInChargeOutsourcing = employeeInChargeOutsourcing;
    }

    public List<OutsourcingAssessment> getOutsourcingAssessmentList() {
        return outsourcingAssessmentList;
    }

    public void setOutsourcingAssessmentList(List<OutsourcingAssessment> outsourcingAssessmentList) {
        this.outsourcingAssessmentList = outsourcingAssessmentList;
    }
}