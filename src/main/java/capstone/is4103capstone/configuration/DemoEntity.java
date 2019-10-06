package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;

//add the two annotations
@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DemoEntity extends DBEntityTemplate {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
