package capstone.is4103capstone.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class AuditTrailActivity implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String userUuid;
    private String activity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    public AuditTrailActivity() {
        // set date performed to now
        this.timeStamp = new Date();
    }

    public AuditTrailActivity(String userUuid, String activity) {
        this.userUuid = userUuid;
        this.activity = activity;
        // set date performed to now
        this.timeStamp = new Date();
    }
}
