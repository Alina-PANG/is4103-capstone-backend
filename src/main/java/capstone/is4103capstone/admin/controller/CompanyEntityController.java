package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.service.CompanyEntityService;
import capstone.is4103capstone.entities.Currency;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ce")
public class CompanyEntityController {

    @Autowired
    CompanyEntityService ces;

    // === CURRENCY METHODS ===
    @PostMapping("/currency")
    public Currency createCurrency(@RequestBody Currency currencyRequest) {
        return ces.createCurrency(currencyRequest);
    }

    @GetMapping("/currency")
    public List<Currency> getAllCurrencies() {
        return ces.getAllCurrencies();
    }

    @PutMapping("/currency")
    public ResponseEntity updateCurrency(@RequestBody Currency currency) {
        try {
            ces.updateCurrency(currency.getId(), currency);
            return ResponseEntity.status(200).build();
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + currency.getId() + " not found!");
        }
    }

    @DeleteMapping("/currency")
    public ResponseEntity deleteCurrency(@RequestParam(name = "code") String currencyCode) {
        try {
            if (ces.deleteCurrency(currencyCode)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed!");
            }
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + currencyCode + " not found!");
        }
    }

    // ===== END CURRENCY METHODS =====

    // ===== START REGION METHODS =====
    @PostMapping("/region")
    public Region createRegion(@RequestBody Region region) {
        return ces.createRegion(region);
    }

    @GetMapping("/region/{regionName}")
    public Region getRegion(@PathVariable(name = "regionName", required = true) String regionName) {
        try {
            return ces.getRegionByName(regionName);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region with name " + regionName + " was not found.");
        }
    }

    @GetMapping("/region")
    public List<Region> getAllRegions() {
        if (ces.getAllRegions().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No regions are saved in the database.");
        return ces.getAllRegions();
    }

    @PutMapping("/region")
    public Region updateRegion(@RequestBody Region region) {
        return ces.updateRegion(region);
    }

    @DeleteMapping("/region/{uuid}")
    public ResponseEntity deleteRegion(@PathVariable(name = "uuid") String uuid) {
        ces.deleteRegion(ces.getRegionByUuid(uuid));
        return ResponseEntity.status(HttpStatus.OK).body("Region with UUID " + uuid + " was successfully deleted.");
    }

    /*
    // ===== START COUNTRY METHODS =====
    @PostMapping("/country")
    public ResponseEntity createCountry(@RequestBody Country country) {

    }

    @GetMapping("/country")
    public ResponseEntity getAllCountries() {

    }

    @GetMapping("/country")
    public ResponseEntity getCountry(String uuid) {

    }

    @PutMapping("/country")
    public ResponseEntity updateCountry(Country country) {

    }

    @DeleteMapping("/country")
    public ResponseEntity deleteCountry(String uuid) {
    }

    // ===== END COUNTRY METHODS =====
     */
}
