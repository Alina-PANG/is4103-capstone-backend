package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.CountryDto;
import capstone.is4103capstone.admin.service.CountryService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ce/country")
public class CountryController {


    @Autowired
    CountryService cs;

    @PostMapping
    public CountryDto createCountry(@RequestBody CountryDto input) {
        return cs.createCountry(input);
    }

    @GetMapping
    public List<CountryDto> getAllCountries() {
        return cs.getAllCountries();
    }

    @GetMapping("/{uuid}")
    public CountryDto getCountryDto(@PathVariable(name = "uuid") String uuid) {
        return cs.getCountryByUuid(uuid);
    }

    @GetMapping("/byRegion/{uuid}")
    public List<CountryDto> getCountryDtoByRegion(@PathVariable(name = "uuid") String uuid) {
        return cs.getCountryEntityByRegion(uuid);
    }

    @PutMapping
    public CountryDto updateCountry(@RequestBody CountryDto input) {
        try {
            return cs.updateCountry(input);
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteCountry(@PathVariable(name = "uuid") String uuid) {
        try {
            cs.deleteCountry(uuid);
            return ResponseEntity.status(HttpStatus.OK).body("Country with UUID " + uuid + " sucessfully deleted.");
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
