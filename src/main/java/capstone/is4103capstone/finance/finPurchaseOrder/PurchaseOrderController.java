package capstone.is4103capstone.finance.finPurchaseOrder;

import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchaseOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderController {
    @Autowired
    PurchaseOrderService purchaseOrderService;

    @PostMapping("/createPO")
    public ResponseEntity<GeneralRes> createPO(@RequestBody CreatePOReq createPOReq) {
        if(Authentication.authenticateUser(createPOReq.getUsername())) {
                return purchaseOrderService.createPO(createPOReq, null);
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/updatePO/{id}")
    public ResponseEntity<GeneralRes> updatePO(@RequestBody CreatePOReq createPOReq, @PathVariable("id") String id) {
        if(Authentication.authenticateUser(createPOReq.getUsername()))
            return purchaseOrderService.createPO(createPOReq, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getPO/{id}")
    public ResponseEntity<GeneralRes> getPO(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return purchaseOrderService.getPO(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getListPO/{status}")
    public ResponseEntity<GeneralRes> getListPO(@PathVariable("status") BudgetPlanStatusEnum status, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return purchaseOrderService.getListPO(status);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }


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
