package capstone.is4103capstone.general.controller;

import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.general.model.ChatbotViewTicketModel;
import capstone.is4103capstone.general.service.ChatbotSpecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chabot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotSpecialServiceController {


    @Autowired
    ChatbotSpecialService chatbotSpecialService;

    @GetMapping("/pending-approvals")
    public ResponseEntity<Object> getMyPendingApprovals(@RequestParam(name = "approver",required = false) String approvalIdOrUsername){
        try{
            return ResponseEntity.ok().body(chatbotSpecialService.getFormTicketsAndDetails(approvalIdOrUsername));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.getMessage());

        }
    }

}
