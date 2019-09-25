package capstone.is4103capstone.finance.budget.controller;

import capstone.is4103capstone.finance.budget.service.BudgetDataAnalysisService;
import capstone.is4103capstone.finance.budget.model.req.BudgetDataAnalysisReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.FilePathReq;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ExportToFileService;
import capstone.is4103capstone.general.service.ReadFromFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/budgetData")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetDataAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetDataAnalysisController.class);

    @Autowired
    private BudgetDataAnalysisService budgetDataAnalysisService;

    @Autowired
    ExportToFileService exportToFileService;


    @PostMapping(value = "/upload")
    public  ResponseEntity<GeneralRes> uploadFile(@RequestBody FilePathReq filePathReq) {
        if(Authentication.authenticateUser(filePathReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(budgetDataAnalysisService.readUploadedFile(filePathReq.getFilePath()));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping(value = "/download/{id}/{filename}")
    public ResponseEntity<Object> exportReport(@PathVariable("id") String id, @PathVariable("filename") String filename) {
        Object in = budgetDataAnalysisService.exportToBudgetFile(filename, id);
        // return IOUtils.toByteArray(in);
        if(in instanceof ByteArrayInputStream){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename="+filename+".xlsx");

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
