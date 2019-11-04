package capstone.is4103capstone.finance.dashboard.controller;

import capstone.is4103capstone.finance.dashboard.service.ContractValueAnalysisService;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard/contract")
@CrossOrigin(origins = "http://localhost:3000")
public class ContractValueAnalysisController {


    @Autowired
    ContractValueAnalysisService contractValueAnalysisService;

    @GetMapping("/all-vendor")
    public ResponseEntity<GeneralRes> getContractValueAnalysisResultAllVendor(@RequestParam(name = "year")String yearStr){
        try{
            int year = Integer.valueOf(yearStr);
            return ResponseEntity.ok().body(contractValueAnalysisService.getAllVendorByYear(year));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }

    }
}
