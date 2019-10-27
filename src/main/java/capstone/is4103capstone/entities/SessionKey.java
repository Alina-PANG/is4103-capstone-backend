package capstone.is4103capstone.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class SessionKey implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String sessionKey;
    private Date lastAuthenticated;

    @OneToOne(fetch = FetchType.LAZY)
    private Employee linkedUser;

    public SessionKey() {
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getLastAuthenticated() {
        return lastAuthenticated;
    }

    public void setLastAuthenticated(Date lastAuthenticated) {
        this.lastAuthenticated = lastAuthenticated;
    }

    public Employee getLinkedUser() {
        return linkedUser;
    }

    public void setLinkedUser(Employee linkedUser) {
        this.linkedUser = linkedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
