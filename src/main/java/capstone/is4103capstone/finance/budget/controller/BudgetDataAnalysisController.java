package capstone.is4103capstone.finance.budget.controller;

import capstone.is4103capstone.finance.budget.service.BudgetDataAnalysisService;
import capstone.is4103capstone.finance.budget.model.req.BudgetDataAnalysisReq;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ExportToFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/budgetData")
public class BudgetDataAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetDataAnalysisService budgetDataAnalysisService;

    @Autowired
    ExportToFileService exportToFileService;

    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<Object> exportReport(@PathVariable("filename") String filename, @RequestBody BudgetDataAnalysisReq budgetDataAnalysisReq) {
        Object in = budgetDataAnalysisService.exportToBudgetFile(filename, budgetDataAnalysisReq);
        // return IOUtils.toByteArray(in);
        if(in instanceof ByteArrayInputStream){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename="+filename);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new InputStreamResource((ByteArrayInputStream)in));
        }
        else{
            return ResponseEntity
                    .badRequest()
                    .body((GeneralRes) in);
        }
    }
}
