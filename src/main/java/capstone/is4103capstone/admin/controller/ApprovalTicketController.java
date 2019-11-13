package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApprovalTicketController {
    @Autowired
    ApprovalTicketService approvalTicketService;
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/approval-ticket/{id}")
    public @ResponseBody ResponseEntity<Object> getTicketById(@PathVariable(name = "id") String ticketId, @RequestHeader(name = "Authorization", required = false) String headerUsername){
        if(AuthenticationTools.authenticateUser(headerUsername))
            return new ResponseEntity<Object>(approvalTicketService.getTicketById(ticketId,headerUsername).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/approve")
    public ResponseEntity<GeneralRes> approveRequest(@RequestBody ApprovalTicketModel result){
        try{
            return ResponseEntity.ok().body(approvalTicketService.approveTicketAPI(result,true));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<GeneralRes> rejectRequest(@RequestBody ApprovalTicketModel result){
        try{
            return ResponseEntity.ok().body(approvalTicketService.approveTicketAPI(result,false));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }
    }


    @GetMapping("/approval-tickets-waiting-list")
    public @ResponseBody ResponseEntity<Object> getPendingTicketsByCurrentUser(){
        try{
            String approverId = employeeService.getCurrentLoginEmployee().getId();
            return ResponseEntity.ok().body(approvalTicketService.getPendingTicketsByApprover(approverId));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));

        }
    }
}
