package capstone.is4103capstone.finance.finPurchaseOrder.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soa")
@CrossOrigin(origins = "http://localhost:3000")
public class StatementOfAccountController {
    @Autowired
    EmployeeService employeeService;


//    @PostMapping("/createBySchedule")
//    public ResponseEntity<GeneralRes> createPO(@RequestBody CreatePOReq createPOReq) {
//        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
//            return purchaseOrderService.createPO(createPOReq, null);
//        }
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @PostMapping("/createByInvoice")
//    public ResponseEntity<GeneralRes> createPO(@RequestBody CreatePOReq createPOReq) {
//        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
//            return purchaseOrderService.createPO(createPOReq, null);
//        }
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
//
//    @GetMapping("/getSoaByPO/{id}")
//    public ResponseEntity<GeneralRes> getPOAsApprover(@PathVariable("id") String id, @RequestParam(name="approved", required=true) Boolean approved){
//        ApprovalStatusEnum inputStatus;
//        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
//            return purchaseOrderService.getPOAsApprover(employeeService.getCurrentLoginUsername());
//        else
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
//    }
}
