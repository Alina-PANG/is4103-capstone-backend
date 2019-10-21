package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.entities.supplyChain.*;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends DBEntityTemplate {
    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_securitygroup",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "securitygroup_id")
    )
    private List<SecurityGroup> memberOfSecurityGroups = new ArrayList<>();

    private String securityId;

    private EmployeeTypeEnum employeeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costcenter_id")
    private CostCenter defaultCostCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();

    @OneToMany(mappedBy = "requester")
    private List<BJF> bjfs = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<String> myRequestTickets = new ArrayList<>();
    @Convert(converter = StringListConverter.class)
    private List<String> myApprovals = new ArrayList<>();

    //    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "assignee")
    private List<Action> actionsAssigned = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Action> actionsCreated = new ArrayList<>();

    @OneToMany(mappedBy = "handler")
    private List<Dispute> disputesHandling = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Dispute> disputesCreated = new ArrayList<>();

    @OneToMany(mappedBy = "employeeInChargeContract")
    private List<Contract> contractInCharged = new ArrayList<>();

    @OneToMany(mappedBy = "approver")
    private List<Contract> contractsApproved = new ArrayList<>();

    @OneToMany(mappedBy = "employeeAssess")
    private List<OutsourcingAssessment> outsourcingAssessmentList = new ArrayList<>();

    public Employee() {
        this.securityId = "S-" + UUID.randomUUID();
    }

    public Employee(String userName, String firstName, String lastName, String middleName, String password) {
        this.userName = userName;
        this.setObjectName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.setPassword(password);
        this.securityId = "S-" + UUID.randomUUID();
    }

//    public List<ChildContract> getChildContractsApproved() {
//        return childContractsApproved;
//    }
//
//    public void setChildContractsApproved(List<ChildContract> childContractsApproved) {
//        this.childContractsApproved = childContractsApproved;
//    }

    public CostCenter getDefaultCostCenter() {
        return defaultCostCenter;
    }

    public void setDefaultCostCenter(CostCenter defaultCostCenter) {
        this.defaultCostCenter = defaultCostCenter;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Contract> getContractsApproved() {
        return contractsApproved;
    }

    public void setContractsApproved(List<Contract> contractsApproved) {
        this.contractsApproved = contractsApproved;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public String getFullName() {
        if (this.middleName == null || this.middleName.trim().length() == 0) {
            return this.firstName + " " + this.lastName;
        }
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // init the argon2 hashing library
        Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
        this.password = argon2.hash(6, 128 * 1024, 1, password);
    }


    public EmployeeTypeEnum getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeEnum employeeType) {
        this.employeeType = employeeType;
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

    public List<String> getMyRequestTickets() {
        return myRequestTickets;
    }

    public void setMyRequestTickets(List<String> myRequestTickets) {
        this.myRequestTickets = myRequestTickets;
    }

    public List<String> getMyApprovals() {
        return myApprovals;
    }

    public void setMyApprovals(List<String> myApprovals) {
        this.myApprovals = myApprovals;
    }

    public List<BJF> getBjfs() {
        return bjfs;
    }

    public void setBjfs(List<BJF> bjfs) {
        this.bjfs = bjfs;
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

    public List<Contract> getContractInCharged() {
        return contractInCharged;
    }

    public void setContractInCharged(List<Contract> contractInCharged) {
        this.contractInCharged = contractInCharged;
    }

    public void addContractIC(Contract newContract) {
        this.getContractInCharged().add(newContract);
    }

    public List<OutsourcingAssessment> getOutsourcingAssessmentList() {
        return outsourcingAssessmentList;
    }

    public void setOutsourcingAssessmentList(List<OutsourcingAssessment> outsourcingAssessmentList) {
        this.outsourcingAssessmentList = outsourcingAssessmentList;
    }

    public String getSecurityId() {
        return securityId;
    }


    public List<SecurityGroup> getMemberOfSecurityGroups() {
        return memberOfSecurityGroups;
    }

    public void setMemberOfSecurityGroups(List<SecurityGroup> memberOfSecurityGroups) {
        this.memberOfSecurityGroups = memberOfSecurityGroups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }
}
