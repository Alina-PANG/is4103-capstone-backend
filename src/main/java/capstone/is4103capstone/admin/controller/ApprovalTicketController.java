package capstone.is4103capstone.admin.controller;

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
            return ResponseEntity.ok().body(approvalTicketService.approveTicketAPI(result.getRequestedItemId(),result.getApproverComment(),true));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<GeneralRes> rejectRequest(@RequestBody ApprovalTicketModel result){
        try{
            return ResponseEntity.ok().body(approvalTicketService.approveTicketAPI(result.getRequestedItemId(),result.getApproverComment(),false));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));

        }
    }

    @GetMapping("/approval-tickets-waiting-list/{approverId}")
    public @ResponseBody ResponseEntity<Object> getPendingTicketsByApproverId(@PathVariable(name = "approverId") String approverId){
        try{
            return ResponseEntity.ok().body(approvalTicketService.getPendingTicketsByApprover(approverId));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));

        }
    }
}
