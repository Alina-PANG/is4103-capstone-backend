package capstone.is4103capstone.general.model;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;

import java.io.Serializable;

public class GeneralEntityModel implements Serializable {
    private String id;
    private String code;
    private String name;

    public GeneralEntityModel(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public GeneralEntityModel(DBEntityTemplate entity){
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getObjectName();
        if (entity instanceof Employee){
            this.name = ((Employee) entity).getUserName();
        }
    }
    public GeneralEntityModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
