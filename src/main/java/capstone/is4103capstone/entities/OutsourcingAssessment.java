package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.enums.OutsourcingAssessmentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class OutsourcingAssessment extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing")
    @JsonIgnore
    private Outsourcing outsourcing;

    @OneToMany(mappedBy = "outsourcingAssessment")
    private List<OutsourcingAssessmentSection> sectionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_assess")
    @JsonIgnore
    private Employee employeeAssess;

    private OutsourcingAssessmentStatusEnum outsourcingAssessmentStatus;

    public OutsourcingAssessment(List<OutsourcingAssessmentSection> sectionList, Employee employeeAssess, OutsourcingAssessmentStatusEnum outsourcingAssessmentStatus) {
        this.sectionList = sectionList;
        this.employeeAssess = employeeAssess;
        this.outsourcingAssessmentStatus = outsourcingAssessmentStatus;
    }

    public Outsourcing getOutsourcing() {
        return outsourcing;
    }

    public void setOutsourcing(Outsourcing outsourcing) {
        this.outsourcing = outsourcing;
    }

    public List<OutsourcingAssessmentSection> getSectionList() {
        return sectionList;
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
}