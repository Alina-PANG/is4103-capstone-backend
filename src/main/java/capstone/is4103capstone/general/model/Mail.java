package capstone.is4103capstone.general.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mail implements Serializable {
    @Email
    @NotNull
    private String from;
    @Email
    @NotNull
    private String to;
    private String subject;
    private Map<String, Object> model;


    public Mail() {
    }


    public Mail(@Email @NotNull String from, @Email @NotNull String to, String subject, Map<String, Object> model) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.model = model;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
