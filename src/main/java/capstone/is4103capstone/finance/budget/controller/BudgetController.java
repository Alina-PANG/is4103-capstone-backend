package capstone.is4103capstone.finance.budget.controller;

import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.finance.budget.service.BudgetService;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

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

//    @GetMapping("/getMostRecentDraft") // [TODO] test
//    public ResponseEntity<GeneralRes> getMostRecentDraft(@RequestParam(name="username", required=true) String username, @RequestParam(name="code", required=true) String code, @RequestParam(name="type", required=true) String type){
//        if(Authentication.authenticateUser(username))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.getMostRecentPlanDraft(username, type, code));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }

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

    @GetMapping("/getBudget/{id}")
    public ResponseEntity<GeneralRes> getBudget(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return ResponseEntity.ok().body(budgetService.getBudget(id));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getBudgetList/{costcenterId}")
    public ResponseEntity<GeneralRes> getPendingBudgetList(@PathVariable("costcenterId") String costcenterId, @RequestParam(name="username", required=true) String username, @RequestParam(name="type", required=true) boolean isBudget){
        if(Authentication.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(budgetService.getBudgetList(costcenterId, username, isBudget));
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
}
