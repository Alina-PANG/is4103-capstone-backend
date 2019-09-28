package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GetCostCentersRes;
import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cost-center")
@CrossOrigin(origins = "http://localhost:3000")
public class CostCenterController {
    @Autowired
    CostCenterService ccService;

    @GetMapping("/view-my")
    public ResponseEntity<GetCostCentersRes> retrieveCostCenterByUser(@RequestParam(name = "username") String username){
        if(Authentication.authenticateUser(username))
            return ResponseEntity
                    .ok()
                    .body(ccService.getCostCentersByUser(username));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GetCostCentersRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}