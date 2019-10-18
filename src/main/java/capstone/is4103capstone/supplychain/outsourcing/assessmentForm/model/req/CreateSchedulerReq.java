package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req;

import capstone.is4103capstone.general.model.Mail;

import java.io.Serializable;
import java.util.Date;

public class CreateSchedulerReq implements Serializable {
    Date date;
    Mail mail;

    public CreateSchedulerReq() {
    }

    public CreateSchedulerReq(Date date, Mail mail) {
        this.date = date;
        this.mail = mail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }
}
