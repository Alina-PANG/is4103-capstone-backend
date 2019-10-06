package capstone.is4103capstone.finance.finPurchaseOrder;

import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchaseOrder")
public class PurchaseOrderController {
//    @PostMapping("/createPO")
//    public ResponseEntity<GeneralRes> createPO(@RequestBody CreateBudgetReq createBudgetReq) {
//        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.createBudget(createBudgetReq));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @PostMapping("/updateBudget/{id}")
//    public ResponseEntity<GeneralRes> updateBudget(@RequestBody CreateBudgetReq createBudgetReq, @PathVariable("id") String id) {
//        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.updateBudget(createBudgetReq, id));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @GetMapping("/getBudget/{id}")
//    public ResponseEntity<GeneralRes> getBudget(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
//        if(Authentication.authenticateUser(username))
//            return ResponseEntity.ok().body(budgetService.getBudget(id));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @GetMapping("/getBudgetList")
//    public ResponseEntity<GeneralRes> getPendingBudgetList(@RequestParam(name="username", required=true) String username, @RequestParam(name="type", required=true) String type, @RequestParam(name="status", required=true) String status){
//        if(Authentication.authenticateUser(username))
//            return ResponseEntity
//                    .ok()
//                    .body(budgetService.getBudgetList(username, type, status));
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }

//    @PostMapping("/approveBudget")
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
