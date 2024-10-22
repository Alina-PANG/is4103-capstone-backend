package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OutsourcingAssessmentLine extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_assessment_section")
    @JsonIgnore
    private OutsourcingAssessmentSection outsourcingAssessmentSection;

    private String question;
    private Boolean answer;
    private String comment;
    private int number;

    public OutsourcingAssessmentLine(String question) {
        this.question = question;
    }

    public OutsourcingAssessmentSection getOutsourcingAssessmentSection() {
        return outsourcingAssessmentSection;
    }

    public void setOutsourcingAssessmentSection(OutsourcingAssessmentSection outsourcingAssessmentSection) {
        this.outsourcingAssessmentSection = outsourcingAssessmentSection;
    }

    public OutsourcingAssessmentLine() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
