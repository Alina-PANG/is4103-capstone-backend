package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.enums.EmployeeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
Haven't added in accessRequest
 */
@Entity
@Table
public class Employee extends DBEntityTemplate {
    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;

    @ManyToMany
    private List<SecurityGroup> memberOfSecurityGroups = new ArrayList<>();
    private String securityId;

    private EmployeeTypeEnum employeeType;
    //A uni-directional with CostCenter
    private String costCenterCode;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Team> memberOfTeams = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();

    public Employee() {
    }

    public Employee(String userName, String firstName, String lastName, String middleName, String password) {
        this.userName = userName;
        this.setObjectName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.setPassword(password);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

        // hash the password
        // init argon2 hashing algo
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        // hash the plaintext password
        this.password = argon2.hash(8, 64*1024, 1, password);

        // clear the password - probably unnecessary/insecure
        password = null;
    }

    public EmployeeTypeEnum getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeEnum employeeType) {
        this.employeeType = employeeType;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
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

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public List<SecurityGroup> getMemberOfSecurityGroups() {
        return memberOfSecurityGroups;
    }

    public void setMemberOfSecurityGroups(List<SecurityGroup> memberOfSecurityGroups) {
        this.memberOfSecurityGroups = memberOfSecurityGroups;
    }
}
