package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class Project extends DBEntityTemplate {

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectOwner_Id")
    private Employee projectOwner;

    private String description;

    private BigDecimal budgetAmt;

    private String costCenterCode;

    public Project() {
    }

    public Project(String projectName, String projectCode, Date startDate, Date endDate, String description, BigDecimal budgetAmt,String costCenterCode) {
        super(projectName, projectCode);
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.budgetAmt = budgetAmt;
        this.costCenterCode = costCenterCode;
    }



    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(BigDecimal budgetAmt) {
        this.budgetAmt = budgetAmt;
    }
}
