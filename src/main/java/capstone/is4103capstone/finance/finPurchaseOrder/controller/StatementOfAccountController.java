package capstone.is4103capstone.finance.finPurchaseOrder.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByInvoiceReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByScheduleReq;
import capstone.is4103capstone.finance.finPurchaseOrder.service.StatementOfAccountService;
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
    @Autowired
    StatementOfAccountService statementOfAccountService;

    @PostMapping("/createBySchedule")
    public ResponseEntity<GeneralRes> createBySchedule(@RequestBody CreateSoAByScheduleReq createSoAByScheduleReq) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
            return statementOfAccountService.createBySchedule(createSoAByScheduleReq, employeeService.getCurrentLoginUsername());
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }


    @PostMapping("/createByInvoice")
    public ResponseEntity<GeneralRes> createByInvoice(@RequestBody CreateSoAByInvoiceReq createSoAByInvoiceReq) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
            return statementOfAccountService.createByInvoice(createSoAByInvoiceReq, employeeService.getCurrentLoginUsername());
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getSoaByPO/{id}")
    public ResponseEntity<GeneralRes> getSoaByPO(@PathVariable("id") String id){
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return statementOfAccountService.getSoaByPo(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
