package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GenericObjectRes;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/businessUnit")
public class BusinessUnitController {

    @Autowired
    BusinessUnitService businessUnitService;

    @GetMapping
    public ResponseEntity<GenericObjectRes> getAllBusinessUnits() {
        try {
            WriteAuditTrail.autoAuditUsingSpringSecurity();
            return ResponseEntity.ok().body(new GenericObjectRes(null, false, Optional.of(businessUnitService.getAllBusinessUnits(true))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GenericObjectRes> getBusinessUnitsByUuid(@PathVariable(name = "uuid") String input) {
        try {
            WriteAuditTrail.autoAuditUsingSpringSecurity();
            return ResponseEntity.ok().body(new GenericObjectRes(null, false, Optional.of(Collections.singletonList(businessUnitService.getBusinessUnitByUuid(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byCountryUuid/{uuid}")
    public ResponseEntity<GenericObjectRes> getBusinessUnitsByCountryUuid(@PathVariable(name = "uuid") String input) {
        try {
            WriteAuditTrail.autoAuditUsingSpringSecurity();
            return ResponseEntity.ok().body(new GenericObjectRes(null, false, Optional.of(businessUnitService.getBusinessUnitsByCountryUuid(input))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

}