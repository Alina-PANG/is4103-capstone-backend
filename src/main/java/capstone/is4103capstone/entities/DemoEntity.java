package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.Entity;
import javax.persistence.Table;

//add the two annotations
@Entity
@Table
public class DemoEntity extends DBEntityTemplate {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
