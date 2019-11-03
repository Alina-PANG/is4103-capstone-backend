package capstone.is4103capstone.finance.requestsMgmt.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTrainingRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.finance.requestsMgmt.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/train")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TrainingController {

    @Autowired
    TrainingService trainingService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<TTFormResponse> createTrainingRequest(@RequestBody CreateTrainingRequest req){
        try{
            return ResponseEntity.ok().body(trainingService.createTrainingPlan(req));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/view-my-request")
    public ResponseEntity<TTListResponse> getTrainingPlansByUser(){
        try{
            String usernameOrId = employeeService.getCurrentLoginUsername();

            return ResponseEntity.ok().body(trainingService.retrieveTrainingPlansByUser(usernameOrId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/view-my-approval")
    public ResponseEntity<TTListResponse> getTravelPlansByApprover(@PathVariable(name = "id",required = false) String usernameOrId){
        try{
            if (usernameOrId == null || usernameOrId.isEmpty()){
                usernameOrId = employeeService.getCurrentLoginUsername();
            }
            return ResponseEntity.ok().body(trainingService.retrieveTrainingPlanByApprover(usernameOrId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TTFormResponse> getTrainingPlanDetails(@PathVariable(name = "id") String planId){
        try{
            return ResponseEntity.ok().body(trainingService.getTrainingPlanDetails(planId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }

    }

}
