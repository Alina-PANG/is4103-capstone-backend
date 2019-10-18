package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.general.service.FileStorageService;
import capstone.is4103capstone.seat.model.EmployeeGroupModelWithValidityChecking;
import capstone.is4103capstone.seat.service.SeatAllocationExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/seatAllocation/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatAllocationExcelController {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationExcelController.class);

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    SeatAllocationExcelService seatAllocationExcelService;

    @PostMapping
    public ResponseEntity uploadEmployeeIdExcelFile(@RequestParam("file") MultipartFile file) {
        Path p = fileStorageService.storeFileAndReturnPath(file);
        EmployeeGroupModelWithValidityChecking employeeGroupModelWithValidityChecking = seatAllocationExcelService.convertUploadedEmployeeIdExcelFile(p);
        return ResponseEntity.ok().body(employeeGroupModelWithValidityChecking);
    }
}
