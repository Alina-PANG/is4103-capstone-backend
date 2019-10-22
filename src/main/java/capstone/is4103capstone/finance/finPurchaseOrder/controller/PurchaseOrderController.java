package capstone.is4103capstone.finance.finPurchaseOrder.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.finance.finPurchaseOrder.service.PurchaseOrderService;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchaseOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);
    @Autowired
    PurchaseOrderService purchaseOrderService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/createPO")
    public ResponseEntity<GeneralRes> createPO(@RequestBody CreatePOReq createPOReq) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
                return purchaseOrderService.createPO(createPOReq, null);
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/updatePO/{id}")
    public ResponseEntity<GeneralRes> updatePO(@RequestBody CreatePOReq createPOReq, @PathVariable("id") String id) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return purchaseOrderService.createPO(createPOReq, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getPO/{id}")
    public ResponseEntity<GeneralRes> getPO(@PathVariable("id") String id){
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return purchaseOrderService.getPO(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getPOAsApprover")
    public ResponseEntity<GeneralRes> getPOAsApprover(){
        ApprovalStatusEnum inputStatus;
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return purchaseOrderService.getPOAsApprover(employeeService.getCurrentLoginUsername());
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getPOAsRequestor")
    public ResponseEntity<GeneralRes> getPOAsRequestor(){
        ApprovalStatusEnum inputStatus;
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return purchaseOrderService.getPOAsRequestor(employeeService.getCurrentLoginUsername());
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }


    @PostMapping("/approvePO/{id}")
    public ResponseEntity<GeneralRes> getListPO(@PathVariable("id") String id, @RequestParam(name="approved", required=true) int approved){
        System.out.println("reached!");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return purchaseOrderService.approvePO(id, approved == 1);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
