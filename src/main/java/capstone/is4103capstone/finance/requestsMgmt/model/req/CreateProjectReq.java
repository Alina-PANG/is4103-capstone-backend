package capstone.is4103capstone.finance.requestsMgmt.model.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CreateProjectReq implements Serializable {

    String requester;
    String projectTitle;
    String projectDescription;
    String startDate;
    String endDate;
    String projectOwner;
    List<String> teamMembers;
    BigDecimal budget;
    String currency;
    String additionalInfo;

    public CreateProjectReq() {
    }

    public CreateProjectReq(String requester, String projectTitle, String projectDescription, String startDate, String endDate, String projectOwner, List<String> teamMembers, BigDecimal budget, String currency, String additionalInfo) {
        this.requester = requester;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
        this.teamMembers = teamMembers;
        this.budget = budget;
        this.currency = currency;
        this.additionalInfo = additionalInfo;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
