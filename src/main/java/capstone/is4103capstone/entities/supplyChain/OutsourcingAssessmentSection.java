package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OutsourcingAssessmentSection extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_assessment")
    @JsonIgnore
    private OutsourcingAssessment outsourcingAssessment;

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @OneToMany(mappedBy = "outsourcingAssessmentSection",fetch = FetchType.LAZY)
    private List<OutsourcingAssessmentLine> outsourcingAssessmentLines = new ArrayList<>();

    public OutsourcingAssessmentSection() {
    }

    public OutsourcingAssessmentSection(List<OutsourcingAssessmentLine> outsourcingAssessmentLines) {
        this.outsourcingAssessmentLines = outsourcingAssessmentLines;
    }

    public OutsourcingAssessment getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(OutsourcingAssessment outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
    }

    public List<OutsourcingAssessmentLine> getOutsourcingAssessmentLines() {
        Collections.sort(this.outsourcingAssessmentLines, (a, b) -> a.getNumber() - b.getNumber());
        return this.outsourcingAssessmentLines;
    }

    public void setOutsourcingAssessmentLines(List<OutsourcingAssessmentLine> outsourcingAssessmentLines) {
        this.outsourcingAssessmentLines = outsourcingAssessmentLines;
    }
}



