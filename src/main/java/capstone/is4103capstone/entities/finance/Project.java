package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.entities.helper.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project extends RequestFormTemplate {

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectOwner_Id")
    private Employee projectOwner;

    @Convert(converter = StringListConverter.class)
    private List<String> members = new ArrayList<>();

    private String projectTitle;

    public Project() {
    }

    public Project(String objectName, Employee requester, CostCenter costCenter, String requestDescription, String additionalInfo, BigDecimal estimatedBudget, String currency, Date startDate, Date endDate, Employee projectOwner, List<String> members, String projectTitle) {
        super(objectName, requester, costCenter, requestDescription, additionalInfo, estimatedBudget, currency);
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
        this.members = members;
        this.projectTitle = projectTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Employee projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
