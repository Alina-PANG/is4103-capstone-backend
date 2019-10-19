package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.finance.Repository.ProjectRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.ProjectModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateProjectReq;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CostCenterRepository costCenterRepository;
    @Autowired
    EmployeeService employeeService;

    public ProjectModel createProject(CreateProjectReq req) throws Exception{

        Project p = new Project();
        p.setObjectName(req.getProjectTitle());
        p.setProjectTitle(req.getProjectTitle());
        p.setRequestDescription(req.getProjectDescription());
        p.setAdditionalInfo(req.getAdditionalInfo()==null?"":req.getAdditionalInfo());
        try{
            p.setStartDate(Tools.dateFormatter.parse(req.getStartDate()));
            p.setEndDate(Tools.dateFormatter.parse(req.getEndDate()));
        }catch (ParseException pe){
            throw new Exception("Date format incorrect.");
        }

        Employee projectOwner = employeeService.validateUser(req.getProjectOwner());
        Employee projectRequester = req.getRequester()==null? employeeService.getCurrentLoginEmployee() : employeeService.validateUser(req.getRequester());


        Employee projectSupervisory = employeeService.validateUser(req.getProjectSupervisor());

        p.setProjectOwner(projectOwner);
        p.setRequester(projectRequester);
        p.setProjectSupervisor(projectSupervisory);
        for (String member:req.getTeamMembers())
            employeeService.validateUser(member);

        p.setMembers(req.getTeamMembers());
        p.setCreatedBy(projectRequester.getUserName());

        p.setEstimatedBudget(req.getBudget());
        p.setCurrency(req.getCurrency());
        p = projectRepository.save(p);
        String code = EntityCodeHPGeneration.getCode(projectRepository,p);
        p.setCode(code);

        //TODO: create request ticket
        ApprovalTicketService.createTicketAndSendEmail(projectRequester,projectSupervisory,p,p.getRequestDescription(), ApprovalTypeEnum.PROJECT);

        return new ProjectModel(p);
    }

    public ProjectModel updateProject(CreateProjectReq req) throws Exception{
        Project p = validateProject(req.getProjectId());
        System.out.println("*******"+req.getProjectId());
        if ( p.getProjectLifeCycleStatus().equals(ProjectStatus.CLOSED)){
            throw new Exception("Project is "+p.getProjectLifeCycleStatus()+", you can't update.");
        }
        if (!p.getProjectLifeCycleStatus().equals(ProjectStatus.PENDING_APPROVAL) && !p.getProjectLifeCycleStatus().equals(ProjectStatus.REJECTED)){
            //only allow start end date;
            try{
                p.setStartDate(Tools.dateFormatter.parse(req.getStartDate()));
                p.setEndDate(Tools.dateFormatter.parse(req.getEndDate()));
            }catch (ParseException pe){
                throw new Exception("Date format incorrect.");
            }
        }else{
            // pending status: can edit all the fields defined
            p.setObjectName(req.getProjectTitle() == null? p.getObjectName():req.getProjectTitle());
            p.setProjectTitle(req.getProjectTitle() == null? p.getObjectName():req.getProjectTitle());
            p.setRequestDescription(req.getProjectDescription() == null? p.getRequestDescription():req.getProjectDescription());
            p.setAdditionalInfo(req.getAdditionalInfo()==null?p.getAdditionalInfo():req.getAdditionalInfo());
            try{
                if (req.getStartDate() != null)
                    p.setStartDate(Tools.dateFormatter.parse(req.getStartDate()));
                if (req.getEndDate() != null)
                    p.setEndDate(Tools.dateFormatter.parse(req.getEndDate()));
            }catch (ParseException pe){
                throw new Exception("Date format incorrect.");
            }
            Employee projectOwner = p.getProjectOwner();
            Employee projectSupervisory = p.getProjectSupervisor();
            if (req.getProjectOwner() != null)
                projectOwner = employeeService.validateUser(req.getProjectOwner());
                p.setProjectOwner(projectOwner);
            if (req.getProjectSupervisor() != null)
                projectSupervisory = employeeService.validateUser(req.getProjectSupervisor());
                p.setProjectSupervisor(projectSupervisory);

            for (String member:req.getTeamMembers())
                employeeService.validateUser(member);

            p.setMembers(req.getTeamMembers());

            p.setEstimatedBudget(req.getBudget() == null?p.getEstimatedBudget() : req.getBudget());
            p.setCurrency(req.getCurrency() == null? p.getCurrency(): req.getCurrency());
            if (p.getProjectLifeCycleStatus().equals(ProjectStatus.REJECTED)){
                p.setProjectLifeCycleStatus(ProjectStatus.PENDING_APPROVAL);
                ApprovalTicketService.createTicketAndSendEmail(employeeService.getCurrentLoginEmployee(),projectSupervisory,p,"Re-submit Project plan."+p.getRequestDescription(), ApprovalTypeEnum.PROJECT);
            }
        }

        p.setLastModifiedBy(employeeService.getCurrentLoginUsername());
        p = projectRepository.saveAndFlush(p);

        return new ProjectModel(p);
    }

    public ProjectModel getProjectDetails(String projectId) throws Exception{
        Optional<Project> projectOps = projectRepository.findById(projectId);
        if (!projectOps.isPresent() || projectOps.get().getDeleted())
            throw new Exception("Project doesn't exist.");

        Project p = projectOps.get();

        List<Employee> members = new ArrayList<>();
        for (String memId: p.getMembers()){
            Employee e = employeeService.validateUser(memId);
            if (e == null)
                throw new Exception("Team member not found in database.");
            members.add(e);
        }
        p.getRequester();
        p.getProjectOwner();
        p.getProjectSupervisor();
        return new ProjectModel(p,members);
    }
    public void projectApproval(ApprovalForRequest ticket) throws Exception{
        Project p = validateProject(ticket.getRequestedItemId());
        p.setApprovalStatus(ticket.getApprovalStatus());
        boolean isApproved = ticket.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED);
        if (isApproved){
            p.setProjectLifeCycleStatus(ProjectStatus.APPROVED);
            createCostCenterForProject(p);
        }else {
            p.setProjectLifeCycleStatus(ProjectStatus.REJECTED);
            projectRepository.save(p);
        }
    }

    public void createCostCenterForProject(Project p) throws Exception{
        //cost center manager set to project owner;
        //
        try{
            CostCenter newCC = new CostCenter();
            newCC.setBmApprover(p.getProjectOwner());
            newCC.setFunctionApprover(p.getProjectSupervisor());
            newCC = costCenterRepository.save(newCC);
            String code = EntityCodeHPGeneration.getCode(costCenterRepository,newCC,"project");
            newCC.setCode(code);
            p.setCostCenter(newCC);
            projectRepository.save(p);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Error happened in creating project cost center: "+ex.getMessage());
        }
    }

    @Transactional
    public void updateProjectStatusDailyCheck(){
        List<Project> plist = projectRepository.findAll();
        for (Project p:plist){
            if (p.getDeleted() || p.getApprovalStatus().equals(ApprovalStatusEnum.REJECTED) || p.getProjectLifeCycleStatus().equals(ProjectStatus.REJECTED))
                continue;
            String today = Tools.dateFormatter.format(new Date());
            String tomorrow = Tools.getTomorrowStr(new Date());
            if (p.getProjectLifeCycleStatus().equals(ProjectStatus.APPROVED)){
                String projectStartDate = Tools.dateFormatter.format(p.getStartDate());
                if (projectStartDate.equals(today)){
                    p.setProjectLifeCycleStatus(ProjectStatus.ONGOING);
                    notifiyTeam(p,true);
                }
            }else{
                if (p.getProjectLifeCycleStatus().equals(ProjectStatus.ONGOING)){
                    p.setProjectLifeCycleStatus(ProjectStatus.CLOSED);
                    notifiyTeam(p,false);
                }
            }

        }
    }

    public List<ProjectModel> retrieveAllProjects() throws Exception{
        List<Project> projects = projectRepository.getAllProjects();
        if (projects.isEmpty())
            throw new Exception("No projects available.");

        List<ProjectModel> projectModels = new ArrayList<>();
        for (Project p:projects){
            String id = p.getProjectOwner().getId();
            id = p.getRequester().getId();
            projectModels.add(new ProjectModel(p));
        }
        return projectModels;
    }

    public List<ProjectModel> retrieveProjectsByOwner(String ownerId) throws Exception{
        Employee owner = employeeService.validateUser(ownerId);
        List<Project> projects = projectRepository.getProjectsByProjectOwnerId(owner.getId());
        if (projects.isEmpty())
            throw new Exception("No projects owned by the user.");
        List<ProjectModel> projectModels = new ArrayList<>();
        for (Project p:projects){
            projectModels.add(new ProjectModel(p));
        }
        return projectModels;
    }



    public Project validateProject(String idOrCode) throws EntityNotFoundException {

        Project e = projectRepository.getProjectByCode(idOrCode);
        if (e != null)
            if (!e.getDeleted())
                return e;
            else
                throw new EntityNotFoundException("Project already deleted.");


        Optional<Project> project = projectRepository.findById(idOrCode);
        if (project.isPresent())
            if (!project.get().getDeleted())
                return project.get();
            else
                throw new EntityNotFoundException("Project already deleted.");

        throw new EntityNotFoundException("Project code or id not valid");
    }

    //TODO:!! NEED EMAIL TEMPLATE
    public void notifiyTeam(Project p, boolean isStart){
        if (isStart){
            //notify starting
        }else{
            //notify ending
        }
    }


}
