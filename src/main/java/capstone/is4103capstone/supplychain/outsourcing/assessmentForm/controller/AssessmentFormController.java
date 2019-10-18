package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.controller;

import capstone.is4103capstone.finance.budget.controller.BudgetController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessmentForm")
@CrossOrigin(origins = "http://localhost:3000")
public class AssessmentFormController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

//    @Autowired
//    private
//
//
//    @PostMapping("/create")
//    public ResponseEntity<GeneralRes> createBudget(@RequestBody CreateBudgetReq createBudgetReq) {
//        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.createBudget(createBudgetReq, null));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @PostMapping("/update/{id}")
//    public ResponseEntity<GeneralRes> updateBudget(@RequestBody CreateBudgetReq createBudgetReq, @PathVariable("id") String id) {
//        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.createBudget(createBudgetReq, id));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @GetMapping("/getDetails/{id}")
//    public ResponseEntity<GeneralRes> getBudget(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
//        if(Authentication.authenticateUser(username))
//            return ResponseEntity.ok().body(budgetService.getBudget(id,username));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @PostMapping("/approve")
//    public ResponseEntity approveBudget(@RequestBody ApproveBudgetReq approveBudgetReq){
//        if(Authentication.authenticateUser(approveBudgetReq.getUsername()))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.approveBudget(approveBudgetReq));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }

}
