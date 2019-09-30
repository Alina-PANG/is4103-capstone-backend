package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.controller.model.res.CurrencyRes;
import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.admin.service.CurrencyService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/ce/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<CurrencyRes> createCurrency(@RequestBody CurrencyDto input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new CurrencyRes(null, false, Optional.of(Collections.singletonList(currencyService.createCurrency(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new CurrencyRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping
    public ResponseEntity<CurrencyRes> getAllCurrencies(@RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new CurrencyRes(null, false, Optional.of(currencyService.getAllCurrencies())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new CurrencyRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CurrencyRes> getAllCurrencies(@PathVariable(name = "uuid") String input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new CurrencyRes(null, false, Optional.of(Collections.singletonList(currencyService.getCurrencyByUuid(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new CurrencyRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @PutMapping
    public ResponseEntity<CurrencyRes> updateCurrency(@RequestBody CurrencyDto input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity.ok().body(new CurrencyRes(null, false, Optional.of(Collections.singletonList(currencyService.updateCurrency(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new CurrencyRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<CurrencyRes> deleteCurrency(@PathVariable(name = "uuid") String input, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            currencyService.deleteCurrency(input);
            return ResponseEntity.ok().body(new CurrencyRes("Currency with UUID " + input + " has been successfully deleted.", false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new CurrencyRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}