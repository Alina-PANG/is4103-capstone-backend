package capstone.is4103capstone.demoModule.controller;

import capstone.is4103capstone.demoModule.service.DemoService;
import capstone.is4103capstone.demoModule.service.ExportToFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoService demoService;

    @Autowired
    ExportToFileService exportToFileService;

    @GetMapping
    public String defaultDemoPage() {
        return "This is default page for DemoService";
    }


    @GetMapping(value = "/api/download/customers.xlsx")
    public ResponseEntity<InputStreamResource> excelCustomersReport() {
        ByteArrayInputStream in = exportToFileService.exportToExcel("customers");
        // return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=customers.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/{id}")
    public String getDemoObjectById(@PathVariable("id") long id) {
        return demoService.getTestString(id);
    }




}
