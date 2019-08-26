package capstone.is4103capstone.demoModule.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "demorole")
public class DemoRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<DemoUser> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DemoUser> getUsers() {
        return users;
    }

    public void setUsers(Set<DemoUser> users) {
        this.users = users;
    }
}
