package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.ws.BindingType;
import java.util.List;

@Entity
@Table
public class OutsourcingAssessmentSection extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_assessment")
    @JsonIgnore
    private OutsourcingAssessment outsourcingAssessment;

    @OneToMany(mappedBy = "outsourcingAssessmentSection")
    private List<OutsourcingAssessmentLine> outsourcingAssessmentLines;

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
        return outsourcingAssessmentLines;
    }

    public void setOutsourcingAssessmentLines(List<OutsourcingAssessmentLine> outsourcingAssessmentLines) {
        this.outsourcingAssessmentLines = outsourcingAssessmentLines;
    }
}



