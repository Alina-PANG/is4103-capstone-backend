package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.enums.EmployeeTypeEnum;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Convert(converter = StringListConverter.class)
    private List<String> groupsBelongTo = new ArrayList<>();

    private EmployeeTypeEnum employeeType;
    //A uni-directional with CostCenter
    private String costCenterCode;

    @ManyToMany(mappedBy = "members",fetch = FetchType.EAGER)
    private List<Team> memberOfTeams = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();

    public Employee() {
    }

    @OneToMany(mappedBy = "assignee")
    private List<Action> actionsAssigned;

    @OneToMany(mappedBy = "creator")
    private List<Action> actionsCreated;

    @OneToMany(mappedBy = "handler")
    private List<Dispute> disputesHandling;

    @OneToMany(mappedBy = "creator")
    private List<Dispute> disputesCreated;

    @OneToMany(mappedBy = "employeeInCharge")
    private List<Outsourcing> outsourcingInCharged;

    @OneToMany(mappedBy = "employeeAssess")
    private List<OutsourcingAssessment> outsourcingAssessmentList;


    public Employee(String userName, String firstName, String lastName, String middleName, String password) {
        this.userName = userName;
        this.setObjectName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
    }


    public List<Outsourcing> getOutsourcingInCharged() {
        return outsourcingInCharged;
    }

    public List<OutsourcingAssessment> getOutsourcingAssessmentList() {
        return outsourcingAssessmentList;
    }

    public void setOutsourcingAssessmentList(List<OutsourcingAssessment> outsourcingAssessmentList) {
        this.outsourcingAssessmentList = outsourcingAssessmentList;
    }

    public void setOutsourcingInCharged(List<Outsourcing> outsourcingInCharged) {
        this.outsourcingInCharged = outsourcingInCharged;
    }

    public List<Dispute> getDisputesHandling() {
        return disputesHandling;
    }

    public void setDisputesHandling(List<Dispute> disputesHandling) {
        this.disputesHandling = disputesHandling;
    }

    public List<Dispute> getDisputesCreated() {
        return disputesCreated;
    }

    public void setDisputesCreated(List<Dispute> disputesCreated) {
        this.disputesCreated = disputesCreated;
    }

    public List<Action> getActionsAssigned() {
        return actionsAssigned;
    }

    public void setActionsAssigned(List<Action> actionsAssigned) {
        this.actionsAssigned = actionsAssigned;
    }

    public List<Action> getActionsCreated() {
        return actionsCreated;
    }

    public void setActionsCreated(List<Action> actionsCreated) {
        this.actionsCreated = actionsCreated;
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
        this.password = password;
    }

    public List<String> getGroupsBelongTo() {
        return groupsBelongTo;
    }

    public void setGroupsBelongTo(List<String> groupsBelongTo) {
        this.groupsBelongTo = groupsBelongTo;
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
}
