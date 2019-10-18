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
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateProjectReq;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
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
        Employee projectRequester = employeeService.validateUser(req.getRequester());
        Employee projectSupervisory = employeeService.validateUser(req.getProjectSupervisor());

        p.setProjectOwner(projectOwner);
        p.setRequester(projectRequester);
        p.setProjectSupervisor(projectSupervisory);
        for (String member:req.getTeamMembers())
            employeeService.validateUser(member);

        p.setMembers(req.getTeamMembers());
        p.setCreatedBy(req.getRequester());

        p.setEstimatedBudget(req.getBudget());
        p.setCurrency(req.getCurrency());
        p = projectRepository.save(p);
        String code = EntityCodeHPGeneration.getCode(projectRepository,p);
        p.setCode(code);

        //TODO: create request ticket
        //TODO: once approved: create a cost center for this project, related with the project code

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

    //TODO: Pending test after approval step: this function is invoke after project plan is approved;
    @Transactional
    public void createCostCenterForProject(String projectId) throws Exception{
        //cost center manager set to project owner;
        //
        CostCenter newCC = new CostCenter();
        Optional<Project> pOps = projectRepository.findById(projectId);
        if (!pOps.isPresent())
            throw new Exception("[Internal error] given projectid not correct");
        Project project = pOps.get();

        newCC.setBmApprover(project.getProjectOwner());
        newCC.setFunctionApprover(project.getProjectSupervisor());
        newCC = costCenterRepository.save(newCC);
        String code = EntityCodeHPGeneration.getCode(costCenterRepository,newCC,"project");
        newCC.setCode(code);

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

    public void projectApproval(ApprovalForRequest ticket){

    }
}
