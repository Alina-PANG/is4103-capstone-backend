package capstone.is4103capstone.seat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupModel implements Serializable {
    private String id;
    private String code;
    private String name;

    public GroupModel() {
    }

    public GroupModel(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
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
