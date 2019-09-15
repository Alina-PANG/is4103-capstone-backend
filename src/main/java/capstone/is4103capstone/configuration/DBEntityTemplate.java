package capstone.is4103capstone.configuration;

import capstone.is4103capstone.entities.enums.PermissionTypeEnum;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DBEntityTemplate implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false,length = 36)
    @Length(min=36, max=36)
    private String id;

    public DBEntityTemplate() {
    }

    public DBEntityTemplate(String objectName, String code) {
        this.objectName = objectName;
        this.code = code;
    }

    public DBEntityTemplate(String code) {
        this.code = code;
    }

    public DBEntityTemplate(String objectName, String code, String hierachyPath) {
        this.objectName = objectName;
        this.code = code;
        this.hierachyPath = hierachyPath;
    }

    public DBEntityTemplate(String objectName, String code, String hierachyPath, String createdBy, String lastModifiedBy) {
        this.objectName = objectName;
        this.code = code;
        this.hierachyPath = hierachyPath;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    private String objectName;

    @Column(unique = true)
    private String code;

    private String hierachyPath;

    private Boolean isDeleted = false;

    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreatedDate
    private Date createdDateTime;

    private String lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDateTime;
    private MultiValuedMap<PermissionTypeEnum, String> permissionMap = new HashSetValuedHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(Date lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHierachyPath() {
        return hierachyPath;
    }

    public void setHierachyPath(String hierachyPath) {
        this.hierachyPath = hierachyPath;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public MultiValuedMap<PermissionTypeEnum, String> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(MultiValuedMap<PermissionTypeEnum, String> permissionMap) {
        this.permissionMap = permissionMap;
    }
}