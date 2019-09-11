package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
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
    @JoinColumn(name = "employee_inCharge")
    @JsonIgnore
    private Employee employeeInCharge;

    @OneToMany(mappedBy = "outsourcing")
    private List<OutsourcingAssessment> outsourcingAssessment;

    public Outsourcing(String outsourcingDescription, Vendor outsourcedVendor, Employee employeeInCharge, List<OutsourcingAssessment> outsourcingAssessment) {
        this.outsourcingDescription = outsourcingDescription;
        this.outsourcedVendor = outsourcedVendor;
        this.employeeInCharge = employeeInCharge;
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

    public Employee getEmployeeInCharge() {
        return employeeInCharge;
    }

    public void setEmployeeInCharge(Employee employeeInCharge) {
        this.employeeInCharge = employeeInCharge;
    }

    public List<OutsourcingAssessment> getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(List<OutsourcingAssessment> outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
    }
}