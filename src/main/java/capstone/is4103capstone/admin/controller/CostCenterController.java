package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GetCostCenterListRes;
import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.general.AuthenticationTools;
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
    public @ResponseBody ResponseEntity<Object> retrieveSimpleCostCentersByUser(){
        try{
            return new ResponseEntity<Object>(ccService.getCostCentersByUser("").toString(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Object>(new GeneralRes(ex.getMessage(),true), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/view-my")
    public ResponseEntity<GetCostCenterListRes> retrieveCostCenterByUser(){
        try{
            return new ResponseEntity<>(ccService.getCostCentersByUser(""), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new GetCostCenterListRes(ex.getMessage(),true), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<GetCostCenterListRes> findAll(){
        try{
            return new ResponseEntity<>(ccService.getCostCentersByUser(""), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new GetCostCenterListRes(ex.getMessage(),true), HttpStatus.BAD_REQUEST);
        }
    }
}
