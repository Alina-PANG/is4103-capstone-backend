package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class OutsourcingAssessmentLine extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_assessment_section")
    @JsonIgnore
    private OutsourcingAssessmentSection outsourcingAssessmentSection;

    private String question;
    private String answer;
    private String comment;

    public OutsourcingAssessmentLine(String question) {
        this.question = question;
    }

    public OutsourcingAssessmentSection getOutsourcingAssessmentSection() {
        return outsourcingAssessmentSection;
    }

    public void setOutsourcingAssessmentSection(OutsourcingAssessmentSection outsourcingAssessmentSection) {
        this.outsourcingAssessmentSection = outsourcingAssessmentSection;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
