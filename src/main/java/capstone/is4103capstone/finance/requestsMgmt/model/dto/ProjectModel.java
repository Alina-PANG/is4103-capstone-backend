package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class ProjectModel extends RequestFormModel {
    private String startDate;
    private String endDate;

    private EmployeeModel projectOwner;
    private EmployeeModel projectSupervisor;
    private List<EmployeeModel> teamMembers = new ArrayList<>();
    private String projectTitle;
    private String lifeCycleStatus;

    public ProjectModel(Project e){//simple version of project
        setLifeCycleStatus(e.getProjectLifeCycleStatus().name());
        setProjectTitle(e.getProjectTitle());
        setProjectOwner(new EmployeeModel(e.getProjectOwner()));
        setRequester(new EmployeeModel(e.getRequester()));
        setApprovalStatus(e.getApprovalStatus().name());
        setId(e.getId());
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
        setProjectSupervisor(new EmployeeModel(e.getProjectSupervisor()));

    }


    public ProjectModel(Project e, List<Employee> members) {//team members need to manually convert? or just no need....
        super(e);
        setStartDate(Tools.dateFormatter.format(e.getStartDate()));
        setEndDate(Tools.dateFormatter.format(e.getEndDate()));
        setProjectOwner(new EmployeeModel(e.getProjectOwner()));
        setProjectTitle(e.getProjectTitle());
        setLifeCycleStatus(e.getProjectLifeCycleStatus().name());
        setProjectSupervisor(new EmployeeModel(e.getProjectSupervisor()));
        for (Employee member:members){
            teamMembers.add(new EmployeeModel(member));
        }
    }


    public EmployeeModel getProjectSupervisor() {
        return projectSupervisor;
    }

    public void setProjectSupervisor(EmployeeModel projectSupervisor) {
        this.projectSupervisor = projectSupervisor;
    }
    public ProjectModel() {
    }

    public String getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(String lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
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

    public EmployeeModel getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(EmployeeModel projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<EmployeeModel> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<EmployeeModel> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
