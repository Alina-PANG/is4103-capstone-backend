package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/approval-ticket")
public class ApprovalTicketController {
    @Autowired
    ApprovalTicketService approvalTicketService;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getTicketById(@PathVariable(name = "id") String ticketId, @RequestHeader(name = "Authorization", required = false) String headerUsername){
        if(Authentication.authenticateUser(headerUsername))
            return new ResponseEntity<Object>(approvalTicketService.getTicketById(ticketId,headerUsername).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);


    }
}
