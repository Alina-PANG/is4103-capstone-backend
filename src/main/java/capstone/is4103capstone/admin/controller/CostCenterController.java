package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GetCostCenterListRes;
import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cost-center")
@CrossOrigin(origins = "http://localhost:3000")
public class CostCenterController {
    @Autowired
    CostCenterService ccService;

    @GetMapping("/view-my-cc")
    public @ResponseBody ResponseEntity<Object> retrieveSimpleCostCentersByUser(@RequestHeader("Authorization") String authToken){
        String username = Authentication.getUsernameFromToken(authToken);
        WriteAuditTrail.autoAudit(username);
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(ccService.getCostCentersByUserSimple(username).toString(), HttpStatus.OK);
        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);


    }

    @GetMapping("/view-my")
    public ResponseEntity<GetCostCenterListRes> retrieveCostCenterByUser(@RequestHeader("Authorization") String authToken){
        String username = Authentication.getUsernameFromToken(authToken);
        WriteAuditTrail.autoAudit(username);
        if(Authentication.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(ccService.getCostCentersByUser(username));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GetCostCenterListRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
