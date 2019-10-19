package capstone.is4103capstone.finance.requestsMgmt.controller;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateProjectReq;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.finance.requestsMgmt.service.ProjectService;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.seat.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping
    public ResponseEntity<TTFormResponse> createProject(@RequestBody CreateProjectReq req){
        try{
            return ResponseEntity.ok().body(new TTFormResponse("Successfully created",false,projectService.createProject(req)));
        }catch (Exception ex){
            ex.printStackTrace();

            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<TTFormResponse> getProjectDetails(@PathVariable(name = "projectId") String projectId){
        try{
            EmployeeModel approverOfProject = ApprovalTicketService.getOpenTicketApproverByRequestedItem(projectId);
            System.out.println(approverOfProject.getFullName()+" PROJECT "+projectId);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currEmployee = (Employee) auth.getPrincipal();
            boolean currentUserCanApprove = approverOfProject.getId().equals(currEmployee.getId());
            return ResponseEntity.ok().body(new TTFormResponse("Successfully retrieved",false,
                    projectService.getProjectDetails(projectId),currentUserCanApprove));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<TTFormResponse> updateProject(@RequestBody CreateProjectReq req){
        try{
            return ResponseEntity.ok().body(new TTFormResponse("Successfully updated",false,projectService.updateProject(req)));
        }catch (Exception ex){
            ex.printStackTrace();

            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProjects(){
        try{
            return ResponseEntity.ok().body(
                    new TTListResponse("Successfully retrieved",false, projectService.retrieveAllProjects()));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/view-my/{userId}")
    public ResponseEntity<TTListResponse> getProjectsManagedBy(@PathVariable(name = "userId") String ownerId){
        try{
            return ResponseEntity.ok().body(
                    new TTListResponse("Successfully retrieved",false, projectService.retrieveProjectsByOwner(ownerId)));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }
}
