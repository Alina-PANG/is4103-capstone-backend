package capstone.is4103capstone.finance.requestsMgmt.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.finance.requestsMgmt.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/travel")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TravelController {
    @Autowired
    TravelService travelService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<TTFormResponse> createTravelRequest(@RequestBody CreateTravelRequest req){
        try{
            return ResponseEntity.ok().body(travelService.createTravelPlan(req));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }

    }


    @GetMapping("/view-my-request")
    public ResponseEntity<TTListResponse> getTravelPlansByUser(@PathVariable(name = "id",required = false) String usernameOrId){
        try{
            if (usernameOrId == null || usernameOrId.isEmpty()){
                usernameOrId = employeeService.getCurrentLoginUsername();
            }
            return ResponseEntity.ok().body(travelService.retrieveTravelPlansByUser(usernameOrId));
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
            return ResponseEntity.ok().body(travelService.retrieveTravelPlanByApprover(usernameOrId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TTFormResponse> getTravelPlanDetails(@PathVariable(name = "id") String planId){
        try{
            return ResponseEntity.ok().body(travelService.getTravelPlanDetails(planId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }
}
