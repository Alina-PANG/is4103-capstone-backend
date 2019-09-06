package capstone.is4103capstone._demoModule.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "demouser")
public class DemoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(name = "demo_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<DemoRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<DemoRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<DemoRole> roles) {
        this.roles = roles;
    }
}
