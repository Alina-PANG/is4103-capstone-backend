package capstone.is4103capstone.entities;

import capstone.is4103capstone.util.enums.OperationTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table
public class AuditTrailActivity implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String userUuid;
    private String activity;
    private String modifiedObjectUuid = "";
    private String objectType = "";
    private OperationTypeEnum operationTypeEnum;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    public AuditTrailActivity() {
        // set date performed to now
        this.timeStamp = new Date();
    }

    public AuditTrailActivity(String userUuid, String activity) {
        this.userUuid = userUuid;
        this.activity = activity;
        this.timeStamp = new Date();
    }

    public AuditTrailActivity(String userUuid, String activity, String modifiedObjectUuid) {
        this.userUuid = userUuid;
        this.activity = activity;
        this.modifiedObjectUuid = modifiedObjectUuid;
        // set date performed to now
        this.timeStamp = new Date();
    }

    public String getModifiedObjectUuid() {
        return modifiedObjectUuid;
    }

    public void setModifiedObjectUuid(String modifiedObjectUuid) {
        this.modifiedObjectUuid = modifiedObjectUuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
