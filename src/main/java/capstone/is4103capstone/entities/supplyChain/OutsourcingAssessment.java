package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OutsourcingAssessment extends DBEntityTemplate {
    @OneToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "outsourcing_id")
    @JsonIgnore
    private Outsourcing outsourcing;

    private String businessCaseDescription;

    @OneToMany(mappedBy = "outsourcingAssessment",fetch = FetchType.LAZY)
    private List<OutsourcingAssessmentSection> sectionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_assess")
    @JsonIgnore
    private Employee employeeAssess;

    @OneToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "related_bjf_id")
    @JsonIgnore
    private BJF related_BJF;

    private OutsourcingAssessmentStatusEnum outsourcingAssessmentStatus;

    public OutsourcingAssessment() {
    }

    public OutsourcingAssessment(String businessCaseDescription, List<OutsourcingAssessmentSection> sectionList, Employee employeeAssess, OutsourcingAssessmentStatusEnum outsourcingAssessmentStatus) {
        this.businessCaseDescription = businessCaseDescription;
        this.sectionList = sectionList;
        this.employeeAssess = employeeAssess;
        this.outsourcingAssessmentStatus = outsourcingAssessmentStatus;
    }

    public BJF getRelated_BJF() {
        return related_BJF;
    }

    public void setRelated_BJF(BJF related_BJF) {
        this.related_BJF = related_BJF;
    }

    public Outsourcing getOutsourcing() {
        return outsourcing;
    }

    public void setOutsourcing(Outsourcing outsourcing) {
        this.outsourcing = outsourcing;
    }

    public List<OutsourcingAssessmentSection> getSectionList() {
        Collections.sort(this.sectionList, (a, b) -> a.getNumber() - b.getNumber());
        return this.sectionList;
    }

    public void setSectionList(List<OutsourcingAssessmentSection> sectionList) {
        this.sectionList = sectionList;
    }

    public Employee getEmployeeAssess() {
        return employeeAssess;
    }

    public void setEmployeeAssess(Employee employeeAssess) {
        this.employeeAssess = employeeAssess;
    }

    public OutsourcingAssessmentStatusEnum getOutsourcingAssessmentStatus() {
        return outsourcingAssessmentStatus;
    }

    public void setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum outsourcingAssessmentStatus) {
        this.outsourcingAssessmentStatus = outsourcingAssessmentStatus;
    }

    public String getBusinessCaseDescription() {
        return businessCaseDescription;
    }

    public void setBusinessCaseDescription(String businessCaseDescription) {
        this.businessCaseDescription = businessCaseDescription;
    }
}