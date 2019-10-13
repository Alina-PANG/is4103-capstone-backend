package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;

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

        Employee projectOwner = validateUser(req.getProjectOwner());
        Employee projectRequester = validateUser(req.getRequester());
        if (projectOwner == null)
            throw new Exception("Requester doesn't exist");
        p.setProjectOwner(projectOwner);
        for (String member:req.getTeamMembers())
            if (validateUser(member) == null)
                throw new Exception("Team member ["+member+"] doesn't exist.");

        p.setMembers(req.getTeamMembers());
        p.setCreatedBy(req.getRequester());
        p.setRequester(projectRequester);

        p.setEstimatedBudget(req.getBudget());
        p.setCurrency(req.getCurrency());
        p = projectRepository.save(p);
        String code = EntityCodeHPGeneration.getCode(projectRepository,p);
        p.setCode(code);

        //TODO: create request ticket
        //once approved: create a cost center for this project, related with the project code

        return new ProjectModel(p);
    }

    public ProjectModel getProjectDetails(String projectId) throws Exception{
        Optional<Project> projectOps = projectRepository.findById(projectId);
        if (!projectOps.isPresent() || projectOps.get().getDeleted())
            throw new Exception("Project doesn't exist.");

        Project p = projectOps.get();

        List<Employee> members = new ArrayList<>();
        for (String memId: p.getMembers()){
            Employee e = employeeRepository.findEmployeeById(memId);
            if (e == null)
                throw new Exception("Team member not found in database.");
            members.add(e);
        }
        p.getRequester();
        p.getProjectOwner();
        return new ProjectModel(p,members);
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
        List<Project> projects = projectRepository.getProjectsByProjectOwnerId(ownerId);
        if (projects.isEmpty())
            throw new Exception("No projects owned by the user.");
        List<ProjectModel> projectModels = new ArrayList<>();
        for (Project p:projects){
            projectModels.add(new ProjectModel(p));
        }
        return projectModels;
    }

    private Employee validateUser(String idOrUsername){
        Optional<Employee> employee = employeeRepository.findUndeletedEmployeeById(idOrUsername);
        if (employee.isPresent())
            return employee.get();
        Employee e = employeeRepository.findEmployeeByUserName(idOrUsername);
        if (e != null)
            return e;
        return null;
    }
}
