package capstone.is4103capstone.module01.domain;

import org.springframework.data.annotation.Id;

/*
Short-cuts

Auto-generation: right click -> generate
                 or Alt+Insert
 */


public class DemoObject {
    @Id
    Long id;

    String description;

    public DemoObject(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
