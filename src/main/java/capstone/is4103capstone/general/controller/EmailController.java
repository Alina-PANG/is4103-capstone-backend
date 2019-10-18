package capstone.is4103capstone.general.controller;

import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailSenderService;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    ApprovalTicketService approvalTicketService;

    @RequestMapping(value = "/send/{username}")
    public ResponseEntity<GeneralRes> sendEmail(@RequestBody Mail mail, @PathVariable("username") String username) {
        String template = "mailTemplate";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hangzhi");
        model.put("message", "test message");
        mail.setModel(model);
//        mailSenderService.sendmail();
        if(AuthenticationTools.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(mailSenderService.sendEmail(mail, template));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));

    }

    @RequestMapping(value = "/budgetSend")
    public ResponseEntity<String> sendBudget(@RequestBody Mail mail) {

        ApprovalForRequest approvalForRequest = new ApprovalForRequest();
        Employee approver = new Employee();
        approver.setEmail("hangzhipang@u.nus.edu");
        approvalForRequest.setApprover(approver);
        Employee requestor = new Employee();
        requestor.setEmail("test@gmail.com");
        requestor.setFirstName("first");
        requestor.setLastName("last");
        requestor.setUserName("username");
        requestor.setEmployeeType(EmployeeTypeEnum.TEMPORARY);
        approvalForRequest.setRequester(requestor);

        approvalForRequest.setCommentByRequester("this is a test request");
        approvalForRequest.setCode("TICKET_01");
        approvalForRequest.setRequestedItemId("ITEM_01");
        approvalForRequest.setCreatedDateTime(new Date());
        approvalTicketService.sendEmail(approvalForRequest);

            return ResponseEntity
                    .ok()
                    .body("Sent!");


    }
}