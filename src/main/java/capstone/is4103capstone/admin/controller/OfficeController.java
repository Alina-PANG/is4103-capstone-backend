package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.OfficeDto;
import capstone.is4103capstone.admin.model.res.OfficeRes;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/ce/office")
public class OfficeController {

    @Autowired
    OfficeService officeService;

    // CREATE (POST)
    @PostMapping
    public ResponseEntity<OfficeRes> createNewOffice(@RequestBody OfficeDto input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new OfficeRes(null, false, Optional.of(Collections.singletonList(officeService.createNewOffice(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new OfficeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // RETRIEVE (GET)
    @GetMapping
    public ResponseEntity<OfficeRes> getAllOffices() {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new OfficeRes(null, false, Optional.of(officeService.getAllOffices())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new OfficeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OfficeRes> getOffice(@PathVariable(name = "uuid") String input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new OfficeRes(null, false, Optional.of(Collections.singletonList(officeService.getOfficeByUuid(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new OfficeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // UPDATE (PUT)
    @PutMapping
    public ResponseEntity<OfficeRes> updateOffice(@RequestBody OfficeDto input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new OfficeRes(null, false, Optional.of(Collections.singletonList(officeService.updateOffice(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new OfficeRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    // DELETE (DELETE)
    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteOffice(@PathVariable(name = "uuid") String input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            officeService.deleteOffice(input);
            return ResponseEntity.ok().body(new OfficeRes("Office with UUID " + input + " has been successfully deleted!", false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new OfficeRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
