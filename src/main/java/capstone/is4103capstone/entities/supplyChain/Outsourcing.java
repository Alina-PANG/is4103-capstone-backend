package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
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

    @OneToMany(mappedBy = "outsourcing")
    private List<OutsourcingAssessment> outsourcingAssessment;

    public Outsourcing(String outsourcingDescription, Vendor outsourcedVendor, Employee employeeInChargeOutsourcing, List<OutsourcingAssessment> outsourcingAssessment) {
        this.outsourcingDescription = outsourcingDescription;
        this.outsourcedVendor = outsourcedVendor;
        this.employeeInChargeOutsourcing = employeeInChargeOutsourcing;
        this.outsourcingAssessment = outsourcingAssessment;
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

    public List<OutsourcingAssessment> getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(List<OutsourcingAssessment> outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
    }
}