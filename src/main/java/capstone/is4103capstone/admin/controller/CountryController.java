package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.CountryRes;
import capstone.is4103capstone.admin.dto.CountryDto;
import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import capstone.is4103capstone.admin.service.CountryService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/ce/country")
public class CountryController {

    @Autowired
    AuditTrailActivityService atas;

    @Autowired
    CountryService cs;

    @PostMapping
    public ResponseEntity<CountryRes> createCountry(@RequestBody CountryDto input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new CountryRes(null, false, Optional.of(Arrays.asList(cs.createCountry(input)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping
    public ResponseEntity<CountryRes> getAllCountries() {
        WriteAuditTrail.autoAudit("admin");
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new CountryRes(null, false, Optional.of(cs.getAllCountries())));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CountryRes> getCountryByUuid(@PathVariable(name = "uuid") String uuid) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new CountryRes(null, false, Optional.of(Arrays.asList(cs.getCountryByUuid(uuid)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byRegion/{region}")
    public ResponseEntity<CountryRes> getCountryDtoByRegion(@PathVariable(name = "region") String region) throws Exception {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new CountryRes(null, false, Optional.of(cs.getCountryEntityByRegion(region))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @PutMapping
    public ResponseEntity<CountryRes> updateCountry(@RequestBody CountryDto input) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new CountryRes(null, false, Optional.of(Arrays.asList(cs.updateCountry(input)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteCountry(@PathVariable(name = "uuid") String uuid) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            cs.deleteCountry(uuid);
            return ResponseEntity
                    .ok()
                    .body(new CountryRes("Country with UUID " + uuid + " has been successfully deleted.", false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new CountryRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
