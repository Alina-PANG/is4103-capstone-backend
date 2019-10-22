package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OutsourcingSelfAssessmentQuestion extends DBEntityTemplate {
    private String question;
    private Boolean answer;
    private String comment;

    public OutsourcingSelfAssessmentQuestion() {
    }

    public OutsourcingSelfAssessmentQuestion(String question, Boolean answer, String comment) {
        this.question = question;
        this.answer = answer;
        this.comment = comment;
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
