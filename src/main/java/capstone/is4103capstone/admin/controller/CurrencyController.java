package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.admin.service.CurrencyService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/ce/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public CurrencyDto createCurrency(@RequestBody CurrencyDto input) {
        return currencyService.createCurrency(input);
    }

    @GetMapping
    public List<CurrencyDto> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{uuid}")
    public CurrencyDto getAllCurrencies(@PathVariable(name = "uuid") String input) {
        try {
            return currencyService.getCurrencyByUuid(input);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + input + " not found!");
        }
    }

    @PutMapping
    public CurrencyDto updateCurrency(@RequestBody CurrencyDto input) {
        try {
            return currencyService.updateCurrency(input);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + input.getId() + " not found!");
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteCurrency(@PathVariable(name = "uuid") String input) {
        try {
            currencyService.deleteCurrency(input);
            return ResponseEntity.status(200).body("Currency " + input + " has been deleted.");
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + input + " not found!");
        }
    }
}
