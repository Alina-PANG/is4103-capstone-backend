package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
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
    private String modifiedObjectUuid;
    private String modifiedObjectType;
    private OperationTypeEnum operationType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    public AuditTrailActivity() {
        // set date performed to now
        this.timeStamp = new Date();
    }

    // this is for API calls which do not reference objects
    public AuditTrailActivity(String userUuid, String activity) {
        this.userUuid = userUuid;
        this.activity = activity;
        this.modifiedObjectUuid = "N/A";
        this.modifiedObjectType = "N/A";
        this.timeStamp = new Date();
        this.operationType = OperationTypeEnum.READ;
    }

    // this is for calls which reference objects
    public AuditTrailActivity(String userUuid, String activity, String modifiedObjectUuid, String modifiedObjectType, OperationTypeEnum operationType) {
        this.userUuid = userUuid;
        this.activity = activity;
        this.modifiedObjectUuid = modifiedObjectUuid;
        this.modifiedObjectType = modifiedObjectType;
        this.operationType = operationType;
        // set date performed to now
        this.timeStamp = new Date();
    }

    // direct passing of modified object
    public AuditTrailActivity(String userUuid, String activity, DBEntityTemplate modifiedObject, OperationTypeEnum operationType) {
        this.userUuid = userUuid;
        this.activity = activity;
        this.modifiedObjectUuid = modifiedObject.getId() == null || modifiedObject.getId().isEmpty()? "N/A" : modifiedObject.getId();
        this.modifiedObjectType = modifiedObject.getClass().getSimpleName();
        this.operationType = operationType;
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

    public String getModifiedObjectType() {
        return modifiedObjectType;
    }

    public void setModifiedObjectType(String modifiedObjectType) {
        this.modifiedObjectType = modifiedObjectType;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }
}
