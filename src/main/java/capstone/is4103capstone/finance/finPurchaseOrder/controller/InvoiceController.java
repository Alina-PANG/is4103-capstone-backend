package capstone.is4103capstone.finance.finPurchaseOrder.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.finance.Invoice;
import capstone.is4103capstone.finance.budget.service.BudgetService;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateInvoiceReq;
import capstone.is4103capstone.finance.finPurchaseOrder.service.InvoiceService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.UploadFileResponse;
import capstone.is4103capstone.general.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "http://localhost:3000")
public class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/upload/{soaId}")
    public ResponseEntity<GeneralRes> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("soaId") String id) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return invoiceService.storeFile(file, id, employeeService.getCurrentLoginUsername());
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/update/{soaId}")
    public ResponseEntity<GeneralRes> uploadFile(@PathVariable("soaId") String id, CreateInvoiceReq createInvoiceReq) {
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return invoiceService.update(createInvoiceReq, id, employeeService.getCurrentLoginUsername());
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/download/{invoiceId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("invoiceId") String id) {
        if (AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return invoiceService.getFile(id);
        else
            return ResponseEntity
                    .badRequest().build();
    }
}
