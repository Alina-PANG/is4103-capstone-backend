package capstone.is4103capstone.finance.budget.controller;

import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.finance.budget.model.res.PlanCompareRes;
import capstone.is4103capstone.finance.budget.service.BudgetService;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.service.PlansComparisonService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private PlansComparisonService comparisonService;

    @PostMapping("/createBudget")
    public ResponseEntity<GeneralRes> createBudget(@RequestBody CreateBudgetReq createBudgetReq) {
        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(budgetService.createBudget(createBudgetReq, null));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/updateBudget/{id}")
    public ResponseEntity<GeneralRes> updateBudget(@RequestBody CreateBudgetReq createBudgetReq, @PathVariable("id") String id) {
        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(budgetService.createBudget(createBudgetReq, id));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getBudgetDetails/{id}")
    public ResponseEntity<GeneralRes> getBudget(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return ResponseEntity.ok().body(budgetService.getBudget(id,username));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getBudgetListByTeam")
    public ResponseEntity<GeneralRes> getAllBudgetListByUser(@RequestParam(name="username", required=true) String username, @RequestParam(name="teamId", required=true) String teamId,@RequestParam(value = "ccId",required = false) String costcenterId, @RequestParam(name="type",required = false) Integer retrieveType, @RequestParam(name="year",required = false) Integer year){
        if(Authentication.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(budgetService.getBudgetList(username,teamId));//,costcenterId,  retrieveType,year));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/approveBudget")
    public ResponseEntity approveBudget(@RequestBody ApproveBudgetReq approveBudgetReq){
        if(Authentication.authenticateUser(approveBudgetReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(budgetService.approveBudget(approveBudgetReq));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/diff")
    public ResponseEntity<GeneralRes> compareReforecast(@RequestParam(name = "reforecastId",required = true) String reforecastPlanId){
        try{
            return ResponseEntity
                    .ok()
                    .body(comparisonService.getPlanComparisonResult(reforecastPlanId));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new GeneralRes(ex.getMessage(), true));
        }
    }
}
