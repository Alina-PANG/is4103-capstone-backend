package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;

import javax.persistence.*;
import java.util.List;

/*
Haven't added in accessRequest
 */
@Entity
@Table
public class Employee extends DBEntityTemplate {

    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private List<String> groupsBelongTo;

    @ManyToMany
    private List<Team> memberOfTeams;
    @ManyToOne
    private Employee manager;
    @OneToMany
    private List<Employee> subordinates;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Team> getMemberOfTeams() {
        return memberOfTeams;
    }

    public void setMemberOfTeams(List<Team> memberOfTeams) {
        this.memberOfTeams = memberOfTeams;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public List<String> getGroupsBelongTo() {
        return groupsBelongTo;
    }

    public void setGroupsBelongTo(List<String> groupsBelongTo) {
        this.groupsBelongTo = groupsBelongTo;
    }
}
